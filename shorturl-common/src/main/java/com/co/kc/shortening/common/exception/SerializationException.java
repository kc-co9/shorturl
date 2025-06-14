package com.co.kc.shortening.common.exception;

import com.co.kc.shortening.common.constant.ErrorCode;

/**
 * 序列化异常
 *
 * @author kc
 */
public class SerializationException extends BaseException {
    public SerializationException(String reason, Throwable throwable) {
        super(ErrorCode.SERIALIZATION_ERROR, reason, throwable);
    }
}
