package com.co.kc.shortening.blocklist.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BlocklistTests {
    private Blocklist blocklist;

    @Before
    public void initBlocklist() {
        blocklist = BlocklistFactory.createBlocklist();
    }

    @Test
    public void testBlocklistPropertiesSucceedToSet() {
        Assert.assertEquals(BlocklistFactory.getTestBlockId(), blocklist.getBlockId());
        Assert.assertEquals(BlocklistFactory.getTestBlockLink(), blocklist.getLink());
        Assert.assertEquals(BlocklistFactory.getTestBlockRemark(), blocklist.getRemark());
        Assert.assertEquals(BlocklistFactory.testBlockStatus, blocklist.getStatus());
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
        Assert.assertEquals(BlocklistFactory.getTestBlockRemark(), blocklist.getRemark());
        blocklist.changeRemark(BlocklistFactory.getTestBlockChangedRemark());
        Assert.assertEquals(BlocklistFactory.getTestBlockChangedRemark(), blocklist.getRemark());
    }

    @Test
    public void testBlocklistChangeStatus() {
        Assert.assertEquals(BlocklistFactory.testBlockStatus, blocklist.getStatus());
        blocklist.changeStatus(BlocklistFactory.testBlockChangedStatus);
        Assert.assertEquals(BlocklistFactory.testBlockChangedStatus, blocklist.getStatus());
    }
}
