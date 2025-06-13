package com.co.kc.shortening.user.service;

import com.co.kc.shortening.user.domain.model.UserPassword;
import com.co.kc.shortening.user.domain.model.UserRawPassword;
import jodd.util.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class Sha256PasswordServiceTests {

    private final PasswordService passwordService = new Sha256PasswordService();

    @Test
    void testEncryptPassword() {
        RandomString randomString = new RandomString();
        UserRawPassword userRawPassword = new UserRawPassword(randomString.randomAscii(20));
        UserPassword userPassword = passwordService.encrypt(userRawPassword);
        Assertions.assertNotNull(userPassword);
        Assertions.assertNotNull(userPassword.getPassword());
    }

    @Test
    void testVerifyPassword() {
        RandomString randomString = new RandomString();
        UserRawPassword userRawPassword = new UserRawPassword(randomString.randomAscii(20));
        UserPassword userPassword = passwordService.encrypt(userRawPassword);
        Assertions.assertTrue(passwordService.verify(userRawPassword, userPassword));
    }

}
