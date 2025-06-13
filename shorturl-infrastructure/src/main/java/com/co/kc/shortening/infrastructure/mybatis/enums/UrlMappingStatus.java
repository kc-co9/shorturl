package com.co.kc.shortening.infrastructure.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.co.kc.shortening.shorturl.domain.model.ShorturlStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * @author kc
 */
@Getter
@AllArgsConstructor
public enum UrlMappingStatus {
    /**
     * 未知
     */
    NONE(0),
    /**
     * 激活
     */
    ACTIVE(1),
    /**
     * 失效
     */
    INVALID(2),
    ;
    @EnumValue
    private final Integer code;

    public static Optional<ShorturlStatus> convert(UrlMappingStatus status) {
        if (ACTIVE.equals(status)) {
            return Optional.of(ShorturlStatus.ONLINE);
        } else if (INVALID.equals(status)) {
            return Optional.of(ShorturlStatus.OFFLINE);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<UrlMappingStatus> convert(ShorturlStatus status) {
        if (ShorturlStatus.ONLINE.equals(status)) {
            return Optional.of(UrlMappingStatus.ACTIVE);
        } else if (ShorturlStatus.OFFLINE.equals(status)) {
            return Optional.of(UrlMappingStatus.INVALID);
        } else {
            return Optional.empty();
        }
    }
}
