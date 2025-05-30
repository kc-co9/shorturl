package com.co.kc.shortening.common.exception;

/**
 * 提示异常
 * <p>
 * 主要弹出toast提醒用户
 *
 * @author kc
 */
public class ToastException extends BaseException {
    public ToastException(String msg) {
        super(msg);
    }
}
