package com.co.kc.shortening.blocklist.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlocklistMemoryRepositoryTests {
    private BlocklistRepository blocklistRepository;

    @BeforeEach
    public void initBlocklistRepository() {
        blocklistRepository = new BlocklistMemoryRepository();
    }

    @Test
    void testFindNullByBlockId() {
        Blocklist blocklist = blocklistRepository.find(BlocklistFactory.getTestBlockId());
        Assertions.assertNull(blocklist);
    }

    @Test
    void testFindNullByLink() {
        Blocklist blocklist = blocklistRepository.find(BlocklistFactory.getTestBlockLink());
        Assertions.assertNull(blocklist);
    }

    @Test
    void testSaveBlocklistSuccessfully() {
        Blocklist blocklist = saveTestBlocklist();

        Assertions.assertNotNull(blocklistRepository.find(blocklist.getBlockId()));
        Assertions.assertNotNull(blocklistRepository.find(blocklist.getLink()));

        Assertions.assertEquals(blocklist.getBlockId(), blocklistRepository.find(blocklist.getBlockId()).getBlockId());
        Assertions.assertEquals(blocklist.getBlockId(), blocklistRepository.find(blocklist.getLink()).getBlockId());
    }

    @Test
    void testRemoveBlocklist() {
        Blocklist blocklist = saveTestBlocklist();

        blocklistRepository.remove(blocklist);
        Assertions.assertNull(blocklistRepository.find(blocklist.getBlockId()));
        Assertions.assertNull(blocklistRepository.find(blocklist.getLink()));
    }

    private Blocklist saveTestBlocklist() {
        Blocklist blocklist = BlocklistFactory.createTestBlocklist();
        blocklistRepository.save(blocklist);
        return blocklist;
    }


}
