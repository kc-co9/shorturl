package com.co.kc.shortening.admin.security.authentication.holder;

import com.co.kc.shortening.admin.security.authentication.entity.JwtUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Optional;

/**
 * @author kc
 */
public class AdministratorHolder {

    private AdministratorHolder() {
    }

    public static Long getAdministratorId() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(PreAuthenticatedAuthenticationToken.class::isInstance)
                .map(Authentication::getPrincipal)
                .map(JwtUserDetails.class::cast)
                .map(JwtUserDetails::getPrincipal)
                .map(Long::parseLong)
                .orElse(-1L);
    }


    public static String getAdministratorName() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(PreAuthenticatedAuthenticationToken.class::isInstance)
                .map(Authentication::getPrincipal)
                .map(JwtUserDetails.class::cast)
                .map(JwtUserDetails::getUsername)
                .orElse("");
    }
}
