package com.co.kc.shortening.admin.mock;

import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.client.TokenClient;
import com.co.kc.shortening.application.model.client.SessionDTO;
import com.co.kc.shortening.application.model.client.TokenDTO;
import com.co.kc.shortening.user.domain.model.RoleFactory;
import com.co.kc.shortening.user.domain.model.UserFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class SecurityMock {

    public static void mockTestUserHasAuthenticated(TokenClient tokenClient, SessionClient sessionClient) {
        doAnswer(invocation -> {
            String token = invocation.getArgument(0);
            if (!UserFactory.testUserToken.equals(token)) {
                return null;
            }
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setUserId(UserFactory.testUserId);
            tokenDTO.setCreateTime(LocalDateTime.now());
            return tokenDTO;
        }).when(tokenClient).parse(any(String.class));

        doAnswer(invocation -> {
            String sessionId = invocation.getArgument(0);
            if (!String.valueOf(UserFactory.testUserId).equals(sessionId)) {
                return null;
            }
            SessionDTO sessionDTO = new SessionDTO();
            sessionDTO.setUserId(UserFactory.testUserId);
            sessionDTO.setUserName(UserFactory.testUserName);
            sessionDTO.setUserEmail(UserFactory.testUserEmail);
            sessionDTO.setRoleIdList(UserFactory.testUserRoleIds);
            sessionDTO.setPermissionIdList(RoleFactory.testPermissionIds);
            sessionDTO.setPermissionValueList(RoleFactory.testPermissionValueList);
            return sessionDTO;
        }).when(sessionClient).get(any(String.class));
    }

    public static void mockTestUserAuthenticationHasExpired(TokenClient tokenClient, SessionClient sessionClient) {
        doAnswer(invocation -> {
            String token = invocation.getArgument(0);
            if (!UserFactory.testUserToken.equals(token)) {
                return null;
            }
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setUserId(UserFactory.testUserId);
            tokenDTO.setCreateTime(LocalDateTime.now());
            return tokenDTO;
        }).when(tokenClient).parse(any(String.class));

        doReturn(null).when(sessionClient).get(any(String.class));
    }


}
