package com.co.kc.shortening.user.service;

import com.co.kc.shortening.user.domain.model.*;
import com.co.kc.shortening.common.exception.AuthException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * @author kc
 */
public class AuthServiceTests {
    private final UserRepository userRepository = new UserMemoryRepository();

    @Before
    public void initUserRepository() {
        User user = UserFactory.createTestUser();
        userRepository.save(user);
    }

    @Test
    public void testAuthenticateSuccessfully() {
        AuthService authService = new AuthService(userRepository, UserFactory.testPasswordService);
        User user = authService.authenticate(UserFactory.getTestUserEmail(), UserFactory.getTestUserRawPassword());
        Assert.assertNotNull(user);
    }

    @Test
    public void testFailToAuthenticateBecauseOfErrorEmail() {
        AuthService authService = new AuthService(userRepository, UserFactory.testPasswordService);
        try {
            authService.authenticate(UserFactory.getTestUserWrongEmail(), UserFactory.getTestUserRawPassword());
        } catch (AuthException ex) {
            Assert.assertEquals("user is not exist", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testFailToAuthenticateBecauseOfErrorPassword() {
        AuthService authService = new AuthService(userRepository, UserFactory.testPasswordService);
        try {
            authService.authenticate(UserFactory.getTestUserEmail(), UserFactory.getTestUserWrongRawPassword());
        } catch (AuthException ex) {
            Assert.assertEquals("user is failed to authenticate", ex.getMessage());
            return;
        }
        Assert.fail();
    }
}
