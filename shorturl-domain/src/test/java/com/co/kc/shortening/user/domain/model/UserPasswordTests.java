package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Test;

public class UserPasswordTests {
    @Test
    public void testCreateNullUserPassword() {
        try {
            new UserPassword(null);
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("password is null or empty", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEmptyUserPassword() {
        try {
            new UserPassword("");
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("password is null or empty", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateUserPasswordSuccessfully() {
        UserPassword userPassword = new UserPassword("testUserPassword");
        Assert.assertEquals("testUserPassword", userPassword.getPassword());
    }

    @Test
    public void testSameUserPasswordIsEqual() {
        UserPassword userPassword1 = new UserPassword("testUserPassword");
        UserPassword userPassword2 = new UserPassword("testUserPassword");
        Assert.assertEquals(userPassword1, userPassword2);
    }

    @Test
    public void testDifferentUserPasswordIsNotEqual() {
        UserPassword userPassword1 = new UserPassword("testUserPassword1");
        UserPassword userPassword2 = new UserPassword("testUserPassword2");
        Assert.assertNotEquals(userPassword1, userPassword2);
    }
}
