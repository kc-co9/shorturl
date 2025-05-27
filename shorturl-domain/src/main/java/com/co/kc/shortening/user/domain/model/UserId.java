package com.co.kc.shortening.user.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 认证用户-唯一标识
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode
public class UserId {

    private final Long id;

    public UserId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("id is less than or equal to 0");
        }
        this.id = id;
    }
}
