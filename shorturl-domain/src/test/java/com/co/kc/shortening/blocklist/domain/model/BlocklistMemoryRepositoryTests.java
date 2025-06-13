package com.co.kc.shortening.blocklist.domain.model;

import com.co.kc.shortening.shared.domain.model.Link;
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
        Blocklist blocklist = blocklistRepository.find(new BlockId(10L));
        Assertions.assertNull(blocklist);
    }

    @Test
    void testFindNullByLink() {
        Blocklist blocklist = blocklistRepository.find(new Link("http://www.test.com"));
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
        BlockId blockId = new BlockId(10L);
        Link blockLink = new Link("http://www.test.com");
        BlockRemark remark = new BlockRemark("testRemark");
        BlockStatus status = BlockStatus.ONLINE;
        Blocklist blocklist = new Blocklist(blockId, blockLink, remark, status);
        blocklistRepository.save(blocklist);
        return blocklist;
    }


}
