package com.co.kc.shortening.blocklist.domain.model;

import org.junit.Assert;
import org.junit.Test;

public class BlockIdTests {
    @Test
    public void testCreateNullBlockId() {
        try {
            new BlockId(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "id is null");
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateLessThanZeroBlockId() {
        try {
            new BlockId(-1L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "id is less than or equal to 0");
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEqualToZeroBlockId() {
        try {
            new BlockId(0L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "id is less than or equal to 0");
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateBlockIdSuccessfully() {
        BlockId blockId = new BlockId(10L);
        Assert.assertEquals(10L, blockId.getId().longValue());
    }

    @Test
    public void testSameBlockIdIsEqual() {
        BlockId blockId1 = new BlockId(10L);
        BlockId blockId2 = new BlockId(10L);
        Assert.assertEquals(blockId1, blockId2);
    }

    @Test
    public void testDifferentBlockIdIsNotEqual() {
        BlockId blockId1 = new BlockId(10L);
        BlockId blockId2 = new BlockId(20L);
        Assert.assertNotEquals(blockId1, blockId2);
    }
}
