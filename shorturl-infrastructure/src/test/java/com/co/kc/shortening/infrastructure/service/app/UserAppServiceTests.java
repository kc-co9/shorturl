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
import com.co.kc.shortening.user.domain.model.UserFactory;
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
    private UserAppService userAppService;

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
    void testUpdatInitUser() {
        UserUpdateCommand updateCommand =
                new UserUpdateCommand(initUser.getUserId(), UserFactory.testUserChangedEmail, UserFactory.testUserChangedName, UserFactory.testUserRawPassword);
        userAppService.updateUser(updateCommand);

        UserDetailQuery userDetailQuery = new UserDetailQuery(initUser.getUserId());
        UserDetailDTO userDetailDTO = userAppService.userDetail(userDetailQuery);
        Assertions.assertEquals(initUser.getUserId(), userDetailDTO.getUserId());
        Assertions.assertEquals(UserFactory.testUserChangedEmail, userDetailDTO.getUserEmail());
        Assertions.assertEquals(UserFactory.testUserChangedName, userDetailDTO.getUserName());
    }

    @Test
    void testRemoveInitUser() {
        UserRemoveCommand removeCommand = new UserRemoveCommand(initUser.getUserId());
        userAppService.removeUser(removeCommand);

        UserDetailQuery userDetailQuery = new UserDetailQuery(initUser.getUserId());
        Assertions.assertThrows(NotFoundException.class, () -> userAppService.userDetail(userDetailQuery));
    }
}
