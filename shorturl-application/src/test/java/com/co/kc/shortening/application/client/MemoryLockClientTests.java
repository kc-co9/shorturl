package com.co.kc.shortening.application.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @author kc
 */
public class MemoryLockClientTests {
    private LockClient lockClient;

    @Before
    public void initLockClient() {
        lockClient = new MemoryLockClient();
    }

    @Test
    public void testTryLockWithoutLiveTime() {
        Supplier<Boolean> supplier1 = () -> lockClient.tryLock("testLock");
        Supplier<Boolean> supplier2 = () -> lockClient.tryLock("testLock");

        Executor executor = Executors.newFixedThreadPool(2);
        Assert.assertTrue(CompletableFuture.supplyAsync(supplier1, executor).join());
        Assert.assertFalse(CompletableFuture.supplyAsync(supplier2, executor).join());
    }

    @Test
    public void testTryLockWithLiveTime() {
        Supplier<Boolean> supplier1 = () -> lockClient.tryLock("testLock", 10L);
        Supplier<Boolean> supplier2 = () -> lockClient.tryLock("testLock", 5L);

        Executor executor = Executors.newFixedThreadPool(2);
        Assert.assertTrue(CompletableFuture.supplyAsync(supplier1, executor).join());
        Assert.assertFalse(CompletableFuture.supplyAsync(supplier2, executor).join());
    }

    @Test
    public void testTryLockWhenLockExpired() {
        Supplier<Boolean> supplier1 = () -> lockClient.tryLock("testLock", 0L);
        Supplier<Boolean> supplier2 = () -> lockClient.tryLock("testLock", 5L);

        Executor executor = Executors.newFixedThreadPool(2);
        Assert.assertTrue(CompletableFuture.supplyAsync(supplier1, executor).join());
        Assert.assertTrue(CompletableFuture.supplyAsync(supplier2, executor).join());
    }

    @Test
    public void testTryLockWhenLockRelease() {
        Supplier<Boolean> supplier1 = () -> lockClient.tryLock("testLock") && lockClient.releaseLock("testLock");
        Supplier<Boolean> supplier2 = () -> lockClient.tryLock("testLock");

        Executor executor = Executors.newFixedThreadPool(2);
        Assert.assertTrue(CompletableFuture.supplyAsync(supplier1, executor).join());
        Assert.assertTrue(CompletableFuture.supplyAsync(supplier2, executor).join());
    }
}
