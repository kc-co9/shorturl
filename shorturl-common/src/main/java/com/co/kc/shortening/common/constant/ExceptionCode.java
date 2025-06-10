package com.co.kc.shortening.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常码
 * <p>
 * 特殊场景才需要声明异常码，直接输出给接入方。
 *
 * @author kc
 */
@Getter
@AllArgsConstructor
public enum ExceptionCode {
    /**
     * 默认异常码
     */
    DEFAULT(500, "系统繁忙,请稍后重试");

    private final int code;
    private final String msg;
}
