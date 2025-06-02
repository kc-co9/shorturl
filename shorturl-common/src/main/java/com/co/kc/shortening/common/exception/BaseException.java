package com.co.kc.shortening.common.exception;

import lombok.Getter;

/**
 * 异常基础类
 *
 * @author kc
 */
@Getter
public class BaseException extends RuntimeException {
    /**
     * 异常码
     */
    protected int code;
    /**
     * 异常消息
     */
    protected String msg;
    /**
     * 异常原因
     */
    protected String reason;

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
