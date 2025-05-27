package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class UserNameTests {
    @Test
    public void testCreateNullUserName() {
        try {
            new UserName(null);
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("name is null or empty", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEmptyUserName() {
        try {
            new UserName("");
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("name is null or empty", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateUserNameSuccessfully() {
        UserName userName = new UserName("testUserName");
        Assert.assertEquals("testUserName", userName.getName());
    }

    @Test
    public void testSameUserNameIsEqual() {
        UserName userName1 = new UserName("testUserName");
        UserName userName2 = new UserName("testUserName");
        Assert.assertEquals(userName1, userName2);
    }

    @Test
    public void testDifferentUserNameIsNotEqual() {
        UserName userName1 = new UserName("testUserName1");
        UserName userName2 = new UserName("testUserName2");
        Assert.assertNotEquals(userName1, userName2);
    }
}
