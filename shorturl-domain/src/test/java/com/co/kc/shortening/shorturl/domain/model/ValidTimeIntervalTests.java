package com.co.kc.shortening.shorturl.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author kc
 */
class ValidTimeIntervalTests {
    @Test
    void testCreateNullStartTime() {
        IllegalArgumentException illegalArgumentException =
                Assertions.assertThrows(IllegalArgumentException.class, () -> new ValidTimeInterval(null, LocalDateTime.MAX));
        Assertions.assertEquals("startTime is null", illegalArgumentException.getMessage());
    }

    @Test
    void testCreateNullEndTime() {
        IllegalArgumentException illegalArgumentException =
                Assertions.assertThrows(IllegalArgumentException.class, () -> new ValidTimeInterval(LocalDateTime.MIN, null));
        Assertions.assertEquals("endTime is null", illegalArgumentException.getMessage());
    }

    @Test
    void testStartTimeGreaterThanEndTime() {
        IllegalArgumentException illegalArgumentException =
                Assertions.assertThrows(IllegalArgumentException.class, () -> new ValidTimeInterval(LocalDateTime.MAX, LocalDateTime.MIN));
        Assertions.assertEquals("endTime is less than startTime", illegalArgumentException.getMessage());
    }

    @Test
    void testDatetimeWithinInterval() {
        ValidTimeInterval validTimeInterval =
                new ValidTimeInterval(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1));
        Assertions.assertTrue(validTimeInterval.contain(LocalDateTime.now()));
    }

    @Test
    void testDatetimeWithoutInterval() {
        ValidTimeInterval validTimeInterval =
                new ValidTimeInterval(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1));
        Assertions.assertFalse(validTimeInterval.contain(LocalDateTime.now().plusHours(10)));
    }
}
