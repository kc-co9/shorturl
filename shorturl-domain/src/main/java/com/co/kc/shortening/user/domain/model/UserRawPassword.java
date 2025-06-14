package com.co.kc.shortening.user.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 用户密码-值对象
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode
public class UserRawPassword {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@#$%^&()_+\\-=\\[\\]{}|;:,.<>/?~`]+$");

    private final String rawPassword;

    public UserRawPassword(String rawPassword) {
        if (StringUtils.isBlank(rawPassword)) {
            throw new IllegalArgumentException("password is null or empty");
        }
        if (!PASSWORD_PATTERN.matcher(rawPassword).matches()) {
            throw new IllegalArgumentException("password contains illegal characters");
        }
        this.rawPassword = rawPassword;
    }
}
