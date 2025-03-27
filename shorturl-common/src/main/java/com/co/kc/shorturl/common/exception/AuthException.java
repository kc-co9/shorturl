package com.co.kc.shorturl.common.exception;

/**
 * 认证异常
 * <p>
 * 主要用于登陆认证异常
 *
 * @author kc
 */
public class AuthException extends BaseException {
    public AuthException(String msg) {
        super(msg);
    }
}
