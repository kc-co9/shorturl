package com.co.kc.shortening.admin.security.authentication.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author kcl.co
 * @since 2022/02/19
 */
public class JwtUserGrantedAuthority implements GrantedAuthority {

    private final String permission;

    public JwtUserGrantedAuthority(String permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission;
    }
}
