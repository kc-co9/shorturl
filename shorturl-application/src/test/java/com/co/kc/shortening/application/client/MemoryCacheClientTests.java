package com.co.kc.shortening.application.client;

import com.co.kc.shorturl.common.exception.BusinessException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author kc
 */
public class MemoryCacheClientTests {

    private CacheClient cacheClient;

    @Before
    public void initCacheClient() {
        cacheClient = new MemoryCacheClient();
    }

    @Test
    public void testSetCacheWithoutExpired() {
        cacheClient.set("testKey", "cacheVal");
        Assert.assertTrue(cacheClient.hasKey("testKey"));

        Optional<String> cacheVal = cacheClient.get("testKey", String.class);
        Assert.assertTrue(cacheVal.isPresent());
        Assert.assertEquals("cacheVal", cacheVal.get());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assert.assertEquals(-1L, expired.longValue());
    }

    @Test
    public void testIncrCacheWithoutExpired() {
        Long incrVal = cacheClient.increment("testKey");
        Assert.assertEquals(1L, incrVal.longValue());
        Assert.assertTrue(cacheClient.hasKey("testKey"));

        Optional<Long> cacheVal = cacheClient.get("testKey", Long.class);
        Assert.assertTrue(cacheVal.isPresent());
        Assert.assertEquals(1L, cacheVal.get().longValue());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assert.assertNotNull(expired);
        Assert.assertEquals(-1L, expired.longValue());
    }

    @Test
    public void testIncrExistCacheWithoutExpired() {
        cacheClient.set("testKey", 2L);
        Assert.assertTrue(cacheClient.hasKey("testKey"));

        Long incrVal = cacheClient.increment("testKey");
        Assert.assertEquals(3L, incrVal.longValue());
        Assert.assertTrue(cacheClient.hasKey("testKey"));

        Optional<Long> cacheVal = cacheClient.get("testKey", Long.class);
        Assert.assertTrue(cacheVal.isPresent());
        Assert.assertEquals(3L, cacheVal.get().longValue());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assert.assertNotNull(expired);
        Assert.assertEquals(-1L, expired.longValue());
    }

    @Test
    public void testErrorIncrExistCacheWithoutExpired() {
        cacheClient.set("testKey", "stringVal");
        Assert.assertTrue(cacheClient.hasKey("testKey"));
        try {
            cacheClient.increment("testKey");
        } catch (BusinessException ex) {
            Assert.assertEquals("非法操作", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSetCacheWithExpired() {
        cacheClient.set("testKey", "cacheVal", 1, TimeUnit.HOURS);
        Assert.assertTrue(cacheClient.hasKey("testKey"));

        Optional<String> cacheVal = cacheClient.get("testKey", String.class);
        Assert.assertTrue(cacheVal.isPresent());
        Assert.assertEquals("cacheVal", cacheVal.get());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.MINUTES);
        Assert.assertNotNull(expired);
        Assert.assertTrue(expired >= 59L);
    }

    @Test
    public void testIncrCacheWithExpired() {
        Long incrVal = cacheClient.increment("testKey", 2, 3600);
        Assert.assertEquals(2L, incrVal.longValue());
        Assert.assertTrue(cacheClient.hasKey("testKey"));

        Optional<Long> cacheVal = cacheClient.get("testKey", Long.class);
        Assert.assertTrue(cacheVal.isPresent());
        Assert.assertEquals(2L, cacheVal.get().longValue());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.MINUTES);
        Assert.assertNotNull(expired);
        Assert.assertTrue(expired >= 59);
    }

    @Test
    public void testIncrExistCacheWithExpired() {
        cacheClient.set("testKey", 2L, 1, TimeUnit.HOURS);
        Assert.assertTrue(cacheClient.hasKey("testKey"));

        Long incrVal = cacheClient.increment("testKey");
        Assert.assertEquals(3L, incrVal.longValue());
        Assert.assertTrue(cacheClient.hasKey("testKey"));

        Optional<Long> cacheVal = cacheClient.get("testKey", Long.class);
        Assert.assertTrue(cacheVal.isPresent());
        Assert.assertEquals(3L, cacheVal.get().longValue());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.MINUTES);
        Assert.assertNotNull(expired);
        Assert.assertTrue(expired >= 59);
    }

    @Test
    public void testErrorIncrExistCacheWithExpired() {
        cacheClient.set("testKey", "stringVal", 1, TimeUnit.HOURS);
        Assert.assertTrue(cacheClient.hasKey("testKey"));
        try {
            cacheClient.increment("testKey");
        } catch (BusinessException ex) {
            Assert.assertEquals("非法操作", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testIncrExistCacheWithChangingExpired() {
        cacheClient.set("testKey", 2L, 1, TimeUnit.HOURS);
        Assert.assertTrue(cacheClient.hasKey("testKey"));

        Long expired = cacheClient.getExpire("testKey", TimeUnit.MINUTES);
        Assert.assertNotNull(expired);
        Assert.assertTrue(expired >= 59);

        Long incrVal = cacheClient.increment("testKey", 1, 7200);
        Assert.assertEquals(3L, incrVal.longValue());
        Assert.assertTrue(cacheClient.hasKey("testKey"));

        Optional<Long> cacheVal = cacheClient.get("testKey", Long.class);
        Assert.assertTrue(cacheVal.isPresent());
        Assert.assertEquals(3L, cacheVal.get().longValue());

        Long changedExpired = cacheClient.getExpire("testKey", TimeUnit.MINUTES);
        Assert.assertNotNull(changedExpired);
        Assert.assertTrue(changedExpired >= 119);
    }

    @Test
    public void testRemoveCacheWithoutExpired() {
        cacheClient.set("testKey", "cacheVal");
        cacheClient.remove("testKey");
        Assert.assertFalse(cacheClient.hasKey("testKey"));

        Optional<String> cacheVal = cacheClient.get("testKey", String.class);
        Assert.assertFalse(cacheVal.isPresent());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assert.assertEquals(-2L, expired.longValue());
    }

    @Test
    public void testRemoveCacheWithExpired() {
        cacheClient.set("testKey", "cacheVal", 1, TimeUnit.HOURS);
        cacheClient.remove("testKey");
        Assert.assertFalse(cacheClient.hasKey("testKey"));

        Optional<String> cacheVal = cacheClient.get("testKey", String.class);
        Assert.assertFalse(cacheVal.isPresent());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assert.assertEquals(-2L, expired.longValue());
    }

    @Test
    public void testCacheExpired() {
        cacheClient.set("testKey", "cacheVal", 1, TimeUnit.MICROSECONDS);
        Assert.assertFalse(cacheClient.hasKey("testKey"));

        Optional<String> expiredCacheVal = cacheClient.get("testKey", String.class);
        Assert.assertFalse(expiredCacheVal.isPresent());

        Long expired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assert.assertEquals(-2L, expired.longValue());
    }

    @Test
    public void testRefreshCache() {
        cacheClient.set("testKey", "cacheVal", 1, TimeUnit.HOURS);
        Long expired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assert.assertTrue(expired >= 59);

        cacheClient.refreshExpire("testKey", 2, TimeUnit.HOURS);
        Long refreshExpired = cacheClient.getExpire("testKey", TimeUnit.SECONDS);
        Assert.assertTrue(refreshExpired >= 119);
    }
}
