package com.co.kc.shortening.infrastructure.repository.blocklist;

import com.co.kc.shortening.application.client.CacheClient;
import com.co.kc.shortening.blocklist.domain.model.BlockId;
import com.co.kc.shortening.blocklist.domain.model.Blocklist;
import com.co.kc.shortening.blocklist.domain.model.BlocklistFactory;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.shared.domain.model.Link;
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

import static com.co.kc.shortening.application.model.enums.CacheKey.BLOCKLIST_BY_ID_CACHE;
import static com.co.kc.shortening.application.model.enums.CacheKey.BLOCKLIST_BY_LINK_CACHE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class BlocklistCacheRepositoryTests {
    @SpyBean
    private CacheClient cacheClient;
    @SpyBean
    private BlocklistMySqlRepository blocklistMySqlRepository;

    @Autowired
    private BlocklistCacheRepository blocklistCacheRepository;

    @BeforeEach
    void initBlocklist() {
        Blocklist newBlocklist = BlocklistFactory.createTestBlocklist();
        blocklistMySqlRepository.save(newBlocklist);

        reset(cacheClient);
        reset(blocklistMySqlRepository);
    }

    @AfterEach
    void clearCache() {
        cacheClient.remove(BLOCKLIST_BY_ID_CACHE.get(BlocklistFactory.testBlockId));
        cacheClient.remove(BLOCKLIST_BY_LINK_CACHE.get(BlocklistFactory.testBlockLink));
    }

    @Test
    void testRemoveCacheAfterSavingBlocklist() {
        Blocklist blocklistById = blocklistCacheRepository.find(BlocklistFactory.getTestBlockId());
        Blocklist blocklistByLink = blocklistCacheRepository.find(BlocklistFactory.getTestBlockLink());
        Assertions.assertTrue(cacheClient.hasKey(BLOCKLIST_BY_ID_CACHE.get(BlocklistFactory.testBlockId)));
        Assertions.assertTrue(cacheClient.hasKey(BLOCKLIST_BY_LINK_CACHE.get(BlocklistFactory.testBlockLink)));
        Assertions.assertEquals(blocklistById, blocklistByLink);

        blocklistCacheRepository.save(blocklistById);
        verify(blocklistMySqlRepository, times(1)).save(blocklistById);
        verify(cacheClient, times(1)).remove(BLOCKLIST_BY_ID_CACHE.get(BlocklistFactory.testBlockId));
        verify(cacheClient, times(1)).remove(BLOCKLIST_BY_LINK_CACHE.get(BlocklistFactory.testBlockLink));

        Assertions.assertFalse(cacheClient.hasKey(BLOCKLIST_BY_ID_CACHE.get(BlocklistFactory.testBlockId)));
        Assertions.assertFalse(cacheClient.hasKey(BLOCKLIST_BY_LINK_CACHE.get(BlocklistFactory.testBlockLink)));
    }

    @Test
    void testCacheBlocklistWhenFindById() {
        Blocklist blocklist = blocklistCacheRepository.find(BlocklistFactory.getTestBlockId());
        verify(cacheClient, times(1)).get(BLOCKLIST_BY_ID_CACHE.get(BlocklistFactory.testBlockId), Blocklist.class);
        verify(blocklistMySqlRepository, times(1)).find(BlocklistFactory.getTestBlockId());
        verify(cacheClient, times(1)).set(BLOCKLIST_BY_ID_CACHE.get(BlocklistFactory.testBlockId), blocklist, BLOCKLIST_BY_ID_CACHE.getTimeout(), BLOCKLIST_BY_ID_CACHE.getTimeUnit());
        Assertions.assertTrue(cacheClient.hasKey(BLOCKLIST_BY_ID_CACHE.get(BlocklistFactory.testBlockId)));

        reset(cacheClient);
        reset(blocklistMySqlRepository);

        Blocklist blocklistCache = blocklistCacheRepository.find(BlocklistFactory.getTestBlockId());
        verify(cacheClient, times(1)).get(BLOCKLIST_BY_ID_CACHE.get(BlocklistFactory.testBlockId), Blocklist.class);
        verify(blocklistMySqlRepository, times(0)).find(any(BlockId.class));
        verify(cacheClient, times(0)).set(any(), any(), anyLong(), any());
        Assertions.assertTrue(cacheClient.hasKey(BLOCKLIST_BY_ID_CACHE.get(BlocklistFactory.testBlockId)));

        Assertions.assertEquals(blocklist.getId(), blocklistCache.getId());
        Assertions.assertEquals(blocklist.getBlockId(), blocklistCache.getBlockId());
        Assertions.assertEquals(blocklist.getLink(), blocklistCache.getLink());
        Assertions.assertEquals(blocklist.getRemark(), blocklistCache.getRemark());
        Assertions.assertEquals(blocklist.getStatus(), blocklistCache.getStatus());
    }

    @Test
    void testCacheBlocklistWhenFindByLink() {
        Blocklist blocklist = blocklistCacheRepository.find(BlocklistFactory.getTestBlockLink());
        verify(cacheClient, times(1)).get(BLOCKLIST_BY_LINK_CACHE.get(BlocklistFactory.testBlockLink), Blocklist.class);
        verify(blocklistMySqlRepository, times(1)).find(BlocklistFactory.getTestBlockLink());
        verify(cacheClient, times(1)).set(BLOCKLIST_BY_LINK_CACHE.get(BlocklistFactory.testBlockLink), blocklist, BLOCKLIST_BY_LINK_CACHE.getTimeout(), BLOCKLIST_BY_LINK_CACHE.getTimeUnit());
        Assertions.assertTrue(cacheClient.hasKey(BLOCKLIST_BY_LINK_CACHE.get(BlocklistFactory.testBlockLink)));

        reset(cacheClient);
        reset(blocklistMySqlRepository);

        Blocklist blocklistCache = blocklistCacheRepository.find(BlocklistFactory.getTestBlockLink());
        verify(cacheClient, times(1)).get(BLOCKLIST_BY_LINK_CACHE.get(BlocklistFactory.testBlockLink), Blocklist.class);
        verify(blocklistMySqlRepository, times(0)).find(any(Link.class));
        verify(cacheClient, times(0)).set(any(), any(), anyLong(), any());
        Assertions.assertTrue(cacheClient.hasKey(BLOCKLIST_BY_LINK_CACHE.get(BlocklistFactory.testBlockLink)));

        Assertions.assertEquals(blocklist.getId(), blocklistCache.getId());
        Assertions.assertEquals(blocklist.getBlockId(), blocklistCache.getBlockId());
        Assertions.assertEquals(blocklist.getLink(), blocklistCache.getLink());
        Assertions.assertEquals(blocklist.getRemark(), blocklistCache.getRemark());
        Assertions.assertEquals(blocklist.getStatus(), blocklistCache.getStatus());
    }

    @Test
    void testRemoveCacheAfterRemovingBlocklist() {
        Blocklist blocklist = blocklistCacheRepository.find(BlocklistFactory.getTestBlockId());
        blocklistCacheRepository.remove(blocklist);

        verify(blocklistMySqlRepository, times(1)).remove(blocklist);
        verify(cacheClient, times(1)).remove(BLOCKLIST_BY_ID_CACHE.get(BlocklistFactory.testBlockId));
        verify(cacheClient, times(1)).remove(BLOCKLIST_BY_LINK_CACHE.get(BlocklistFactory.testBlockLink));

        Assertions.assertFalse(cacheClient.hasKey(BLOCKLIST_BY_ID_CACHE.get(BlocklistFactory.testBlockId)));
        Assertions.assertFalse(cacheClient.hasKey(BLOCKLIST_BY_LINK_CACHE.get(BlocklistFactory.testBlockLink)));

        Assertions.assertNull(blocklistCacheRepository.find(BlocklistFactory.getTestBlockId()));
    }

}
