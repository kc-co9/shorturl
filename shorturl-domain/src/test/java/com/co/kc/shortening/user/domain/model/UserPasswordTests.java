package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserPasswordTests {
    @Test
    void testCreateNullUserPassword() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserPassword(null));
        Assertions.assertEquals("password is null or empty", ex.getMessage());
    }

    @Test
    void testCreateEmptyUserPassword() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new UserPassword(""));
        Assertions.assertEquals("password is null or empty", ex.getMessage());
    }

    @Test
    void testCreateUserPasswordSuccessfully() {
        UserPassword userPassword = new UserPassword("testUserPassword");
        Assertions.assertEquals("testUserPassword", userPassword.getPassword());
    }

    @Test
    void testSameUserPasswordIsEqual() {
        UserPassword userPassword1 = new UserPassword("testUserPassword");
        UserPassword userPassword2 = new UserPassword("testUserPassword");
        Assertions.assertEquals(userPassword1, userPassword2);
    }

    @Test
    void testDifferentUserPasswordIsNotEqual() {
        UserPassword userPassword1 = new UserPassword("testUserPassword1");
        UserPassword userPassword2 = new UserPassword("testUserPassword2");
        Assertions.assertNotEquals(userPassword1, userPassword2);
    }
}
