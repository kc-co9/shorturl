package com.co.kc.shortening.user.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 角色-唯一标识
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode
public class RoleId {
    private final Long id;

    public RoleId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("id is less than or equal to 0");
        }
        this.id = id;
    }
}
