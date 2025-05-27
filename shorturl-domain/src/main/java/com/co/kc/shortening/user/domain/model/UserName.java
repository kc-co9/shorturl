package com.co.kc.shortening.user.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户名称-值对象
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode
public class UserName {
    private final String name;

    public UserName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("name is null or empty");
        }
        this.name = name;
    }
}
