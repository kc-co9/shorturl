package com.co.kc.shortening.user.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 权限描述-值对象
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode
public class PermissionDesc {
    private final String desc;

    public PermissionDesc(String desc) {
        if (desc == null) {
            desc = "";
        }
        this.desc = desc;
    }
}
