package com.co.kc.shortening.application.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author kc
 */
class MemoryCacheClientTests {

    private CacheClient cacheClient;

    @BeforeEach
    public void initCacheClient() {
        cacheClient = new MemoryCacheClient();
    }

    @Test
    void testSetCacheWithoutExpired() {
        cacheClient.set("testKey", "cacheVal");
        Assertions.assertTrue(cacheClient.hasKey("testKey"));

        Optional<String> cacheVal = cacheClient.get("testKey", String.class);
        Assertions.assertTrue(cacheVal.isPresent());
        Assertions.assertEquals("cacheVal", cacheVal.get());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assertions.assertEquals(-1L, expired.longValue());
    }

    @Test
    void testSetCacheWithExpired() {
        cacheClient.set("testKey", "cacheVal", 1, TimeUnit.HOURS);
        Assertions.assertTrue(cacheClient.hasKey("testKey"));

        Optional<String> cacheVal = cacheClient.get("testKey", String.class);
        Assertions.assertTrue(cacheVal.isPresent());
        Assertions.assertEquals("cacheVal", cacheVal.get());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.MINUTES);
        Assertions.assertNotNull(expired);
        Assertions.assertTrue(expired >= 59L);
    }

    @Test
    void testRemoveCacheWithoutExpired() {
        cacheClient.set("testKey", "cacheVal");
        cacheClient.remove("testKey");
        Assertions.assertFalse(cacheClient.hasKey("testKey"));

        Optional<String> cacheVal = cacheClient.get("testKey", String.class);
        Assertions.assertFalse(cacheVal.isPresent());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assertions.assertEquals(-2L, expired.longValue());
    }

    @Test
    void testRemoveCacheWithExpired() {
        cacheClient.set("testKey", "cacheVal", 1, TimeUnit.HOURS);
        cacheClient.remove("testKey");
        Assertions.assertFalse(cacheClient.hasKey("testKey"));

        Optional<String> cacheVal = cacheClient.get("testKey", String.class);
        Assertions.assertFalse(cacheVal.isPresent());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assertions.assertEquals(-2L, expired.longValue());
    }

    @Test
    void testCacheExpired() {
        cacheClient.set("testKey", "cacheVal", 1, TimeUnit.MICROSECONDS);
        Assertions.assertFalse(cacheClient.hasKey("testKey"));

        Optional<String> expiredCacheVal = cacheClient.get("testKey", String.class);
        Assertions.assertFalse(expiredCacheVal.isPresent());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assertions.assertEquals(-2L, expired.longValue());
    }

    @Test
    void testRefreshCache() {
        cacheClient.set("testKey", "cacheVal", 1, TimeUnit.HOURS);
        Long expired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assertions.assertTrue(expired >= 59);

        cacheClient.refreshExpire("testKey", 2, TimeUnit.HOURS);
        Long refreshExpired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assertions.assertTrue(refreshExpired >= 119);
    }
}
