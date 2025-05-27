package com.co.kc.shortening.blocklist.domain.model;

import com.co.kc.shortening.shared.domain.model.Link;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BlocklistMemoryRepositoryTests {
    private BlocklistRepository blocklistRepository;

    @Before
    public void initBlocklistRepository() {
        blocklistRepository = new BlocklistMemoryRepository();
    }

    @Test
    public void testFindNullByBlockId() {
        Blocklist blocklist = blocklistRepository.find(new BlockId(10L));
        Assert.assertNull(blocklist);
    }

    @Test
    public void testFindNullByLink() {
        Blocklist blocklist = blocklistRepository.find(new Link("http://www.test.com"));
        Assert.assertNull(blocklist);
    }

    @Test
    public void testSaveBlocklistSuccessfully() {
        Blocklist blocklist = saveTestBlocklist();

        Assert.assertNotNull(blocklistRepository.find(blocklist.getBlockId()));
        Assert.assertNotNull(blocklistRepository.find(blocklist.getLink()));

        Assert.assertEquals(blocklist.getBlockId(), blocklistRepository.find(blocklist.getBlockId()).getBlockId());
        Assert.assertEquals(blocklist.getBlockId(), blocklistRepository.find(blocklist.getLink()).getBlockId());
    }

    @Test
    public void testRemoveBlocklist() {
        Blocklist blocklist = saveTestBlocklist();

        blocklistRepository.remove(blocklist);
        Assert.assertNull(blocklistRepository.find(blocklist.getBlockId()));
        Assert.assertNull(blocklistRepository.find(blocklist.getLink()));
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
