package com.co.kc.shortening.application.service.app;

import com.co.kc.shortening.application.assembler.SessionDtoAssembler;
import com.co.kc.shortening.application.assembler.SignInDtoAssembler;
import com.co.kc.shortening.application.assembler.TokenDtoAssembler;
import com.co.kc.shortening.application.assembler.UserDetailDtoAssembler;
import com.co.kc.shortening.application.client.TokenClient;
import com.co.kc.shortening.application.model.client.TokenDTO;
import com.co.kc.shortening.application.model.cqrs.command.user.*;
import com.co.kc.shortening.application.model.cqrs.dto.UserAddDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserDetailQuery;
import com.co.kc.shortening.application.model.cqrs.dto.SignInDTO;
import com.co.kc.shortening.application.model.cqrs.dto.UserDetailDTO;
import com.co.kc.shortening.application.model.client.SessionDTO;
import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.common.exception.NotFoundException;
import com.co.kc.shortening.user.domain.model.*;
import com.co.kc.shortening.user.service.AuthService;
import com.co.kc.shortening.user.service.PasswordService;
import com.co.kc.shortening.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 认证-应用服务
 *
 * @author kc
 */
public class UserAppService {
    private final UserRepository userRepository;

    private final AuthService authService;
    private final UserService userService;
    private final PasswordService passwordService;

    private final IdClient<Long> userIdClient;
    private final SessionClient sessionClient;
    private final TokenClient tokenClient;

    public UserAppService(UserRepository userRepository,
                          AuthService authService,
                          UserService userService,
                          PasswordService passwordService,
                          IdClient<Long> userIdClient,
                          SessionClient sessionClient,
                          TokenClient tokenClient) {
        this.userRepository = userRepository;

        this.authService = authService;
        this.userService = userService;
        this.passwordService = passwordService;

        this.userIdClient = userIdClient;
        this.sessionClient = sessionClient;
        this.tokenClient = tokenClient;
    }

    public SignInDTO signIn(SignInCommand command) {
        UserEmail email = new UserEmail(command.getEmail());
        UserRawPassword rawPassword = new UserRawPassword(command.getPassword());

        User user = authService.authenticate(email, rawPassword);

        List<Role> roleList = userService.getRoleList(user);
        List<Permission> permissionList = userService.getPermissionList(user);

        String sessionId = String.valueOf(user.getUserId().getId());
        SessionDTO sessionDTO = SessionDtoAssembler.userToDTO(user, roleList, permissionList);
        sessionClient.save(sessionId, sessionDTO, 7L, TimeUnit.DAYS);

        TokenDTO tokenDTO = TokenDtoAssembler.userToDTO(user);
        String token = tokenClient.create(tokenDTO);

        return SignInDtoAssembler.userTokenToDTO(user, token);
    }

    public void signOut(SignOutCommand command) {
        String sessionId = String.valueOf(command.getUserId());
        sessionClient.remove(sessionId);
    }

    public UserDetailDTO userDetail(UserDetailQuery query) {
        UserId userId = new UserId(query.getUserId());

        User user = userRepository.find(userId);
        if (user == null) {
            throw new NotFoundException("用户不存在");
        }

        return UserDetailDtoAssembler.userToDTO(user);
    }

    public UserAddDTO addUser(UserAddCommand command) {
        UserId userId = new UserId(userIdClient.next());
        UserEmail userEmail = new UserEmail(command.getEmail());
        UserName userName = new UserName(command.getUsername());
        UserRawPassword userRawPassword = new UserRawPassword(command.getPassword());
        UserPassword userPassword = passwordService.encrypt(userRawPassword);

        User user = new User(userId, userEmail, userName, userPassword, Collections.emptyList());
        userRepository.save(user);

        return new UserAddDTO(userId.getId());
    }

    public void updateUser(UserUpdateCommand command) {
        UserId userId = new UserId(command.getUserId());
        User user = userRepository.find(userId);
        if (user == null) {
            throw new NotFoundException("用户不存在");
        }

        UserEmail userEmail = new UserEmail(command.getEmail());
        UserName userName = new UserName(command.getUsername());
        UserRawPassword userRawPassword = new UserRawPassword(command.getPassword());
        UserPassword userPassword = passwordService.encrypt(userRawPassword);

        user.changeEmail(userEmail);
        user.changeUserName(userName);
        user.changePassword(userPassword);
        userRepository.save(user);
    }

    public void removeUser(UserRemoveCommand command) {
        UserId userId = new UserId(command.getUserId());
        User user = userRepository.find(userId);
        if (user == null) {
            throw new NotFoundException("用户不存在");
        }

        userRepository.remove(user);
    }
}
