package com.co.kc.shortening.common.exception;

import com.co.kc.shortening.common.constant.ErrorCode;

/**
 * 反射异常
 *
 * @author kc
 */
public class ReflectException extends BaseException {
    public ReflectException(String reason, Throwable throwable) {
        super(ErrorCode.REFLECT_ERROR, reason, throwable);
    }
}
