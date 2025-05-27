package com.co.kc.shortening.user.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户密码-值对象
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode
public class UserPassword {
    private final String password;

    public UserPassword(String password) {
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("password is null or empty");
        }
        this.password = password;
    }
}
