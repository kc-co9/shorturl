package com.co.kc.shortening.common.exception;

/**
 * 权限异常
 * <p>
 * 主要用于权限认证异常
 *
 * @author kc
 */
public class PermissionException extends BaseException {
    public PermissionException(String msg) {
        super(msg);
    }
}
