package com.co.kc.shortening.application.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @author kc
 */
class MemoryLockClientTests {
    private LockClient lockClient;

    @BeforeEach
    public void initLockClient() {
        lockClient = new MemoryLockClient();
    }

    @Test
    void testTryLockWithoutLiveTime() {
        Supplier<Boolean> supplier1 = () -> lockClient.tryLock("testLock");
        Supplier<Boolean> supplier2 = () -> lockClient.tryLock("testLock");

        Executor executor = Executors.newFixedThreadPool(2);
        Assertions.assertTrue(CompletableFuture.supplyAsync(supplier1, executor).join());
        Assertions.assertFalse(CompletableFuture.supplyAsync(supplier2, executor).join());
    }

    @Test
    void testTryLockWithLiveTime() {
        Supplier<Boolean> supplier1 = () -> lockClient.tryLock("testLock", 10L);
        Supplier<Boolean> supplier2 = () -> lockClient.tryLock("testLock", 5L);

        Executor executor = Executors.newFixedThreadPool(2);
        Assertions.assertTrue(CompletableFuture.supplyAsync(supplier1, executor).join());
        Assertions.assertFalse(CompletableFuture.supplyAsync(supplier2, executor).join());
    }

    @Test
    void testTryLockWhenLockExpired() {
        Supplier<Boolean> supplier1 = () -> lockClient.tryLock("testLock", 0L);
        Supplier<Boolean> supplier2 = () -> lockClient.tryLock("testLock", 5L);

        Executor executor = Executors.newFixedThreadPool(2);
        Assertions.assertTrue(CompletableFuture.supplyAsync(supplier1, executor).join());
        Assertions.assertTrue(CompletableFuture.supplyAsync(supplier2, executor).join());
    }

    @Test
    void testTryLockWhenLockRelease() {
        Supplier<Boolean> supplier1 = () -> lockClient.tryLock("testLock") && lockClient.releaseLock("testLock");
        Supplier<Boolean> supplier2 = () -> lockClient.tryLock("testLock");

        Executor executor = Executors.newFixedThreadPool(2);
        Assertions.assertTrue(CompletableFuture.supplyAsync(supplier1, executor).join());
        Assertions.assertTrue(CompletableFuture.supplyAsync(supplier2, executor).join());
    }
}
