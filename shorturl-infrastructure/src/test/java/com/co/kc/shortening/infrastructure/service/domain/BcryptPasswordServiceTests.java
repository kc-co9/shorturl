package com.co.kc.shortening.infrastructure.service.domain;

import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.user.domain.model.UserFactory;
import com.co.kc.shortening.user.domain.model.UserPassword;
import com.co.kc.shortening.user.domain.model.UserRawPassword;
import jodd.util.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class BcryptPasswordServiceTests {
    @Autowired
    private BcryptPasswordService bcryptPasswordService;

    @Test
    void testVerifyEncryptedPassword() {
        RandomString randomString = new RandomString();
        UserRawPassword userRawPassword = new UserRawPassword(randomString.randomAlpha(20));
        UserPassword userPassword = bcryptPasswordService.encrypt(userRawPassword);
        Assertions.assertTrue(bcryptPasswordService.verify(userRawPassword, userPassword));
    }

    @Test
    void testVerifyWrongPassword() {
        UserRawPassword correctRawPassword = new UserRawPassword(UserFactory.testUserRawPassword);
        UserRawPassword wrongRawPassword = new UserRawPassword(UserFactory.testUserWrongRawPassword);
        UserPassword correctPassword = bcryptPasswordService.encrypt(correctRawPassword);
        Assertions.assertFalse(bcryptPasswordService.verify(wrongRawPassword, correctPassword));
    }

}
