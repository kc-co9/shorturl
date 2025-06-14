package com.co.kc.shortening.application.service.app;

import com.co.kc.shortening.application.client.MemorySessionClient;
import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.model.cqrs.command.user.*;
import com.co.kc.shortening.application.model.cqrs.dto.SignInDTO;
import com.co.kc.shortening.application.model.cqrs.dto.UserAddDTO;
import com.co.kc.shortening.application.model.cqrs.dto.UserDetailDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserDetailQuery;
import com.co.kc.shortening.user.domain.model.*;
import com.co.kc.shortening.common.exception.AuthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserAppServiceTests {
    @Test
    void testSignIn() {
        SessionClient sessionClient = new MemorySessionClient();
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser(sessionClient);

        SignInCommand command = new SignInCommand(UserFactory.testUserEmail, UserFactory.testUserRawPassword);
        SignInDTO signInDTO = userAppService.signIn(command);
        Assertions.assertNotNull(signInDTO);

        Assertions.assertNotNull(sessionClient.get(String.valueOf(signInDTO.getUserId())));
    }

    @Test
    void testSignInWhenUserIsNotFounded() {
        UserAppService userAppService = UserAppServiceFactory.createUserAppService();
        SignInCommand signInCommand = new SignInCommand(UserFactory.testUserEmail, UserFactory.testUserRawPassword);
        Assertions.assertThrows(AuthException.class, () -> userAppService.signIn(signInCommand));
    }

    @Test
    void testSignOut() {
        SessionClient sessionClient = new MemorySessionClient();
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser(sessionClient);

        SignInCommand signInCommand = new SignInCommand(UserFactory.testUserEmail, UserFactory.testUserRawPassword);
        SignInDTO signInDTO = userAppService.signIn(signInCommand);

        SignOutCommand signOutCommand = new SignOutCommand(signInDTO.getUserId());
        userAppService.signOut(signOutCommand);

        Assertions.assertNull(sessionClient.get(String.valueOf(signInDTO.getUserId())));
    }

    @Test
    void testUserDetail() {
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser();

        UserDetailQuery query = new UserDetailQuery(UserFactory.testUserId);
        UserDetailDTO userDetailDTO = userAppService.userDetail(query);
        Assertions.assertNotNull(userDetailDTO);
        Assertions.assertEquals(UserFactory.testUserId, userDetailDTO.getUserId());
        Assertions.assertEquals(UserFactory.testUserName, userDetailDTO.getUserName());
        Assertions.assertEquals(UserFactory.testUserEmail, userDetailDTO.getUserEmail());
    }

    @Test
    void testAddUser() {
        UserRepository userRepository = new UserMemoryRepository();
        UserAppService userAppService = UserAppServiceFactory.createUserAppService(userRepository);

        UserAddCommand command = new UserAddCommand(UserFactory.testUserEmail, UserFactory.testUserName, UserFactory.testUserRawPassword);
        UserAddDTO userAddDTO = userAppService.addUser(command);

        User user = userRepository.find(new UserId(userAddDTO.getUserId()));
        Assertions.assertEquals(UserFactory.getTestUserName(), user.getName());
        Assertions.assertEquals(UserFactory.getTestUserEmail(), user.getEmail());
        Assertions.assertTrue(UserFactory.testPasswordService.verify(UserFactory.getTestUserRawPassword(), user.getPassword()));
    }

    @Test
    void testUpdateUserWithChangedEmailAndChangedName() {
        UserRepository userRepository = new UserMemoryRepository();
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser(userRepository);

        UserUpdateCommand command = new UserUpdateCommand(UserFactory.testUserId, UserFactory.testUserChangedEmail, UserFactory.testUserChangedName, UserFactory.testUserRawPassword);
        userAppService.updateUser(command);

        User user = userRepository.find(UserFactory.getTestUserId());
        Assertions.assertEquals(UserFactory.getTestUserChangedName(), user.getName());
        Assertions.assertEquals(UserFactory.getTestUserChangedEmail(), user.getEmail());
        Assertions.assertTrue(UserFactory.testPasswordService.verify(UserFactory.getTestUserRawPassword(), user.getPassword()));
    }

    @Test
    void testUpdateUserWithChangedPassword() {
        UserRepository userRepository = new UserMemoryRepository();
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser(userRepository);

        UserUpdateCommand command = new UserUpdateCommand(UserFactory.testUserId, UserFactory.testUserEmail, UserFactory.testUserName, UserFactory.testUserChangedRawPassword);
        userAppService.updateUser(command);

        User user = userRepository.find(UserFactory.getTestUserId());
        Assertions.assertEquals(UserFactory.getTestUserName(), user.getName());
        Assertions.assertEquals(UserFactory.getTestUserEmail(), user.getEmail());
        Assertions.assertTrue(UserFactory.testPasswordService.verify(UserFactory.getTestUserChangedRawPassword(), user.getPassword()));
    }

    @Test
    void testUpdateUserWithInvalidPassword() {
        UserRepository userRepository = new UserMemoryRepository();
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser(userRepository);

        UserUpdateCommand command = new UserUpdateCommand(UserFactory.testUserId, UserFactory.testUserEmail, UserFactory.testUserName, UserFactory.testUserInvalidRawPassword);
        userAppService.updateUser(command);

        User user = userRepository.find(UserFactory.getTestUserId());
        Assertions.assertEquals(UserFactory.getTestUserName(), user.getName());
        Assertions.assertEquals(UserFactory.getTestUserEmail(), user.getEmail());
        Assertions.assertTrue(UserFactory.testPasswordService.verify(UserFactory.getTestUserRawPassword(), user.getPassword()));
    }

    @Test
    void testUpdateUserWithEmptyPassword() {
        UserRepository userRepository = new UserMemoryRepository();
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser(userRepository);

        UserUpdateCommand command = new UserUpdateCommand(UserFactory.testUserId, UserFactory.testUserEmail, UserFactory.testUserName, UserFactory.testUserEmptyRawPassword);
        userAppService.updateUser(command);

        User user = userRepository.find(UserFactory.getTestUserId());
        Assertions.assertEquals(UserFactory.getTestUserName(), user.getName());
        Assertions.assertEquals(UserFactory.getTestUserEmail(), user.getEmail());
        Assertions.assertTrue(UserFactory.testPasswordService.verify(UserFactory.getTestUserRawPassword(), user.getPassword()));
    }

    @Test
    void testRemoveUser() {
        UserRepository userRepository = new UserMemoryRepository();
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser();

        UserRemoveCommand command = new UserRemoveCommand(UserFactory.testUserId);
        userAppService.removeUser(command);

        User user = userRepository.find(UserFactory.getTestUserId());
        Assertions.assertNull(user);
    }
}
