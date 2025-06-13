package com.co.kc.shortening.infrastructure.mybatis.service;

import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.mybatis.entity.Administrator;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.user.domain.model.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class AdministratorServiceTests {
    @Autowired
    private AdministratorService administratorService;

    @Test
    void testFindAddedAdministratorById() {
        addTestAdministrator();

        Optional<Administrator> administrator = administratorService.findByAdministratorId(UserFactory.testUserId);
        Assertions.assertTrue(administrator.isPresent());
        Assertions.assertEquals(UserFactory.testUserId, administrator.get().getAdministratorId());
        Assertions.assertEquals(UserFactory.testUserEmail, administrator.get().getEmail());
        Assertions.assertEquals(UserFactory.testUserName, administrator.get().getUsername());
        Assertions.assertEquals(UserFactory.testUserRawPassword, administrator.get().getPassword());
    }

    @Test
    void testFindAddedAdministratorByEmail() {
        addTestAdministrator();

        Optional<Administrator> administrator = administratorService.findByEmail(UserFactory.testUserEmail);
        Assertions.assertTrue(administrator.isPresent());
        Assertions.assertEquals(UserFactory.testUserId, administrator.get().getAdministratorId());
        Assertions.assertEquals(UserFactory.testUserEmail, administrator.get().getEmail());
        Assertions.assertEquals(UserFactory.testUserName, administrator.get().getUsername());
        Assertions.assertEquals(UserFactory.testUserRawPassword, administrator.get().getPassword());
    }

    @Test
    void testUpdateAddedAdministrator() {
        addTestAdministrator();

        administratorService.update(administratorService.getUpdateWrapper()
                .set(Administrator::getEmail, UserFactory.testUserChangedEmail)
                .set(Administrator::getUsername, UserFactory.testUserChangedName)
                .set(Administrator::getPassword, UserFactory.testUserChangedRawPassword)
                .eq(Administrator::getAdministratorId, UserFactory.testUserId)
        );

        Optional<Administrator> administrator = administratorService.findByAdministratorId(UserFactory.testUserId);
        Assertions.assertTrue(administrator.isPresent());
        Assertions.assertEquals(UserFactory.testUserId, administrator.get().getAdministratorId());
        Assertions.assertEquals(UserFactory.testUserChangedEmail, administrator.get().getEmail());
        Assertions.assertEquals(UserFactory.testUserChangedName, administrator.get().getUsername());
        Assertions.assertEquals(UserFactory.testUserChangedRawPassword, administrator.get().getPassword());
    }

    @Test
    void testRemoveAddedAdministrator() {
        Administrator newAdministrator = addTestAdministrator();

        administratorService.removeById(newAdministrator.getId());

        Optional<Administrator> administrator = administratorService.findByAdministratorId(UserFactory.testUserId);
        Assertions.assertFalse(administrator.isPresent());
    }

    private Administrator addTestAdministrator() {
        Administrator administrator = new Administrator();
        administrator.setAdministratorId(UserFactory.testUserId);
        administrator.setEmail(UserFactory.testUserEmail);
        administrator.setUsername(UserFactory.testUserName);
        administrator.setPassword(UserFactory.testUserRawPassword);
        administratorService.save(administrator);
        return administrator;
    }
}
