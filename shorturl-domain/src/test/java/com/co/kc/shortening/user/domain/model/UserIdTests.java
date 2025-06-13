package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class UserIdTests {
    @Test
    void testCreateNullUserId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserId(null));
        Assertions.assertEquals("id is null", e.getMessage());
    }

    @Test
    void testCreateLessThanZeroUserId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserId(-1L));
        Assertions.assertEquals("id is less than or equal to 0", e.getMessage());
    }

    @Test
    void testCreateEqualToZeroUserId() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserId(0L));
        Assertions.assertEquals("id is less than or equal to 0", e.getMessage());
    }

    @Test
    void testCreateUserIdSuccessfully() {
        UserId userId = new UserId(10L);
        Assertions.assertEquals(10L, userId.getId().longValue());
    }

    @Test
    void testSameUserIdIsEqual() {
        UserId userId1 = new UserId(10L);
        UserId userId2 = new UserId(10L);
        Assertions.assertEquals(userId1, userId2);
    }

    @Test
    void testDifferentUserIdIsNotEqual() {
        UserId userId1 = new UserId(10L);
        UserId userId2 = new UserId(20L);
        Assertions.assertNotEquals(userId1, userId2);
    }
}
