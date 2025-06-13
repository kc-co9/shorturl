package com.co.kc.shortening.shorturl.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class ShortCodeTests {
    @Test
    void testCreateNullShortCode() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new ShortCode(null));
        Assertions.assertEquals("code is null", e.getMessage());
    }

    @Test
    void testCreateEmptyShortCode() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new ShortCode(""));
        Assertions.assertEquals("code is null", e.getMessage());
    }

    @Test
    void testCreateShortCodeSuccessfully() {
        ShortCode code = new ShortCode("testCode");
        Assertions.assertEquals("testCode", code.getCode());
    }

    @Test
    void testSameShortCodeValueIsEqual() {
        Assertions.assertEquals(new ShortCode("10"), new ShortCode("10"));
    }


    @Test
    void testDifferentShortCodeValueIsNotEqual() {
        Assertions.assertNotEquals(new ShortCode("10"), new ShortCode("11"));
    }
}
