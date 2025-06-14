package com.co.kc.shortening.common.exception;

import com.co.kc.shortening.common.constant.ErrorCode;

/**
 * 业务异常
 * <p>
 * 主要用于业务处理异常
 *
 * @author kc
 */
public class BusinessException extends BaseException {
    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(ErrorCode errorCode, String reason) {
        super(errorCode, reason);
    }
}
