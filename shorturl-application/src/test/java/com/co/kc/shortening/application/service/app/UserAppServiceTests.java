package com.co.kc.shortening.application.service.app;

import com.co.kc.shortening.application.client.MemorySessionClient;
import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.model.cqrs.command.user.*;
import com.co.kc.shortening.application.model.cqrs.dto.SignInDTO;
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
    void testAddUser() {
        UserAppService userAppService = UserAppServiceFactory.createUserAppService();

        UserAddCommand command = new UserAddCommand(UserFactory.testUserEmail, UserFactory.testUserName, UserFactory.testUserRawPassword);
        userAppService.addUser(command);

        SignInDTO signInDTO = userAppService.signIn(new SignInCommand(UserFactory.testUserEmail, UserFactory.testUserRawPassword));
        Assertions.assertNotNull(signInDTO);

        UserDetailQuery query = new UserDetailQuery(signInDTO.getUserId());
        UserDetailDTO userDetailDTO = userAppService.userDetail(query);
        Assertions.assertNotNull(userDetailDTO);
        Assertions.assertEquals(UserFactory.testUserName, userDetailDTO.getUserName());
        Assertions.assertEquals(UserFactory.testUserEmail, userDetailDTO.getUserEmail());
    }

    @Test
    void testUpdateUser() {
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser();

        UserUpdateCommand command = new UserUpdateCommand(UserFactory.testUserId, UserFactory.testUserChangedEmail, UserFactory.testUserChangedName, UserFactory.testUserRawPassword);
        userAppService.updateUser(command);

        SignInDTO signInDTO = userAppService.signIn(new SignInCommand(UserFactory.testUserEmail, UserFactory.testUserRawPassword));
        Assertions.assertNotNull(signInDTO);

        UserDetailQuery query = new UserDetailQuery(signInDTO.getUserId());
        UserDetailDTO userDetailDTO = userAppService.userDetail(query);
        Assertions.assertNotNull(userDetailDTO);
        Assertions.assertEquals(UserFactory.testUserId.longValue(), userDetailDTO.getUserId().longValue());
        Assertions.assertEquals(UserFactory.testUserChangedName, userDetailDTO.getUserName());
        Assertions.assertEquals(UserFactory.testUserChangedEmail, userDetailDTO.getUserEmail());
    }

    @Test
    void testRemoveUser() {
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser();

        UserRemoveCommand command = new UserRemoveCommand(UserFactory.testUserId);
        userAppService.removeUser(command);

        SignInCommand signInCommand = new SignInCommand(UserFactory.testUserEmail, UserFactory.testUserRawPassword);
        Assertions.assertThrows(AuthException.class, () -> userAppService.signIn(signInCommand));
    }
}
