package com.co.kc.shortening.infrastructure.client.cache;

import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class RedisCacheClientTests {

    private static final String testCacheKey = "testCacheKey";
    private static final String testCacheStringValue = "testCacheValue";

    @Autowired
    private RedisCacheClient redisCacheClient;

    @AfterEach
    void clearCache() {
        redisCacheClient.remove(testCacheKey);
    }

    @Test
    void testGetSavedStringCache() {
        redisCacheClient.set(testCacheKey, testCacheStringValue);
        Assertions.assertEquals(testCacheStringValue, redisCacheClient.get(testCacheKey, String.class).get());
    }

    @Test
    void testGetSavedMapCache() {
        Map<String, String> content = new HashMap<>();
        content.put(testCacheKey, testCacheStringValue);
        redisCacheClient.set(testCacheKey, content);

        Optional<Map<String, String>> cacheContent = redisCacheClient.get(testCacheKey, new TypeReference<Map<String, String>>() {
        });
        Assertions.assertEquals(content.get(testCacheKey), cacheContent.get().get(testCacheKey));
    }

    @Test
    void testGetExpiredStringCache() {
        redisCacheClient.set(testCacheKey, testCacheStringValue, 1, TimeUnit.SECONDS);
        await().atMost(2, TimeUnit.SECONDS).until(() -> !redisCacheClient.get(testCacheKey, String.class).isPresent());
        Assertions.assertFalse(redisCacheClient.get(testCacheKey, String.class).isPresent());
    }

    @Test
    void testGetRemovedStringCache() {
        redisCacheClient.set(testCacheKey, testCacheStringValue);

        redisCacheClient.remove(testCacheKey);

        Assertions.assertFalse(redisCacheClient.get(testCacheKey, String.class).isPresent());
    }

    @Test
    void testGetBatchRemovedStringCache() {
        redisCacheClient.set(testCacheKey, testCacheStringValue);

        redisCacheClient.remove(Collections.singletonList(testCacheKey));

        Assertions.assertFalse(redisCacheClient.get(testCacheKey, String.class).isPresent());
    }

    @Test
    void testHasKeyCheckSavedStringCache() {
        redisCacheClient.set(testCacheKey, testCacheStringValue);
        Assertions.assertTrue(redisCacheClient.hasKey(testCacheKey));
    }

    @Test
    void testHasKeyCheckRemovedStringCache() {
        redisCacheClient.set(testCacheKey, testCacheStringValue);
        redisCacheClient.remove(testCacheKey);

        Assertions.assertFalse(redisCacheClient.hasKey(testCacheKey));
    }


    @Test
    void testHasKeyCheckBatchRemovedStringCache() {
        redisCacheClient.set(testCacheKey, testCacheStringValue);
        redisCacheClient.remove(Collections.singletonList(testCacheKey));

        Assertions.assertFalse(redisCacheClient.hasKey(testCacheKey));
    }

    @Test
    void testHasKeyCheckExpiredStringCache() {
        redisCacheClient.set(testCacheKey, testCacheStringValue, 1, TimeUnit.SECONDS);
        await().atMost(2, TimeUnit.SECONDS).until(() -> !redisCacheClient.get(testCacheKey, String.class).isPresent());

        Assertions.assertFalse(redisCacheClient.hasKey(testCacheKey));
    }

    @Test
    void testGetExpiredTimeForStringCache() {
        redisCacheClient.set(testCacheKey, testCacheStringValue, 10, TimeUnit.MINUTES);
        Assertions.assertTrue(redisCacheClient.getExpire(testCacheKey, TimeUnit.MINUTES) >= 9);
    }

    @Test
    void testRefreshExpiredTimeForStringCache() {
        redisCacheClient.set(testCacheKey, testCacheStringValue, 10, TimeUnit.MINUTES);
        redisCacheClient.refreshExpire(testCacheKey, 20, TimeUnit.MINUTES);
        Assertions.assertTrue(redisCacheClient.getExpire(testCacheKey, TimeUnit.MINUTES) >= 19);
    }

}
