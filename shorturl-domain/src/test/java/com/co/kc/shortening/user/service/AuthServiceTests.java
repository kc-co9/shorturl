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
    private final PasswordService passwordService = new BcryptPasswordService();

    @Before
    public void initUserRepository() {
        UserPassword userPassword = passwordService.encrypt(new UserRawPassword("testPassword"));
        User user = new User(
                new UserId(1L),
                new UserEmail("testName@test.com"),
                new UserName("testName"),
                userPassword,
                Collections.emptyList());
        userRepository.save(user);
    }

    @Test
    public void testAuthenticateSuccessfully() {
        AuthService authService = new AuthService(userRepository, passwordService);
        User user = authService.authenticate(new UserEmail("testName@test.com"), new UserRawPassword("testPassword"));
        Assert.assertNotNull(user);
    }

    @Test
    public void testFailToAuthenticateBecauseOfErrorEmail() {
        AuthService authService = new AuthService(userRepository, passwordService);
        try {
            authService.authenticate(new UserEmail("error@test.com"), new UserRawPassword("testPassword"));
        } catch (AuthException ex) {
            Assert.assertEquals("user is not exist", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testFailToAuthenticateBecauseOfErrorPassword() {
        AuthService authService = new AuthService(userRepository, passwordService);
        try {
            authService.authenticate(new UserEmail("testName@test.com"), new UserRawPassword("errorPassword"));
        } catch (AuthException ex) {
            Assert.assertEquals("user is failed to authenticate", ex.getMessage());
            return;
        }
        Assert.fail();
    }
}
