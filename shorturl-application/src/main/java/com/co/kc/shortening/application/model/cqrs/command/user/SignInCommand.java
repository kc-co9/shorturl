package com.co.kc.shortening.application.model.cqrs.command.user;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户认证command
 *
 * @author kc
 */
@Getter
public class SignInCommand {
    private final String email;
    private final String password;

    public SignInCommand(String email, String password) {
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("帐号为空");
        }
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("密码为空");
        }
        this.email = email;
        this.password = password;
    }
}
