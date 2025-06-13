package com.co.kc.shortening.infrastructure.client.lock;

import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class RedisLockClientTests {
    private static final String testLockKey = "testLockKey";

    @Autowired
    private RedisLockClient redisLockClient;

    @AfterEach
    void clearLock() {
        redisLockClient.releaseLock(testLockKey);
    }

    @Test
    void testReentrantLock() {
        Assertions.assertTrue(redisLockClient.tryLock(testLockKey));
        Assertions.assertTrue(redisLockClient.tryLock(testLockKey));
        Assertions.assertTrue(redisLockClient.tryLock(testLockKey));
        Assertions.assertTrue(redisLockClient.releaseLock(testLockKey));
    }

    @Test
    void testReentrantLockWithLiveTime() {
        Assertions.assertTrue(redisLockClient.tryLock(testLockKey, 10L));
        Assertions.assertTrue(redisLockClient.tryLock(testLockKey, 10L));
        Assertions.assertTrue(redisLockClient.tryLock(testLockKey, 10L));
        Assertions.assertTrue(redisLockClient.releaseLock(testLockKey));
    }

    @Test
    void testReentrantLockWithLiveTimeAndWaitTime() {
        Assertions.assertTrue(redisLockClient.tryLock(testLockKey, 10L, 10L));
        Assertions.assertTrue(redisLockClient.tryLock(testLockKey, 10L, 10L));
        Assertions.assertTrue(redisLockClient.tryLock(testLockKey, 10L, 10L));
        Assertions.assertTrue(redisLockClient.releaseLock(testLockKey));
    }

    @Test
    void testMultiThreadTryLock() throws Exception {
        Callable<Boolean> tryLockTask = () -> redisLockClient.tryLock(testLockKey);
        Callable<Boolean> releaseLockTask = () -> redisLockClient.releaseLock(testLockKey);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            boolean sameThreadTask = tryLockTask.call();
            boolean diffThreadTask = executorService.submit(tryLockTask).get();
            Assertions.assertTrue(sameThreadTask);
            Assertions.assertFalse(diffThreadTask);
            Assertions.assertTrue(releaseLockTask.call());
        } finally {
            executorService.shutdown();
        }
    }
}
