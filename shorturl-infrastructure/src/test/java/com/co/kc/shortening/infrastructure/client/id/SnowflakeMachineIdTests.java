package com.co.kc.shortening.infrastructure.client.id;

import com.co.kc.shortening.common.exception.ExhaustionException;
import com.co.kc.shortening.infrastructure.constant.ZooKeeperConstants;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.ZKPaths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class SnowflakeMachineIdTests {

    private static final int TEST_DATA_CENTER_ID = -1;
    private static final int TEST_MAX_MACHINE_ID = 32;
    private static final String MACHINE_ID_ROOT =
            ZKPaths.makePath(ZooKeeperConstants.getSnowflakePath(), ZooKeeperConstants.getDataCenterPath(TEST_DATA_CENTER_ID));


    @Autowired
    private CuratorFramework curatorFramework;
    private Supplier<Long> allocateMachineIdSupplier;

    @BeforeEach
    void initSnowflakeMachineId() {
        this.allocateMachineIdSupplier = () -> {
            try {
                // 每次都创建一个SnowflakeMachineId实例是为了模拟多实例做并发测试
                SnowflakeMachineId snowflakeMachineId =
                        new SnowflakeMachineId(curatorFramework, TEST_DATA_CENTER_ID, TEST_MAX_MACHINE_ID);
                return snowflakeMachineId.allocateMachineId();
            } catch (ExhaustionException ignored) {
                return -1L;
            } catch (Exception ignored) {
                return -2L;
            }
        };
    }

    @AfterEach
    void clearSnowflakeMachineIdState() throws Exception {
        curatorFramework.delete()
                .deletingChildrenIfNeeded()
                .forPath(MACHINE_ID_ROOT);
    }

    @Test
    @SneakyThrows
    void testSingleThreadAllocateMachineId() {
        for (int i = 0; i <= TEST_MAX_MACHINE_ID; i++) {
            Assertions.assertEquals(i, allocateMachineIdSupplier.get());
        }
        for (int i = 0; i < 3; i++) {
            Assertions.assertEquals(-1, allocateMachineIdSupplier.get());
        }

        this.expectZkNodeEqualToMachineId();
    }

    @Test
    @SneakyThrows
    void testMultiThreadAllocateMachineId() {
        List<CompletableFuture<Long>> completableFutureList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            completableFutureList.add(CompletableFuture.supplyAsync(allocateMachineIdSupplier, Executors.newFixedThreadPool(10)));
        }
        Map<Long, Long> machineIdCountMap = CompletableFuture
                .allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .thenApply(v ->
                        completableFutureList.stream().map(CompletableFuture::join)
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                .join();

        for (long i = 0; i <= TEST_MAX_MACHINE_ID; i++) {
            Assertions.assertEquals(1, machineIdCountMap.get(i));
        }

        int failedNum = 100 - (TEST_MAX_MACHINE_ID + 1);
        Assertions.assertEquals(failedNum, machineIdCountMap.get(-1L));

        this.expectZkNodeEqualToMachineId();
    }

    private void expectZkNodeEqualToMachineId() throws Exception {
        List<String> childrenPath = curatorFramework.getChildren().forPath(MACHINE_ID_ROOT);
        Assertions.assertEquals(TEST_MAX_MACHINE_ID + 1, childrenPath.size());
        for (long i = 0; i <= TEST_MAX_MACHINE_ID; i++) {
            Assertions.assertTrue(childrenPath.contains(String.valueOf(i)));
        }
    }
}
