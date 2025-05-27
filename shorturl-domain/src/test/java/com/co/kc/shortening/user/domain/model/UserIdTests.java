package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class UserIdTests {
    @Test
    public void testCreateNullUserId() {
        try {
            new UserId(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "id is null");
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateLessThanZeroUserId() {
        try {
            new UserId(-1L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "id is less than or equal to 0");
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEqualToZeroUserId() {
        try {
            new UserId(0L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "id is less than or equal to 0");
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateUserIdSuccessfully() {
        UserId userId = new UserId(10L);
        Assert.assertEquals(10L, userId.getId().longValue());
    }

    @Test
    public void testSameUserIdIsEqual() {
        UserId userId1 = new UserId(10L);
        UserId userId2 = new UserId(10L);
        Assert.assertEquals(userId1, userId2);
    }

    @Test
    public void testDifferentUserIdIsNotEqual() {
        UserId userId1 = new UserId(10L);
        UserId userId2 = new UserId(20L);
        Assert.assertNotEquals(userId1, userId2);
    }
}
