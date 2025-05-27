package com.co.kc.shortening.application.model.cqrs.command.user;

import lombok.Getter;

/**
 * 用户移除Command
 *
 * @author kc
 */
@Getter
public class UserRemoveCommand {
    private final Long userId;

    public UserRemoveCommand(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }
        if (userId <= 0) {
            throw new IllegalArgumentException("userId is less than 0");
        }
        this.userId = userId;
    }
}
