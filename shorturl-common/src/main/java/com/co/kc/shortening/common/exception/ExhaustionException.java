package com.co.kc.shortening.common.exception;

import com.co.kc.shortening.common.constant.ErrorCode;

/**
 * 资源耗尽异常
 *
 * @author kc
 */
public class ExhaustionException extends BaseException {
    public ExhaustionException(String reason) {
        super(ErrorCode.EXHAUSTION_ERROR, reason);
    }
}
