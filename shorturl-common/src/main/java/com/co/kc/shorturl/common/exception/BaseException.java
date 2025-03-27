package com.co.kc.shorturl.common.exception;

import lombok.Getter;

/**
 * 异常基础类
 *
 * @author kc
 */
@Getter
public class BaseException extends RuntimeException {
    protected final String msg;

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
