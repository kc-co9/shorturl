package com.co.kc.shortening.shorturl.domain.model;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author kc
 */
public class ValidTimeIntervalTests {
    @Test
    public void testCreateNullStartTime() {
        try {
            new ValidTimeInterval(null, LocalDateTime.MAX);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("startTime is null", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateNullEndTime() {
        try {
            new ValidTimeInterval(LocalDateTime.MIN, null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("endTime is null", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testStartTimeGreaterThanEndTime() {
        try {
            new ValidTimeInterval(LocalDateTime.MAX, LocalDateTime.MIN);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("endTime is less than startTime", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testDatetimeWithinInterval() {
        ValidTimeInterval validTimeInterval =
                new ValidTimeInterval(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1));
        Assert.assertTrue(validTimeInterval.contain(LocalDateTime.now()));
    }

    @Test
    public void testDatetimeWithoutInterval() {
        ValidTimeInterval validTimeInterval =
                new ValidTimeInterval(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1));
        Assert.assertFalse(validTimeInterval.contain(LocalDateTime.now().plusHours(10)));
    }
}
