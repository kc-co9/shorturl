package com.co.kc.shortening.application.model.cqrs.command.user;

import lombok.Getter;

/**
 * 用户更新Command
 *
 * @author kc
 */
@Getter
public class UserUpdateCommand {
    /**
     * 用户ID
     */
    private final Long userId;
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

    public UserUpdateCommand(Long userId, String email, String username, String password) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
