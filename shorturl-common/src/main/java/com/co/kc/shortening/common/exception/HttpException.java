package com.co.kc.shortening.common.exception;

/**
 * HTTP请求异常
 * <p>
 * 主要用于HTTP请求异常
 *
 * @author kc
 */
public class HttpException extends BaseException {
    public HttpException(String msg) {
        super(msg);
    }
}
