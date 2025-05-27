package com.co.kc.shortening.blocklist.domain.model;

import com.co.kc.shortening.shared.domain.model.Link;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BlocklistTests {
    private Blocklist blocklist;

    @Before
    public void initBlocklist() {
        BlockId blockId = new BlockId(10L);
        Link blockLink = new Link("http://www.test.com");
        BlockRemark remark = new BlockRemark("testRemark");
        BlockStatus status = BlockStatus.ONLINE;
        blocklist = new Blocklist(blockId, blockLink, remark, status);
    }

    @Test
    public void testBlocklistIsActiveStatus() {
        Assert.assertEquals(BlockStatus.ONLINE, blocklist.getStatus());
    }

    @Test
    public void testActivateBlocklist() {
        blocklist.activate();
        Assert.assertEquals(BlockStatus.ONLINE, blocklist.getStatus());
    }

    @Test
    public void testInactivateBlocklist() {
        blocklist.inactivate();
        Assert.assertEquals(BlockStatus.OFFLINE, blocklist.getStatus());
    }

    @Test
    public void testBlocklistChangeRemark() {
        Assert.assertEquals("testRemark", blocklist.getRemark().getRemark());
        blocklist.changeRemark(new BlockRemark("testChangeRemark"));
        Assert.assertEquals("testChangeRemark", blocklist.getRemark().getRemark());
    }

    @Test
    public void testBlocklistChangeStatus() {
        Assert.assertEquals(BlockStatus.ONLINE, blocklist.getStatus());
        blocklist.changeStatus(BlockStatus.ONLINE);
        Assert.assertEquals(BlockStatus.ONLINE, blocklist.getStatus());

        blocklist.changeStatus(BlockStatus.OFFLINE);
        Assert.assertEquals(BlockStatus.OFFLINE, blocklist.getStatus());
    }
}
