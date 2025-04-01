package com.co.kc.shorturl.repository.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.co.kc.shorturl.common.model.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author kc
 */
@Getter
@AllArgsConstructor
public enum UrlKeyStatus implements BaseEnum {
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
