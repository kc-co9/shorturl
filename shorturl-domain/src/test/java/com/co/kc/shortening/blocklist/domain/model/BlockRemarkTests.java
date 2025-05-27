package com.co.kc.shortening.blocklist.domain.model;

import org.junit.Assert;
import org.junit.Test;

public class BlockRemarkTests {
    @Test
    public void testCreateNullBlockRemark() {
        BlockRemark blockRemark = new BlockRemark(null);
        Assert.assertEquals("", blockRemark.getRemark());
    }

    @Test
    public void testCreateEmptyBlockRemark() {
        BlockRemark blockRemark = new BlockRemark("");
        Assert.assertEquals("", blockRemark.getRemark());
    }

    @Test
    public void testCreateExceedLengthBlockRemark() {
        StringBuilder remark = new StringBuilder();
        for (int i = 0; i < 257; i++) {
            remark.append("T");
        }
        try {
            new BlockRemark(remark.toString());
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("remark length is greater than 256", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateBlockRemarkSuccess() {
        BlockRemark blockRemark = new BlockRemark("testRemark");
        Assert.assertEquals("testRemark", blockRemark.getRemark());
    }

}
