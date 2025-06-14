package com.co.kc.shortening.common.exception;

import com.co.kc.shortening.common.constant.ErrorCode;

/**
 * 不存在异常
 *
 * @author kc
 */
public class NotFoundException extends BaseException {
    public NotFoundException(String reason) {
        super(ErrorCode.NOT_FOUND, reason);
    }
}
