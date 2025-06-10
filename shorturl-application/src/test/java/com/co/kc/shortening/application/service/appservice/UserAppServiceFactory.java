package com.co.kc.shortening.application.service.appservice;

import com.co.kc.shortening.application.client.*;
import com.co.kc.shortening.user.domain.model.*;
import com.co.kc.shortening.user.service.AuthService;
import com.co.kc.shortening.user.service.UserService;

public class UserAppServiceFactory {

    public static UserAppService createUserAppService() {
        UserRepository userRepository = new UserMemoryRepository();
        RoleRepository roleRepository = new RoleMemoryRepository();
        PermissionRepository permissionRepository = new PermissionMemoryRepository();

        UserService userService = new UserService(roleRepository, permissionRepository);
        AuthService authService = new AuthService(userRepository, UserFactory.testPasswordService);

        IdClient<Long> userIdClient = new RandomIdClient();
        SessionClient sessionClient = new MemorySessionClient();
        TokenClient tokenClient = new MemoryTokenClient();

        return new UserAppService(
                userRepository,
                authService,
                userService,
                UserFactory.testPasswordService,
                userIdClient,
                sessionClient,
                tokenClient);
    }

    public static UserAppService createUserAppServiceWithTestUser() {
        UserRepository userRepository = new UserMemoryRepository();
        RoleRepository roleRepository = new RoleMemoryRepository();
        PermissionRepository permissionRepository = new PermissionMemoryRepository();

        UserService userService = new UserService(roleRepository, permissionRepository);
        AuthService authService = new AuthService(userRepository, UserFactory.testPasswordService);

        IdClient<Long> userIdClient = new RandomIdClient();
        SessionClient sessionClient = new MemorySessionClient();
        TokenClient tokenClient = new MemoryTokenClient();

        User user = UserFactory.createTestUser();
        userRepository.save(user);

        return new UserAppService(
                userRepository,
                authService,
                userService,
                UserFactory.testPasswordService,
                userIdClient,
                sessionClient,
                tokenClient);
    }
}
