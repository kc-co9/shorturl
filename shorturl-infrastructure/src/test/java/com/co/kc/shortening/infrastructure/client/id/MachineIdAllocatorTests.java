package com.co.kc.shortening.infrastructure.client.id;

import com.co.kc.shortening.infrastructure.constant.ZooKeeperConstants;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.ZKPaths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class MachineIdAllocatorTests {

    private static final int TEST_DATA_CENTER_ID = -1;
    private static final int TEST_MAX_MACHINE_ID = 32;
    private static final String MACHINE_ID_ROOT =
            ZKPaths.makePath(ZooKeeperConstants.getSnowflakePath(), ZooKeeperConstants.getDataCenterPath(TEST_DATA_CENTER_ID));


    @Autowired
    private CuratorFramework curatorFramework;

    @AfterEach
    void clearSnowflakeMachineIdState() throws Exception {
        curatorFramework.delete()
                .deletingChildrenIfNeeded()
                .forPath(MACHINE_ID_ROOT);
    }

    @Test
    void testAllocateDiffMachineIdWhenRefreshMachineId() {
        Set<Long> machineIdSet = new HashSet<>();
        MachineIdAllocator machineIdAllocator = new MachineIdAllocator(curatorFramework, TEST_DATA_CENTER_ID, TEST_MAX_MACHINE_ID);
        for (int i = 0; i < 10; i++) {
            machineIdSet.add(machineIdAllocator.getMachineId());
            machineIdAllocator.refreshMachineId();
        }
        Assertions.assertEquals(10, machineIdSet.size());
    }

    @Test
    void testAllocateDiffMachineIdWhenUsingDiffInstance() {
        Set<Long> machineIdSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            MachineIdAllocator machineIdAllocator = new MachineIdAllocator(curatorFramework, TEST_DATA_CENTER_ID, TEST_MAX_MACHINE_ID);
            machineIdSet.add(machineIdAllocator.getMachineId());
        }
        Assertions.assertEquals(10, machineIdSet.size());
    }

    @Test
    void testAllocateSameMachineIdAfterReleaseMachineId() {
        Set<Long> machineIdSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            MachineIdAllocator machineIdAllocator = new MachineIdAllocator(curatorFramework, TEST_DATA_CENTER_ID, TEST_MAX_MACHINE_ID);
            machineIdSet.add(machineIdAllocator.getMachineId());
            machineIdAllocator.releaseMachineId();
        }
        Assertions.assertEquals(1, machineIdSet.size());
    }

    @Test
    void testAllocateSameMachineIdWhenUsingDiffBusinessCode() {
        Set<Long> machineIdSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            String businessCode = String.valueOf(i);
            MachineIdAllocator machineIdAllocator = new MachineIdAllocator(curatorFramework, TEST_DATA_CENTER_ID, TEST_MAX_MACHINE_ID, businessCode);
            machineIdSet.add(machineIdAllocator.getMachineId());
        }
        Assertions.assertEquals(1, machineIdSet.size());
    }

    @Test
    void testMultiThreadAllocateDiffMachineIdWhenUsingDiffInstance() {
        List<CompletableFuture<Long>> completableFutureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            completableFutureList.add(CompletableFuture.supplyAsync(() -> {
                MachineIdAllocator machineIdAllocator = new MachineIdAllocator(curatorFramework, TEST_DATA_CENTER_ID, TEST_MAX_MACHINE_ID);
                return machineIdAllocator.getMachineId();
            }, Executors.newFixedThreadPool(10)));
        }
        Map<Long, Long> machineIdCountMap = CompletableFuture
                .allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .thenApply(v ->
                        completableFutureList.stream().map(CompletableFuture::join)
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                .join();

        Assertions.assertEquals(10, machineIdCountMap.size());
        for (long count : machineIdCountMap.values()) {
            Assertions.assertEquals(1, count);
        }
    }


    @Test
    void testMultiThreadAllocateSameMachineIdWhenUsingDiffInstanceAndBusinessCode() {
        List<CompletableFuture<Long>> completableFutureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String businessCode = String.valueOf(i);
            completableFutureList.add(CompletableFuture.supplyAsync(() -> {
                MachineIdAllocator machineIdAllocator = new MachineIdAllocator(curatorFramework, TEST_DATA_CENTER_ID, TEST_MAX_MACHINE_ID, businessCode);
                return machineIdAllocator.getMachineId();
            }, Executors.newFixedThreadPool(10)));
        }
        Map<Long, Long> machineIdCountMap = CompletableFuture
                .allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .thenApply(v ->
                        completableFutureList.stream().map(CompletableFuture::join)
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                .join();

        Assertions.assertEquals(1, machineIdCountMap.size());
        for (long count : machineIdCountMap.values()) {
            Assertions.assertEquals(10, count);
        }
    }

    @Test
    void testMultiThreadAllocateDiffMachineIdWhenRefreshMachineId() {
        MachineIdAllocator machineIdAllocator = new MachineIdAllocator(curatorFramework, TEST_DATA_CENTER_ID, TEST_MAX_MACHINE_ID);
        List<CompletableFuture<Long>> completableFutureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            completableFutureList.add(CompletableFuture.supplyAsync(() -> {
                long machinedId = machineIdAllocator.getMachineId();
                machineIdAllocator.refreshMachineId();
                return machinedId;
            }, Executors.newFixedThreadPool(10)));
        }
        Map<Long, Long> machineIdCountMap = CompletableFuture
                .allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .thenApply(v ->
                        completableFutureList.stream().map(CompletableFuture::join)
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                .join();

        Assertions.assertEquals(10, machineIdCountMap.size());
        for (long count : machineIdCountMap.values()) {
            Assertions.assertEquals(1, count);
        }
    }
}
