package com.co.kc.shortening.user.service;

import com.co.kc.shortening.common.constant.ErrorCode;
import com.co.kc.shortening.user.domain.model.*;
import com.co.kc.shortening.common.exception.AuthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class AuthServiceTests {
    private AuthService authService;

    @BeforeEach
    public void initUserRepository() {
        UserRepository userRepository = new UserMemoryRepository();
        userRepository.save(UserFactory.createTestUser());
        authService = new AuthService(userRepository, UserFactory.testPasswordService);
    }

    @Test
    void testAuthenticateSuccessfully() {
        User user = authService.authenticate(UserFactory.getTestUserEmail(), UserFactory.getTestUserRawPassword());
        Assertions.assertNotNull(user);
    }

    @Test
    void testFailToAuthenticateBecauseOfErrorEmail() {
        UserEmail userWrongEmail = UserFactory.getTestUserWrongEmail();
        UserRawPassword userRawPassword = UserFactory.getTestUserRawPassword();
        AuthException ex = Assertions.assertThrows(AuthException.class, () -> authService.authenticate(userWrongEmail, userRawPassword));
        Assertions.assertEquals(ErrorCode.AUTH_FAIL.getMsg(), ex.getMsg());
        Assertions.assertEquals("user is not exist", ex.getReason());
    }

    @Test
    void testFailToAuthenticateBecauseOfErrorPassword() {
        UserEmail userEmail = UserFactory.getTestUserEmail();
        UserRawPassword userWrongRawPassword = UserFactory.getTestUserWrongRawPassword();
        AuthException ex = Assertions.assertThrows(AuthException.class, () -> authService.authenticate(userEmail, userWrongRawPassword));
        Assertions.assertEquals(ErrorCode.AUTH_FAIL.getMsg(), ex.getMsg());
        Assertions.assertEquals("user is failed to authenticate", ex.getReason());
    }
}
