package com.co.kc.shortening.application.service.appservice;

import com.co.kc.shortening.application.client.*;
import com.co.kc.shortening.application.model.cqrs.command.user.*;
import com.co.kc.shortening.application.model.cqrs.dto.SignInDTO;
import com.co.kc.shortening.application.model.cqrs.dto.UserDetailDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserDetailQuery;
import com.co.kc.shortening.user.domain.model.*;
import com.co.kc.shortening.user.service.AuthService;
import com.co.kc.shortening.user.service.BcryptPasswordService;
import com.co.kc.shortening.user.service.PasswordService;
import com.co.kc.shortening.user.service.UserService;
import com.co.kc.shorturl.common.exception.AuthException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class UserAppServiceTests {
    private UserAppService userAppService;

    @Before
    public void initUserAppService() {
        UserRepository userRepository = new UserMemoryRepository();
        RoleRepository roleRepository = new RoleMemoryRepository();
        PermissionRepository permissionRepository = new PermissionMemoryRepository();

        PasswordService passwordService = new BcryptPasswordService();
        UserService userService = new UserService(roleRepository, permissionRepository);
        AuthService authService = new AuthService(userRepository, passwordService);

        IdClient<Long> userIdClient = new RandomIdClient();
        SessionClient sessionClient = new MemorySessionClient();
        TokenClient tokenClient = new JwtTokenClient();

        UserPassword userPassword = passwordService.encrypt(new UserRawPassword("testPassword"));
        User user = new User(
                new UserId(1L),
                new UserEmail("testName@test.com"),
                new UserName("testName"),
                userPassword,
                Collections.emptyList());
        userRepository.save(user);

        userAppService = new UserAppService(userRepository, authService, userService, passwordService, userIdClient, sessionClient, tokenClient);
    }

    @Test
    public void testSignIn() {
        SignInCommand command = new SignInCommand("testName@test.com", "testPassword");
        SignInDTO signInDTO = userAppService.signIn(command);
        Assert.assertNotNull(signInDTO);


        UserDetailQuery query = new UserDetailQuery(signInDTO.getUserId());
        UserDetailDTO userDetailDTO = userAppService.userDetail(query);
        Assert.assertNotNull(userDetailDTO);
        Assert.assertEquals("testName", userDetailDTO.getUserName());
    }

    @Test
    public void testSignOut() {
        SignInCommand signInCommand = new SignInCommand("testName@test.com", "testPassword");
        SignInDTO signInDTO = userAppService.signIn(signInCommand);

        SignOutCommand signOutCommand = new SignOutCommand(signInDTO.getUserId());
        userAppService.signOut(signOutCommand);

        try {
            UserDetailQuery query = new UserDetailQuery(signInDTO.getUserId());
            userAppService.userDetail(query);
        } catch (AuthException ex) {
            Assert.assertEquals("用户尚未登陆", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testAddUser() {
        UserAddCommand command = new UserAddCommand("testName1@test.com", "testName1", "testPassword1");
        userAppService.addUser(command);

        SignInDTO signInDTO = userAppService.signIn(new SignInCommand("testName1@test.com", "testPassword1"));
        Assert.assertNotNull(signInDTO);

        UserDetailQuery query = new UserDetailQuery(signInDTO.getUserId());
        UserDetailDTO userDetailDTO = userAppService.userDetail(query);
        Assert.assertNotNull(userDetailDTO);
        Assert.assertEquals("testName1", userDetailDTO.getUserName());
    }

    @Test
    public void testUpdateUser() {
        UserUpdateCommand command = new UserUpdateCommand(1L, "testName1@test.com", "testName1", "testPassword1");
        userAppService.updateUser(command);

        SignInDTO signInDTO = userAppService.signIn(new SignInCommand("testName1@test.com", "testPassword1"));
        Assert.assertNotNull(signInDTO);

        UserDetailQuery query = new UserDetailQuery(signInDTO.getUserId());
        UserDetailDTO userDetailDTO = userAppService.userDetail(query);
        Assert.assertNotNull(userDetailDTO);
        Assert.assertEquals(1L, userDetailDTO.getUserId().longValue());
        Assert.assertEquals("testName1", userDetailDTO.getUserName());
    }

    @Test
    public void testRemoveUser() {
        UserRemoveCommand command = new UserRemoveCommand(1L);
        userAppService.removeUser(command);

        try {
            userAppService.signIn(new SignInCommand("testName@test.com", "testPassword"));
        } catch (AuthException ex) {
            Assert.assertEquals("user is not exist", ex.getMessage());
            return;
        }
        Assert.fail();
    }
}
