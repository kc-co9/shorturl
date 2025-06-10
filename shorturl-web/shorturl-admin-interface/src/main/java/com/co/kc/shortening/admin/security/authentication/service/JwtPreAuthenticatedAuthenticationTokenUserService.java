package com.co.kc.shortening.admin.security.authentication.service;

import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.model.client.SessionDTO;
import com.co.kc.shortening.admin.security.authentication.entity.JwtUserDetails;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collections;
import java.util.Objects;

/**
 * @author kc
 */
public class JwtPreAuthenticatedAuthenticationTokenUserService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    private final SessionClient sessionClient;

    public JwtPreAuthenticatedAuthenticationTokenUserService(SessionClient sessionClient) {
        this.sessionClient = sessionClient;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        if (Objects.isNull(token)) {
            throw new BadCredentialsException("required token");
        }
        if (Objects.isNull(token.getPrincipal())) {
            throw new BadCredentialsException("principal is error");
        }
        SessionDTO user = sessionClient.get(String.valueOf(token.getPrincipal()));
        if (Objects.isNull(user)) {
            throw new BadCredentialsException("user is not exist");
        }
        @SuppressWarnings("UnnecessaryLocalVariable")
        JwtUserDetails jwtUserDetails = new JwtUserDetails(String.valueOf(user.getUserId()), user.getUserName(), Collections.emptyList());
        return jwtUserDetails;
    }
}
