package com.co.kc.shortening.infrastructure.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.co.kc.shortening.shorturl.domain.model.ShorturlStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

    public static ShorturlStatus convert(UrlMappingStatus status) {
        if (ACTIVE.equals(status)) {
            return ShorturlStatus.ONLINE;
        } else if (INVALID.equals(status)) {
            return ShorturlStatus.OFFLINE;
        } else {
            return null;
        }
    }

    public static UrlMappingStatus convert(ShorturlStatus status) {
        if (ShorturlStatus.ONLINE.equals(status)) {
            return UrlMappingStatus.ACTIVE;
        } else {
            return UrlMappingStatus.INVALID;
        }
    }
}
