package com.co.kc.shortening.infrastructure.config.bean;

import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.client.TokenClient;
import com.co.kc.shortening.application.service.app.UserAppService;
import com.co.kc.shortening.infrastructure.client.id.SnowflakeId;
import com.co.kc.shortening.infrastructure.client.id.bizid.UserIdClient;
import com.co.kc.shortening.infrastructure.client.session.RedisSessionClient;
import com.co.kc.shortening.infrastructure.client.token.JwtTokenClient;
import com.co.kc.shortening.infrastructure.mybatis.service.AdministratorService;
import com.co.kc.shortening.infrastructure.repository.permission.PermissionMySqlRepository;
import com.co.kc.shortening.infrastructure.repository.role.RoleMySqlRepository;
import com.co.kc.shortening.infrastructure.repository.user.UserMySqlRepository;
import com.co.kc.shortening.infrastructure.service.domain.BcryptPasswordService;
import com.co.kc.shortening.infrastructure.service.query.UserMySqlQueryService;
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
    public UserIdClient userIdClient(SnowflakeId snowflakeId) {
        return new UserIdClient(snowflakeId);
    }

    @Bean
    public RedisSessionClient sessionClient(RedisTemplate<String, String> sessionRedisTemplate) {
        return new RedisSessionClient(sessionRedisTemplate);
    }

    @Bean
    public JwtTokenClient tokenClient() {
        return new JwtTokenClient();
    }

    @Bean
    public UserMySqlRepository userMySqlRepository(AdministratorService administratorService) {
        return new UserMySqlRepository(administratorService);
    }

    @Bean
    public RoleMySqlRepository roleMySqlRepository() {
        return new RoleMySqlRepository();
    }

    @Bean
    public PermissionMySqlRepository permissionMySqlRepository() {
        return new PermissionMySqlRepository();
    }

    @Bean
    public UserService userService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return new UserService(roleRepository, permissionRepository);
    }

    @Bean
    public UserMySqlQueryService userQueryMySqlService(AdministratorService administratorService) {
        return new UserMySqlQueryService(administratorService);
    }

    @Bean
    public BcryptPasswordService bcryptPasswordService() {
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
