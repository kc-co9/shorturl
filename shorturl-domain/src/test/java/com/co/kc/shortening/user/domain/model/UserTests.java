package com.co.kc.shortening.user.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kc
 */
public class UserTests {
    private User user;

    @Before
    public void initUser() {
        user = UserFactory.createTestUser();
    }

    @Test
    public void testUserPropertiesSucceedToSet() {
        Assert.assertEquals(UserFactory.getTestUserId(), user.getUserId());
        Assert.assertEquals(UserFactory.getTestUserEmail(), user.getEmail());
        Assert.assertEquals(UserFactory.getTestUserName(), user.getName());
        Assert.assertTrue(user.validateRawPassword(UserFactory.getTestUserRawPassword(), UserFactory.testPasswordService));
        Assert.assertFalse(user.validateRawPassword(UserFactory.getTestUserWrongRawPassword(), UserFactory.testPasswordService));
        Assert.assertEquals(UserFactory.getTestRoleIds(), user.getRoleIds());
    }

    @Test
    public void testUserChangeUserName() {
        Assert.assertEquals(UserFactory.getTestUserName(), user.getName());
        user.changeUserName(UserFactory.getTestUserChangedName());
        Assert.assertEquals(UserFactory.getTestUserChangedName(), user.getName());
    }

    @Test
    public void testUserChangeUserEmail() {
        Assert.assertEquals(UserFactory.getTestUserEmail(), user.getEmail());
        user.changeEmail(UserFactory.getTestUserChangedEmail());
        Assert.assertEquals(UserFactory.getTestUserChangedEmail(), user.getEmail());
    }

    @Test
    public void testUserChangeUserPassword() {
        Assert.assertTrue(user.validateRawPassword(UserFactory.getTestUserRawPassword(), UserFactory.testPasswordService));
        user.changePassword(UserFactory.getTestUserChangedPassword());
        Assert.assertTrue(user.validateRawPassword(UserFactory.getTestUserChangedRawPassword(), UserFactory.testPasswordService));
    }

    @Test
    public void testUserChangeUserRole() {
        Assert.assertEquals(UserFactory.getTestRoleIds(), user.getRoleIds());
        user.changeRole(UserFactory.getTestChangedRoleIds());
        Assert.assertEquals(UserFactory.getTestChangedRoleIds(), user.getRoleIds());
    }
}
