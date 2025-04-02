package com.co.kc.shorturl.admin.security.authentication.service;

import com.co.kc.shorturl.admin.security.authentication.entity.JwtUserDetails;
import com.co.kc.shorturl.repository.dao.AdministratorRepository;
import com.co.kc.shorturl.repository.po.entity.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

/**
 * @author kc
 */
@Component
@SuppressWarnings("AlibabaServiceOrDaoClassShouldEndWithImpl")
public class JwtPreAuthenticatedAuthenticationTokenUserService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        if (Objects.isNull(token)) {
            throw new BadCredentialsException("required token");
        }
        if (Objects.isNull(token.getPrincipal())) {
            throw new BadCredentialsException("principal is error");
        }
        Administrator user = administratorRepository.findById(Long.parseLong(String.valueOf(token.getPrincipal()))).orElse(null);
        if (Objects.isNull(user)) {
            throw new BadCredentialsException("user is not exist");
        }
        @SuppressWarnings("UnnecessaryLocalVariable")
        JwtUserDetails jwtUserDetails = new JwtUserDetails(String.valueOf(user.getId()), user.getUsername(), Collections.emptyList());
        return jwtUserDetails;
    }
}
