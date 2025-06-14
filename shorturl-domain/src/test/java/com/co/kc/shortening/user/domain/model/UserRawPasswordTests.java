package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserRawPasswordTests {
    @Test
    void testCreateNullUserRawPassword() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserRawPassword(null));
        Assertions.assertEquals("password is null or empty", ex.getMessage());
    }

    @Test
    void testCreateEmptyUserRawPassword() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserRawPassword(""));
        Assertions.assertEquals("password is null or empty", ex.getMessage());
    }

    @Test
    void testCreateIllegalUserRawPassword() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserRawPassword("****"));
        Assertions.assertEquals("password contains illegal characters", ex.getMessage());
    }

    @Test
    void testCreateUserRawPasswordSuccessfully() {
        UserRawPassword userRawPassword = new UserRawPassword("testUserRawPassword");
        Assertions.assertEquals("testUserRawPassword", userRawPassword.getRawPassword());
    }

    @Test
    void testSameUserRawPasswordIsEqual() {
        UserRawPassword userRawPassword1 = new UserRawPassword("testUserRawPassword");
        UserRawPassword userRawPassword2 = new UserRawPassword("testUserRawPassword");
        Assertions.assertEquals(userRawPassword1, userRawPassword2);
    }

    @Test
    void testDifferentUserRawPasswordIsNotEqual() {
        UserRawPassword userRawPassword1 = new UserRawPassword("testUserRawPassword1");
        UserRawPassword userRawPassword2 = new UserRawPassword("testUserRawPassword2");
        Assertions.assertNotEquals(userRawPassword1, userRawPassword2);
    }
}
