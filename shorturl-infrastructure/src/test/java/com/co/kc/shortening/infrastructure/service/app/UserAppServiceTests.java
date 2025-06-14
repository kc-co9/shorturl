package com.co.kc.shortening.infrastructure.service.app;


import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.model.cqrs.command.user.*;
import com.co.kc.shortening.application.model.cqrs.dto.SignInDTO;
import com.co.kc.shortening.application.model.cqrs.dto.UserAddDTO;
import com.co.kc.shortening.application.model.cqrs.dto.UserDetailDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserDetailQuery;
import com.co.kc.shortening.application.service.app.UserAppService;
import com.co.kc.shortening.common.exception.NotFoundException;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.user.domain.model.User;
import com.co.kc.shortening.user.domain.model.UserFactory;
import com.co.kc.shortening.user.domain.model.UserId;
import com.co.kc.shortening.user.domain.model.UserRepository;
import com.co.kc.shortening.user.service.PasswordService;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
class UserAppServiceTests {
    @Autowired
    private SessionClient sessionClient;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAppService userAppService;
    @Autowired
    private PasswordService passwordService;


    private UserAddDTO initUser;

    @BeforeEach
    void initUser() {
        UserAddCommand addCommand =
                new UserAddCommand(UserFactory.testUserEmail, UserFactory.testUserName, UserFactory.testUserRawPassword);
        initUser = userAppService.addUser(addCommand);
    }

    @Test
    void testInitUserSignInAndSignOut() {
        SignInCommand signInCommand = new SignInCommand(UserFactory.testUserEmail, UserFactory.testUserRawPassword);
        SignInDTO signInDTO = userAppService.signIn(signInCommand);
        Assertions.assertNotNull(signInDTO);
        Assertions.assertNotNull(signInDTO.getUserId());
        Assertions.assertEquals(initUser.getUserId(), signInDTO.getUserId());
        Assertions.assertNotNull(signInDTO.getToken());
        Assertions.assertNotNull(sessionClient.get(String.valueOf(signInDTO.getUserId())));

        SignOutCommand signOutCommand = new SignOutCommand(signInDTO.getUserId());
        userAppService.signOut(signOutCommand);
        Assertions.assertNull(sessionClient.get(String.valueOf(signInDTO.getUserId())));
    }

    @Test
    void testInitUserDetail() {
        UserDetailQuery userDetailQuery = new UserDetailQuery(initUser.getUserId());
        UserDetailDTO userDetailDTO = userAppService.userDetail(userDetailQuery);
        Assertions.assertEquals(initUser.getUserId(), userDetailDTO.getUserId());
        Assertions.assertEquals(UserFactory.testUserName, userDetailDTO.getUserName());
        Assertions.assertEquals(UserFactory.testUserEmail, userDetailDTO.getUserEmail());
    }

    @Test
    void testUserDetailWhenUserIsNotFounded() {
        UserDetailQuery userDetailQuery = new UserDetailQuery(RandomUtils.nextLong(initUser.getUserId() + 1, initUser.getUserId() + 10));
        Assertions.assertThrows(NotFoundException.class, () -> userAppService.userDetail(userDetailQuery));
    }

    @Test
    void testUpdateInitUserWithChangedEmailAndChangedName() {
        UserUpdateCommand updateCommand =
                new UserUpdateCommand(initUser.getUserId(), UserFactory.testUserChangedEmail, UserFactory.testUserChangedName, UserFactory.testUserRawPassword);
        userAppService.updateUser(updateCommand);

        User user = userRepository.find(new UserId(initUser.getUserId()));
        Assertions.assertEquals(initUser.getUserId(), user.getUserId().getId());
        Assertions.assertEquals(UserFactory.testUserChangedEmail, user.getEmail().getEmail());
        Assertions.assertEquals(UserFactory.testUserChangedName, user.getName().getName());
        Assertions.assertTrue(passwordService.verify(UserFactory.getTestUserRawPassword(), user.getPassword()));
    }

    @Test
    void testUpdateInitUserWithInvalidPassword() {
        UserUpdateCommand updateCommand =
                new UserUpdateCommand(initUser.getUserId(), UserFactory.testUserEmail, UserFactory.testUserName, UserFactory.testUserInvalidRawPassword);
        userAppService.updateUser(updateCommand);

        User user = userRepository.find(new UserId(initUser.getUserId()));
        Assertions.assertEquals(initUser.getUserId(), user.getUserId().getId());
        Assertions.assertEquals(UserFactory.testUserEmail, user.getEmail().getEmail());
        Assertions.assertEquals(UserFactory.testUserName, user.getName().getName());
        Assertions.assertTrue(passwordService.verify(UserFactory.getTestUserRawPassword(), user.getPassword()));
    }

    @Test
    void testUpdateInitUserWithEmptyPassword() {
        UserUpdateCommand updateCommand =
                new UserUpdateCommand(initUser.getUserId(), UserFactory.testUserEmail, UserFactory.testUserName, UserFactory.testUserEmptyRawPassword);
        userAppService.updateUser(updateCommand);

        User user = userRepository.find(new UserId(initUser.getUserId()));
        Assertions.assertEquals(initUser.getUserId(), user.getUserId().getId());
        Assertions.assertEquals(UserFactory.testUserEmail, user.getEmail().getEmail());
        Assertions.assertEquals(UserFactory.testUserName, user.getName().getName());
        Assertions.assertTrue(passwordService.verify(UserFactory.getTestUserRawPassword(), user.getPassword()));
    }

    @Test
    void testUpdateInitUserWithChangedPassword() {
        UserUpdateCommand updateCommand =
                new UserUpdateCommand(initUser.getUserId(), UserFactory.testUserEmail, UserFactory.testUserName, UserFactory.testUserChangedRawPassword);
        userAppService.updateUser(updateCommand);

        User user = userRepository.find(new UserId(initUser.getUserId()));
        Assertions.assertEquals(initUser.getUserId(), user.getUserId().getId());
        Assertions.assertEquals(UserFactory.testUserEmail, user.getEmail().getEmail());
        Assertions.assertEquals(UserFactory.testUserName, user.getName().getName());
        Assertions.assertTrue(passwordService.verify(UserFactory.getTestUserChangedRawPassword(), user.getPassword()));
    }

    @Test
    void testRemoveInitUser() {
        UserRemoveCommand removeCommand = new UserRemoveCommand(initUser.getUserId());
        userAppService.removeUser(removeCommand);

        User user = userRepository.find(new UserId(initUser.getUserId()));
        Assertions.assertNull(user);
    }
}
