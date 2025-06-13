package com.co.kc.shortening.infrastructure.client.session;

import com.co.kc.shortening.application.model.client.SessionDTO;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.user.domain.model.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class RedisSessionClientTests {
    @Autowired
    private RedisSessionClient redisSessionClient;

    @AfterEach
    void clearSession() {
        String sessionId = String.valueOf(UserFactory.testUserId);
        redisSessionClient.remove(sessionId);
    }

    @Test
    void testGetSavedSession() {
        SessionDTO newSessionDTO = new SessionDTO();
        newSessionDTO.setUserId(UserFactory.testUserId);
        newSessionDTO.setUserName(UserFactory.testUserName);
        newSessionDTO.setUserEmail(UserFactory.testUserEmail);
        newSessionDTO.setRoleIdList(UserFactory.testUserRoleIds);
        newSessionDTO.setPermissionIdList(Collections.emptyList());
        newSessionDTO.setPermissionValueList(Collections.emptyList());

        String sessionId = String.valueOf(UserFactory.testUserId);
        redisSessionClient.save(sessionId, newSessionDTO, 1L, TimeUnit.HOURS);

        SessionDTO sessionDTO = redisSessionClient.get(sessionId);
        Assertions.assertEquals(UserFactory.testUserId, sessionDTO.getUserId());
        Assertions.assertEquals(UserFactory.testUserName, sessionDTO.getUserName());
        Assertions.assertEquals(UserFactory.testUserEmail, sessionDTO.getUserEmail());
        Assertions.assertEquals(UserFactory.testUserRoleIds, sessionDTO.getRoleIdList());
        Assertions.assertEquals(Collections.emptyList(), sessionDTO.getPermissionIdList());
        Assertions.assertEquals(Collections.emptyList(), sessionDTO.getPermissionValueList());
    }

    @Test
    void testGetRemovedSession() {
        SessionDTO newSessionDTO = new SessionDTO();
        newSessionDTO.setUserId(UserFactory.testUserId);
        newSessionDTO.setUserName(UserFactory.testUserName);
        newSessionDTO.setUserEmail(UserFactory.testUserEmail);
        newSessionDTO.setRoleIdList(UserFactory.testUserRoleIds);
        newSessionDTO.setPermissionIdList(Collections.emptyList());
        newSessionDTO.setPermissionValueList(Collections.emptyList());

        String sessionId = String.valueOf(UserFactory.testUserId);
        redisSessionClient.save(sessionId, newSessionDTO, 1L, TimeUnit.HOURS);

        redisSessionClient.remove(sessionId);

        SessionDTO sessionDTO = redisSessionClient.get(sessionId);
        Assertions.assertNull(sessionDTO);
    }

    @Test
    void testGetNullSession() {
        String sessionId = String.valueOf(UserFactory.testUserId);
        SessionDTO sessionDTO = redisSessionClient.get(sessionId);
        Assertions.assertNull(sessionDTO);
    }
}
