package com.co.kc.shortening.application.model.cqrs.command.user;

import lombok.Getter;

/**
 * 用户新增Command
 *
 * @author kc
 */
@Getter
public class UserAddCommand {
    /**
     * 邮箱
     */
    private final String email;

    /**
     * 用户名
     */
    private final String username;

    /**
     * 密码
     */
    private final String password;

    public UserAddCommand(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
