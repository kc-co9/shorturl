package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Test;

public class UserRawPasswordTests {
    @Test
    public void testCreateNullUserRawPassword() {
        try {
            new UserRawPassword(null);
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("password is null or empty", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEmptyUserRawPassword() {
        try {
            new UserRawPassword("");
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("password is null or empty", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateUserRawPasswordSuccessfully() {
        UserRawPassword userRawPassword = new UserRawPassword("testUserRawPassword");
        Assert.assertEquals("testUserRawPassword", userRawPassword.getRawPassword());
    }

    @Test
    public void testSameUserRawPasswordIsEqual() {
        UserRawPassword userRawPassword1 = new UserRawPassword("testUserRawPassword");
        UserRawPassword userRawPassword2 = new UserRawPassword("testUserRawPassword");
        Assert.assertEquals(userRawPassword1, userRawPassword2);
    }

    @Test
    public void testDifferentUserRawPasswordIsNotEqual() {
        UserRawPassword userRawPassword1 = new UserRawPassword("testUserRawPassword1");
        UserRawPassword userRawPassword2 = new UserRawPassword("testUserRawPassword2");
        Assert.assertNotEquals(userRawPassword1, userRawPassword2);
    }
}
