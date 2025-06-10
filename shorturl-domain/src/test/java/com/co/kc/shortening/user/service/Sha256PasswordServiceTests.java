package com.co.kc.shortening.user.service;

import com.co.kc.shortening.user.domain.model.UserPassword;
import com.co.kc.shortening.user.domain.model.UserRawPassword;
import jodd.util.RandomString;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class Sha256PasswordServiceTests {

    private final PasswordService passwordService = new Sha256PasswordService();

    @Test
    public void testEncryptPassword() {
        RandomString randomString = new RandomString();
        UserRawPassword userRawPassword = new UserRawPassword(randomString.randomAscii(20));
        UserPassword userPassword = passwordService.encrypt(userRawPassword);
        Assert.assertNotNull(userPassword);
        Assert.assertNotNull(userPassword.getPassword());
    }

    @Test
    public void testVerifyPassword() {
        RandomString randomString = new RandomString();
        UserRawPassword userRawPassword = new UserRawPassword(randomString.randomAscii(20));
        UserPassword userPassword = passwordService.encrypt(userRawPassword);
        Assert.assertTrue(passwordService.verify(userRawPassword, userPassword));
    }

}
