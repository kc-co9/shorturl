package com.co.kc.shortening.user.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 邮箱-值对象
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode
public class UserEmail {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,})?$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private final String email;

    public UserEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("email is null or empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("email is illegal");
        }
        this.email = email;
    }
}
