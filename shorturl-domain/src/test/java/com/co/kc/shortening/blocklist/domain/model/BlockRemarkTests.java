package com.co.kc.shortening.blocklist.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BlockRemarkTests {
    @Test
    void testCreateNullBlockRemark() {
        BlockRemark blockRemark = new BlockRemark(null);
        Assertions.assertEquals("", blockRemark.getRemark());
    }

    @Test
    void testCreateEmptyBlockRemark() {
        BlockRemark blockRemark = new BlockRemark("");
        Assertions.assertEquals("", blockRemark.getRemark());
    }

    @Test
    void testCreateExceedLengthBlockRemark() {
        StringBuilder remark = new StringBuilder();
        for (int i = 0; i < 257; i++) {
            remark.append("T");
        }
        try {
            new BlockRemark(remark.toString());
        } catch (IllegalArgumentException ex) {
            Assertions.assertEquals("remark length is greater than 256", ex.getMessage());
            return;
        }
        Assertions.fail();
    }

    @Test
    void testCreateBlockRemarkSuccess() {
        BlockRemark blockRemark = new BlockRemark("testRemark");
        Assertions.assertEquals("testRemark", blockRemark.getRemark());
    }

}
