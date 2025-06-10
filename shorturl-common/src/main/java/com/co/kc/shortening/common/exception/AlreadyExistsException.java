package com.co.kc.shortening.common.exception;

/**
 * 已经存在异常
 *
 * @author kc
 */
public class AlreadyExistsException extends BaseException {
    public AlreadyExistsException(String msg) {
        super(msg);
    }
}
