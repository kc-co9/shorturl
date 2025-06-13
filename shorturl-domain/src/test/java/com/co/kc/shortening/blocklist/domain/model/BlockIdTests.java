package com.co.kc.shortening.blocklist.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BlockIdTests {
    @Test
    void testCreateNullBlockId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new BlockId(null));
        Assertions.assertEquals("id is null", e.getMessage());
    }

    @Test
    void testCreateLessThanZeroBlockId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new BlockId(-1L));
        Assertions.assertEquals("id is less than or equal to 0", e.getMessage());
    }

    @Test
    void testCreateEqualToZeroBlockId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new BlockId(0L));
        Assertions.assertEquals("id is less than or equal to 0", e.getMessage());
    }

    @Test
    void testCreateBlockIdSuccessfully() {
        BlockId blockId = new BlockId(10L);
        Assertions.assertEquals(10L, blockId.getId().longValue());
    }

    @Test
    void testSameBlockIdIsEqual() {
        BlockId blockId1 = new BlockId(10L);
        BlockId blockId2 = new BlockId(10L);
        Assertions.assertEquals(blockId1, blockId2);
    }

    @Test
    void testDifferentBlockIdIsNotEqual() {
        BlockId blockId1 = new BlockId(10L);
        BlockId blockId2 = new BlockId(20L);
        Assertions.assertNotEquals(blockId1, blockId2);
    }
}
