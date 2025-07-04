package com.co.kc.shortening.infrastructure.client.id.code;

import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.mybatis.service.CodeGenService;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class ShortCodeClientTests {
    @Autowired
    private CodeGenService codeGenService;
    private List<ShortCodeClient> shortCodeClientList;

    @BeforeEach
    void initShortCodeClient() {
        shortCodeClientList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            shortCodeClientList.add(new ShortCodeClient(codeGenService));
        }
    }

    @Test
    void testSingleThreadGenShortCodeByNext() {
        Set<String> set = new HashSet<>();
        ShortCodeClient shortCodeClient = shortCodeClientList.get(0);
        for (int i = 0; i < 1000; i++) {
            Assertions.assertTrue(set.add(shortCodeClient.next()));
        }
    }

    @Test
    void testMultiThreadGenShortCodeByNext() {
        List<CompletableFuture<List<String>>> completableFutureList = new ArrayList<>();
        for (ShortCodeClient shortCodeClient : shortCodeClientList) {
            completableFutureList.add(
                    CompletableFuture.supplyAsync(() -> {
                        List<String> codeList = new ArrayList<>();
                        for (int i = 0; i < 1000; i++) {
                            codeList.add(shortCodeClient.next());
                        }
                        return codeList;
                    }, Executors.newFixedThreadPool(10)));
        }
        Map<String, Long> codeCountMap = CompletableFuture
                .allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .thenApply(v ->
                        completableFutureList.stream()
                                .map(CompletableFuture::join)
                                .flatMap(Collection::stream)
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                .join();
        for (Long count : codeCountMap.values()) {
            Assertions.assertEquals(1, count);
        }
    }
}
