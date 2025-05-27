package com.co.kc.shortening.shorturl.domain.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class ShortCodeTests {
    @Test
    public void testCreateNullShortCode() {
        try {
            new ShortCode(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "code is null");
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEmptyShortCode() {
        try {
            new ShortCode("");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "code is null");
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateShortCodeSuccessfully() {
        ShortCode code = new ShortCode("testCode");
        Assert.assertEquals("testCode", code.getCode());
    }

    @Test
    public void testSameShortCodeValueIsEqual() {
        Assert.assertEquals(new ShortCode("10"), new ShortCode("10"));
    }


    @Test
    public void testDifferentShortCodeValueIsNotEqual() {
        Assert.assertNotEquals(new ShortCode("10"), new ShortCode("11"));
    }
}
