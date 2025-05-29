package com.co.kc.shortening.infrastructure.config.bean;

import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.client.TokenClient;
import com.co.kc.shortening.application.service.appservice.UserAppService;
import com.co.kc.shortening.application.service.queryservice.UserQueryService;
import com.co.kc.shortening.infrastructure.client.id.bizid.UserIdClient;
import com.co.kc.shortening.infrastructure.client.session.RedisSessionClient;
import com.co.kc.shortening.infrastructure.client.token.JwtTokenClient;
import com.co.kc.shortening.infrastructure.mybatis.service.AdministratorService;
import com.co.kc.shortening.infrastructure.repository.PermissionMySqlRepository;
import com.co.kc.shortening.infrastructure.repository.RoleMySqlRepository;
import com.co.kc.shortening.infrastructure.repository.UserMySqlRepository;
import com.co.kc.shortening.infrastructure.service.domain.BcryptPasswordService;
import com.co.kc.shortening.infrastructure.service.query.UserQueryMySqlService;
import com.co.kc.shortening.user.domain.model.PermissionRepository;
import com.co.kc.shortening.user.domain.model.RoleRepository;
import com.co.kc.shortening.user.domain.model.UserRepository;
import com.co.kc.shortening.user.service.AuthService;
import com.co.kc.shortening.user.service.PasswordService;
import com.co.kc.shortening.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author kc
 */
@Configuration
public class UserConfig {

    @Bean
    public UserIdClient userIdClient() {
        return new UserIdClient();
    }

    @Bean
    public SessionClient sessionClient(RedisTemplate<String, String> sessionRedisTemplate) {
        return new RedisSessionClient(sessionRedisTemplate);
    }

    @Bean
    public TokenClient tokenClient() {
        return new JwtTokenClient();
    }

    @Bean
    public UserRepository userRepository(AdministratorService administratorService) {
        return new UserMySqlRepository(administratorService);
    }

    @Bean
    public RoleRepository roleRepository() {
        return new RoleMySqlRepository();
    }

    @Bean
    public PermissionRepository permissionRepository() {
        return new PermissionMySqlRepository();
    }

    @Bean
    public UserService userService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return new UserService(roleRepository, permissionRepository);
    }

    @Bean
    public UserQueryService userQueryService(AdministratorService administratorService) {
        return new UserQueryMySqlService(administratorService);
    }

    @Bean
    public PasswordService passwordService() {
        return new BcryptPasswordService();
    }

    @Bean
    public AuthService authService(UserRepository userRepository, PasswordService passwordService) {
        return new AuthService(userRepository, passwordService);
    }

    @Bean
    public UserAppService userAppService(UserRepository userRepository,
                                         AuthService authService,
                                         UserService userService,
                                         PasswordService passwordService,
                                         IdClient<Long> userIdClient,
                                         SessionClient sessionClient,
                                         TokenClient tokenClient
    ) {
        return new UserAppService(
                userRepository,
                authService,
                userService,
                passwordService,
                userIdClient,
                sessionClient,
                tokenClient);
    }
}
