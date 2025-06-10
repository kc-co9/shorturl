package com.co.kc.shortening.shorturl.domain.model;

import org.junit.Assert;
import org.junit.Test;

public class ShortIdTests {
    @Test
    public void testCreateNullShortId() {
        try {
            new ShortId(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("id is null", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateLessThanZeroShortId() {
        try {
            new ShortId(-1L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("id is less than or equal to 0", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEqualToZeroShortId() {
        try {
            new ShortId(0L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("id is less than or equal to 0", e.getMessage());
            return;
        }
        Assert.fail();
    }


    @Test
    public void testCreateShortIdSuccessfully() {
        ShortId shortId = new ShortId(10L);
        Assert.assertEquals(10L, shortId.getId().longValue());
    }
}
