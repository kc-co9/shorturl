package com.co.kc.shortening.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class UserTests {
    private User user;

    @BeforeEach
    public void initUser() {
        user = UserFactory.createTestUser();
    }

    @Test
    void testUserPropertiesSucceedToSet() {
        Assertions.assertEquals(UserFactory.getTestUserId(), user.getUserId());
        Assertions.assertEquals(UserFactory.getTestUserEmail(), user.getEmail());
        Assertions.assertEquals(UserFactory.getTestUserName(), user.getName());
        Assertions.assertTrue(user.validateRawPassword(UserFactory.getTestUserRawPassword(), UserFactory.testPasswordService));
        Assertions.assertFalse(user.validateRawPassword(UserFactory.getTestUserWrongRawPassword(), UserFactory.testPasswordService));
        Assertions.assertEquals(UserFactory.getTestRoleIds(), user.getRoleIds());
    }

    @Test
    void testUserChangeUserName() {
        Assertions.assertEquals(UserFactory.getTestUserName(), user.getName());
        user.changeUserName(UserFactory.getTestUserChangedName());
        Assertions.assertEquals(UserFactory.getTestUserChangedName(), user.getName());
    }

    @Test
    void testUserChangeUserEmail() {
        Assertions.assertEquals(UserFactory.getTestUserEmail(), user.getEmail());
        user.changeEmail(UserFactory.getTestUserChangedEmail());
        Assertions.assertEquals(UserFactory.getTestUserChangedEmail(), user.getEmail());
    }

    @Test
    void testUserChangeUserPassword() {
        Assertions.assertTrue(user.validateRawPassword(UserFactory.getTestUserRawPassword(), UserFactory.testPasswordService));
        user.changePassword(UserFactory.getTestUserChangedPassword());
        Assertions.assertTrue(user.validateRawPassword(UserFactory.getTestUserChangedRawPassword(), UserFactory.testPasswordService));
    }

    @Test
    void testUserChangeUserRole() {
        Assertions.assertEquals(UserFactory.getTestRoleIds(), user.getRoleIds());
        user.changeRole(UserFactory.getTestChangedRoleIds());
        Assertions.assertEquals(UserFactory.getTestChangedRoleIds(), user.getRoleIds());
    }
}
