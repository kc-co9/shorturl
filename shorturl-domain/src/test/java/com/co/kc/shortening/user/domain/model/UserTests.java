package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author kc
 */
public class UserTests {
    private User user;

    @Before
    public void initUser() {
        user = new User(new UserId(10L),
                new UserEmail("username@test.com"),
                new UserName("testName"),
                new UserPassword("testPassword"),
                Collections.emptyList());
    }

    @Test
    public void testCreateUserSuccessfully() {
        Assert.assertEquals(new UserId(10L), user.getUserId());
        Assert.assertEquals(new UserEmail("username@test.com"), user.getEmail());
        Assert.assertEquals(new UserName("testName"), user.getName());
        Assert.assertTrue(user.validatePassword(new UserPassword("testPassword")));
        Assert.assertFalse(user.validatePassword(new UserPassword("testWrongPassword")));
        Assert.assertEquals(Collections.emptyList(), user.getRoleIds());
    }

    @Test
    public void testUserChangeUserName() {
        Assert.assertEquals(new UserName("testName"), user.getName());
        user.changeUserName(new UserName("testOtherName"));
        Assert.assertEquals(new UserName("testOtherName"), user.getName());
    }

    @Test
    public void testUserChangeUserEmail() {
        Assert.assertEquals(new UserEmail("username@test.com"), user.getEmail());
        user.changeEmail(new UserEmail("otherusername@test.com"));
        Assert.assertEquals(new UserEmail("otherusername@test.com"), user.getEmail());
    }

    @Test
    public void testUserChangeUserPassword() {
        Assert.assertTrue(user.validatePassword(new UserPassword("testPassword")));
        user.changePassword(new UserPassword("testChangePassword"));
        Assert.assertTrue(user.validatePassword(new UserPassword("testChangePassword")));
    }

    @Test
    public void testUserChangeUserRole() {
        Assert.assertEquals(Collections.emptyList(), user.getRoleIds());
        user.changeRole(Arrays.asList(new RoleId(1L), new RoleId(2L)));
        Assert.assertEquals(Arrays.asList(new RoleId(1L), new RoleId(2L)), user.getRoleIds());
    }
}
