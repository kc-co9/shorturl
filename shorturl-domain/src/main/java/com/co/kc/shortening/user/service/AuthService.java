package com.co.kc.shortening.user.service;

import com.co.kc.shortening.user.domain.model.*;
import com.co.kc.shorturl.common.exception.AuthException;

/**
 * 用户认证-领域服务
 *
 * @author kc
 */
public class AuthService {
    private final UserRepository userRepository;

    private final PasswordService passwordService;

    public AuthService(UserRepository userRepository,
                       PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public User authenticate(UserEmail email, UserRawPassword rawPassword) {
        User user = userRepository.find(email);
        if (user == null) {
            throw new AuthException("user is not exist");
        }

        boolean hasPassed = user.validateRawPassword(rawPassword, passwordService);
        if (!hasPassed) {
            throw new AuthException("user is failed to authenticate");
        }

        return user;
    }
}
