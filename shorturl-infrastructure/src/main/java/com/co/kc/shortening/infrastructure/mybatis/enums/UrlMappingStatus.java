package com.co.kc.shortening.infrastructure.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
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
}
