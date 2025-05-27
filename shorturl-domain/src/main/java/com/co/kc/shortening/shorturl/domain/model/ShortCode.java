package com.co.kc.shortening.shorturl.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * 短链Code-值对象
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode
public class ShortCode {

    private final String code;

    public ShortCode(String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("code is null");
        }
        this.code = code;
    }
}
