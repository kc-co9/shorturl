package com.co.kc.shortening.infrastructure.repository.user;

import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.user.domain.model.User;
import com.co.kc.shortening.user.domain.model.UserFactory;
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
class UserMySqlRepositoryTests {
    @Autowired
    private UserMySqlRepository userMySqlRepository;

    @Test
    void testFindSavedUserByUserId() {
        User newUser = UserFactory.createTestUser();
        userMySqlRepository.save(newUser);

        User user = userMySqlRepository.find(newUser.getUserId());
        Assertions.assertEquals(UserFactory.getTestUserId(), user.getUserId());
        Assertions.assertEquals(UserFactory.getTestUserName(), user.getName());
        Assertions.assertEquals(UserFactory.getTestUserEmail(), user.getEmail());
        Assertions.assertEquals(UserFactory.getTestUserPassword(), user.getPassword());
        Assertions.assertEquals(UserFactory.getTestRoleIds(), user.getRoleIds());
    }

    @Test
    void testFindSavedUserByUserEmail() {
        User newUser = UserFactory.createTestUser();
        userMySqlRepository.save(newUser);

        User user = userMySqlRepository.find(newUser.getEmail());
        Assertions.assertEquals(UserFactory.getTestUserId(), user.getUserId());
        Assertions.assertEquals(UserFactory.getTestUserName(), user.getName());
        Assertions.assertEquals(UserFactory.getTestUserEmail(), user.getEmail());
        Assertions.assertEquals(UserFactory.getTestUserPassword(), user.getPassword());
        Assertions.assertEquals(UserFactory.getTestRoleIds(), user.getRoleIds());
    }

    @Test
    void testUpdateSavedUser() {
        User newUser = UserFactory.createTestUser();
        userMySqlRepository.save(newUser);

        User updateUser = userMySqlRepository.find(newUser.getUserId());
        updateUser.changeEmail(UserFactory.getTestUserChangedEmail());
        updateUser.changeUserName(UserFactory.getTestUserChangedName());
        updateUser.changePassword(UserFactory.getTestUserChangedPassword());
        userMySqlRepository.save(updateUser);

        User user = userMySqlRepository.find(newUser.getUserId());
        Assertions.assertEquals(UserFactory.getTestUserChangedEmail(), user.getEmail());
        Assertions.assertEquals(UserFactory.getTestUserChangedName(), user.getName());
        Assertions.assertEquals(UserFactory.getTestUserChangedPassword(), user.getPassword());
    }

    @Test
    void testRemoveSavedUser() {
        User newUser = UserFactory.createTestUser();
        userMySqlRepository.save(newUser);

        userMySqlRepository.remove(newUser);

        Assertions.assertNull(userMySqlRepository.find(newUser.getUserId()));
    }
}
