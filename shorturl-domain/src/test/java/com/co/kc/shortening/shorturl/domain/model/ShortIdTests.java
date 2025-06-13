package com.co.kc.shortening.shorturl.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShortIdTests {
    @Test
    void testCreateNullShortId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new ShortId(null));
        Assertions.assertEquals("id is null", e.getMessage());
    }

    @Test
    void testCreateLessThanZeroShortId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new ShortId(-1L));
        Assertions.assertEquals("id is less than or equal to 0", e.getMessage());
    }

    @Test
    void testCreateEqualToZeroShortId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new ShortId(0L));
        Assertions.assertEquals("id is less than or equal to 0", e.getMessage());
    }


    @Test
    void testCreateShortIdSuccessfully() {
        ShortId shortId = new ShortId(10L);
        Assertions.assertEquals(10L, shortId.getId().longValue());
    }
}
