package com.co.kc.shortening.user.domain.model;

import com.co.kc.shortening.user.service.BcryptPasswordService;
import com.co.kc.shortening.user.service.PasswordService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserFactory {
    public static final Long testUserId = 10L;
    public static final String testUserEmail = "test_name@test.com";
    public static final String testUserWrongEmail = "test_wrong_name@test.com";
    public static final String testUserChangedEmail = "test_changed_name@test.com";
    public static final String testUserName = "test_name";
    public static final String testUserChangedName = "test_changed_name";
    public static final String testUserRawPassword = "test_raw_password";
    public static final String testUserChangedRawPassword = "test_changed_raw_password";
    public static final String testUserWrongRawPassword = "test_wrong_raw_password";
    public static final List<Long> testUserRoleIds = Collections.emptyList();
    public static final List<Long> testUserChangedRoleIds = Arrays.asList(1L, 2L);

    public static final PasswordService testPasswordService = new BcryptPasswordService();

    public static User createTestUser() {
        return new User(
                getTestUserId(),
                getTestUserEmail(),
                getTestUserName(),
                getTestUserPassword(),
                getTestRoleIds());
    }

    public static UserId getTestUserId() {
        return new UserId(testUserId);
    }

    public static UserEmail getTestUserEmail() {
        return new UserEmail(testUserEmail);
    }

    public static UserEmail getTestUserChangedEmail() {
        return new UserEmail(testUserChangedEmail);
    }

    public static UserEmail getTestUserWrongEmail() {
        return new UserEmail(testUserWrongEmail);
    }

    public static UserName getTestUserName() {
        return new UserName(testUserName);
    }

    public static UserName getTestUserChangedName() {
        return new UserName(testUserChangedName);
    }

    public static UserRawPassword getTestUserRawPassword() {
        return new UserRawPassword(testUserRawPassword);
    }

    public static UserPassword getTestUserPassword() {
        return testPasswordService.encrypt(new UserRawPassword(testUserRawPassword));
    }

    public static UserRawPassword getTestUserChangedRawPassword() {
        return new UserRawPassword(testUserChangedRawPassword);
    }

    public static UserPassword getTestUserChangedPassword() {
        return testPasswordService.encrypt(new UserRawPassword(testUserChangedRawPassword));
    }

    public static UserRawPassword getTestUserWrongRawPassword() {
        return new UserRawPassword(testUserWrongRawPassword);
    }

    public static UserPassword getTestUserWrongPassword() {
        return testPasswordService.encrypt(new UserRawPassword(testUserWrongRawPassword));
    }

    public static List<RoleId> getTestRoleIds() {
        return testUserRoleIds.stream().map(RoleId::new).collect(Collectors.toList());
    }

    public static List<RoleId> getTestChangedRoleIds() {
        return testUserChangedRoleIds.stream().map(RoleId::new).collect(Collectors.toList());
    }
}
