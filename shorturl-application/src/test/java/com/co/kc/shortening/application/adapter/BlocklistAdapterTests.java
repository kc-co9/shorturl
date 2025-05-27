package com.co.kc.shortening.application.adapter;

import com.co.kc.shortening.blocklist.domain.model.BlockStatus;
import org.junit.Assert;
import org.junit.Test;

public class BlocklistAdapterTests {

    @Test
    public void testConvertOnlineStatus() {
        BlockStatus blockStatus = BlocklistAdapter.convertStatus(1);
        Assert.assertEquals(BlockStatus.ONLINE, blockStatus);
    }

    @Test
    public void testConvertOfflineStatus() {
        BlockStatus blockStatus = BlocklistAdapter.convertStatus(0);
        Assert.assertEquals(BlockStatus.OFFLINE, blockStatus);
    }
}
