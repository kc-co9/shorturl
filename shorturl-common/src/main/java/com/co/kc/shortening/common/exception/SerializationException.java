package com.co.kc.shortening.common.exception;

/**
 * 序列化异常
 *
 * @author kc
 */
public class SerializationException extends BaseException {
    public SerializationException(String msg) {
        super(msg);
    }

    public SerializationException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
