package com.co.kc.shortening.user.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * 权限-实体
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode
public class PermissionValue {
    private final String value;

    public PermissionValue(String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("value is null or empty");
        }
        this.value = value;
    }
}
