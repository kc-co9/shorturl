package com.co.kc.shortening.application.service.appservice;

import com.co.kc.shortening.application.model.cqrs.command.user.*;
import com.co.kc.shortening.application.model.cqrs.dto.SignInDTO;
import com.co.kc.shortening.application.model.cqrs.dto.UserDetailDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserDetailQuery;
import com.co.kc.shortening.user.domain.model.*;
import com.co.kc.shortening.common.exception.AuthException;
import org.junit.Assert;
import org.junit.Test;

public class UserAppServiceTests {
    @Test
    public void testSignIn() {
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser();

        SignInCommand command = new SignInCommand(UserFactory.testUserEmail, UserFactory.testUserRawPassword);
        SignInDTO signInDTO = userAppService.signIn(command);
        Assert.assertNotNull(signInDTO);

        UserDetailQuery query = new UserDetailQuery(signInDTO.getUserId());
        UserDetailDTO userDetailDTO = userAppService.userDetail(query);
        Assert.assertNotNull(userDetailDTO);
        Assert.assertEquals(UserFactory.testUserName, userDetailDTO.getUserName());
    }

    @Test
    public void testSignOut() {
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser();

        SignInCommand signInCommand = new SignInCommand(UserFactory.testUserEmail, UserFactory.testUserRawPassword);
        SignInDTO signInDTO = userAppService.signIn(signInCommand);

        SignOutCommand signOutCommand = new SignOutCommand(signInDTO.getUserId());
        userAppService.signOut(signOutCommand);

        try {
            UserDetailQuery query = new UserDetailQuery(signInDTO.getUserId());
            userAppService.userDetail(query);
        } catch (AuthException ex) {
            Assert.assertEquals("用户尚未登陆", ex.getMsg());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testAddUser() {
        UserAppService userAppService = UserAppServiceFactory.createUserAppService();

        UserAddCommand command = new UserAddCommand(UserFactory.testUserEmail, UserFactory.testUserName, UserFactory.testUserRawPassword);
        userAppService.addUser(command);

        SignInDTO signInDTO = userAppService.signIn(new SignInCommand(UserFactory.testUserEmail, UserFactory.testUserRawPassword));
        Assert.assertNotNull(signInDTO);

        UserDetailQuery query = new UserDetailQuery(signInDTO.getUserId());
        UserDetailDTO userDetailDTO = userAppService.userDetail(query);
        Assert.assertNotNull(userDetailDTO);
        Assert.assertEquals(UserFactory.testUserName, userDetailDTO.getUserName());
    }

    @Test
    public void testUpdateUser() {
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser();

        UserUpdateCommand command = new UserUpdateCommand(UserFactory.testUserId, UserFactory.testUserEmail, UserFactory.testUserName, UserFactory.testUserRawPassword);
        userAppService.updateUser(command);

        SignInDTO signInDTO = userAppService.signIn(new SignInCommand(UserFactory.testUserEmail, UserFactory.testUserRawPassword));
        Assert.assertNotNull(signInDTO);

        UserDetailQuery query = new UserDetailQuery(signInDTO.getUserId());
        UserDetailDTO userDetailDTO = userAppService.userDetail(query);
        Assert.assertNotNull(userDetailDTO);
        Assert.assertEquals(UserFactory.testUserId.longValue(), userDetailDTO.getUserId().longValue());
        Assert.assertEquals(UserFactory.testUserName, userDetailDTO.getUserName());
    }

    @Test
    public void testRemoveUser() {
        UserAppService userAppService = UserAppServiceFactory.createUserAppServiceWithTestUser();

        UserRemoveCommand command = new UserRemoveCommand(UserFactory.testUserId);
        userAppService.removeUser(command);

        try {
            userAppService.signIn(new SignInCommand(UserFactory.testUserEmail, UserFactory.testUserRawPassword));
        } catch (AuthException ex) {
            Assert.assertEquals("user is not exist", ex.getMsg());
            return;
        }
        Assert.fail();
    }
}
