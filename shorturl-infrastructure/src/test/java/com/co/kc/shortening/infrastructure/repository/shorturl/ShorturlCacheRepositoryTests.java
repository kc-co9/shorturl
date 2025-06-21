package com.co.kc.shortening.infrastructure.repository.shorturl;

import com.co.kc.shortening.application.client.CacheClient;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.shorturl.domain.model.ShortId;
import com.co.kc.shortening.shorturl.domain.model.Shorturl;
import com.co.kc.shortening.shorturl.domain.model.ShorturlFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static com.co.kc.shortening.application.model.enums.CacheKey.*;
import static com.co.kc.shortening.application.model.enums.CacheKey.SHORTURL_BY_CODE_CACHE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.reset;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class ShorturlCacheRepositoryTests {
    @SpyBean
    private CacheClient cacheClient;
    @SpyBean
    private ShorturlMySqlRepository shorturlMySqlRepository;

    @Autowired
    private ShorturlCacheRepository shorturlCacheRepository;

    @BeforeEach
    void initBlocklist() {
        Shorturl newShorturl = ShorturlFactory.createTestShorturl();
        shorturlMySqlRepository.save(newShorturl);

        reset(cacheClient);
        reset(shorturlMySqlRepository);
    }

    @AfterEach
    void clearCache() {
        cacheClient.remove(SHORTURL_BY_ID_CACHE.get(ShorturlFactory.testShortId));
        cacheClient.remove(SHORTURL_BY_CODE_CACHE.get(ShorturlFactory.testShortCode));
    }

    @Test
    void testRemoveCacheAfterSavingShorturl() {
        Shorturl shorturlById = shorturlCacheRepository.find(ShorturlFactory.getTestShortId());
        Shorturl shorturlByCode = shorturlCacheRepository.find(ShorturlFactory.getTestShortCode());
        Assertions.assertTrue(cacheClient.hasKey(SHORTURL_BY_ID_CACHE.get(ShorturlFactory.testShortId)));
        Assertions.assertTrue(cacheClient.hasKey(SHORTURL_BY_CODE_CACHE.get(ShorturlFactory.testShortCode)));
        Assertions.assertEquals(shorturlById, shorturlByCode);

        shorturlCacheRepository.save(shorturlById);
        verify(shorturlMySqlRepository, times(1)).save(shorturlById);
        verify(cacheClient, times(1)).remove(SHORTURL_BY_ID_CACHE.get(ShorturlFactory.testShortId));
        verify(cacheClient, times(1)).remove(SHORTURL_BY_CODE_CACHE.get(ShorturlFactory.testShortCode));

        Assertions.assertFalse(cacheClient.hasKey(SHORTURL_BY_ID_CACHE.get(ShorturlFactory.testShortId)));
        Assertions.assertFalse(cacheClient.hasKey(SHORTURL_BY_CODE_CACHE.get(ShorturlFactory.testShortCode)));
    }

    @Test
    void testCacheShorturlWhenFindById() {
        Shorturl shorturl = shorturlCacheRepository.find(ShorturlFactory.getTestShortId());
        verify(cacheClient, times(1)).get(SHORTURL_BY_ID_CACHE.get(ShorturlFactory.testShortId), Shorturl.class);
        verify(shorturlMySqlRepository, times(1)).find(ShorturlFactory.getTestShortId());
        verify(cacheClient, times(1)).set(SHORTURL_BY_ID_CACHE.get(ShorturlFactory.testShortId), shorturl, SHORTURL_BY_ID_CACHE.getTimeout(), SHORTURL_BY_ID_CACHE.getTimeUnit());
        Assertions.assertTrue(cacheClient.hasKey(SHORTURL_BY_ID_CACHE.get(ShorturlFactory.testShortId)));

        reset(cacheClient);
        reset(shorturlMySqlRepository);

        Shorturl shorturlCache = shorturlCacheRepository.find(ShorturlFactory.getTestShortId());
        verify(cacheClient, times(1)).get(SHORTURL_BY_ID_CACHE.get(ShorturlFactory.testShortId), Shorturl.class);
        verify(shorturlMySqlRepository, times(0)).find(any(ShortId.class));
        verify(cacheClient, times(0)).set(any(), any(), anyLong(), any());
        Assertions.assertTrue(cacheClient.hasKey(SHORTURL_BY_ID_CACHE.get(ShorturlFactory.testShortId)));

        Assertions.assertEquals(shorturl.getId(), shorturlCache.getId());
        Assertions.assertEquals(shorturl.getShortId(), shorturlCache.getShortId());
        Assertions.assertEquals(shorturl.getShortCode(), shorturlCache.getShortCode());
        Assertions.assertEquals(shorturl.getRawLink(), shorturlCache.getRawLink());
        Assertions.assertEquals(shorturl.getValidTime(), shorturlCache.getValidTime());
        Assertions.assertEquals(shorturl.getStatus(), shorturlCache.getStatus());
    }

    @Test
    void testCacheShorturlWhenFindByCode() {
        Shorturl shorturl = shorturlCacheRepository.find(ShorturlFactory.getTestShortCode());
        verify(cacheClient, times(1)).get(SHORTURL_BY_CODE_CACHE.get(ShorturlFactory.testShortCode), Shorturl.class);
        verify(shorturlMySqlRepository, times(1)).find(ShorturlFactory.getTestShortCode());
        verify(cacheClient, times(1)).set(SHORTURL_BY_CODE_CACHE.get(ShorturlFactory.testShortCode), shorturl, SHORTURL_BY_ID_CACHE.getTimeout(), SHORTURL_BY_ID_CACHE.getTimeUnit());
        Assertions.assertTrue(cacheClient.hasKey(SHORTURL_BY_CODE_CACHE.get(ShorturlFactory.testShortCode)));

        reset(cacheClient);
        reset(shorturlMySqlRepository);

        Shorturl shorturlCache = shorturlCacheRepository.find(ShorturlFactory.getTestShortCode());
        verify(cacheClient, times(1)).get(SHORTURL_BY_CODE_CACHE.get(ShorturlFactory.testShortCode), Shorturl.class);
        verify(shorturlMySqlRepository, times(0)).find(any(ShortId.class));
        verify(cacheClient, times(0)).set(any(), any(), anyLong(), any());
        Assertions.assertTrue(cacheClient.hasKey(SHORTURL_BY_CODE_CACHE.get(ShorturlFactory.testShortCode)));

        Assertions.assertEquals(shorturl.getId(), shorturlCache.getId());
        Assertions.assertEquals(shorturl.getShortId(), shorturlCache.getShortId());
        Assertions.assertEquals(shorturl.getShortCode(), shorturlCache.getShortCode());
        Assertions.assertEquals(shorturl.getRawLink(), shorturlCache.getRawLink());
        Assertions.assertEquals(shorturl.getValidTime(), shorturlCache.getValidTime());
        Assertions.assertEquals(shorturl.getStatus(), shorturlCache.getStatus());
    }
}
