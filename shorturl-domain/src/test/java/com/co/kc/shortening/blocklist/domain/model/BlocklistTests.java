package com.co.kc.shortening.blocklist.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlocklistTests {
    private Blocklist blocklist;

    @BeforeEach
    public void initBlocklist() {
        blocklist = BlocklistFactory.createTestBlocklist();
    }

    @Test
    void testBlocklistPropertiesSucceedToSet() {
        Assertions.assertEquals(BlocklistFactory.getTestBlockId(), blocklist.getBlockId());
        Assertions.assertEquals(BlocklistFactory.getTestBlockLink(), blocklist.getLink());
        Assertions.assertEquals(BlocklistFactory.getTestBlockRemark(), blocklist.getRemark());
        Assertions.assertEquals(BlocklistFactory.testBlockStatus, blocklist.getStatus());
    }

    @Test
    void testActivateBlocklist() {
        blocklist.activate();
        Assertions.assertEquals(BlockStatus.ONLINE, blocklist.getStatus());
    }

    @Test
    void testInactivateBlocklist() {
        blocklist.inactivate();
        Assertions.assertEquals(BlockStatus.OFFLINE, blocklist.getStatus());
    }

    @Test
    void testBlocklistChangeRemark() {
        Assertions.assertEquals(BlocklistFactory.getTestBlockRemark(), blocklist.getRemark());
        blocklist.changeRemark(BlocklistFactory.getTestBlockChangedRemark());
        Assertions.assertEquals(BlocklistFactory.getTestBlockChangedRemark(), blocklist.getRemark());
    }

    @Test
    void testBlocklistChangeStatus() {
        Assertions.assertEquals(BlocklistFactory.testBlockStatus, blocklist.getStatus());
        blocklist.changeStatus(BlocklistFactory.testBlockChangedStatus);
        Assertions.assertEquals(BlocklistFactory.testBlockChangedStatus, blocklist.getStatus());
    }
}
