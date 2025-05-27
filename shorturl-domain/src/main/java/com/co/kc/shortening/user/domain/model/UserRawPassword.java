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
public class UserRawPassword {
    private final String rawPassword;

    public UserRawPassword(String rawPassword) {
        if (StringUtils.isBlank(rawPassword)) {
            throw new IllegalArgumentException("password is null or empty");
        }
        this.rawPassword = rawPassword;
    }
}
