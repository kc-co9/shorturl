package com.co.kc.shortening.common.exception;

import com.co.kc.shortening.common.constant.ExceptionCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 异常基础类
 *
 * @author kc
 */
@Getter
public class BaseException extends RuntimeException {
    /**
     * 异常码
     */
    private final int code;
    /**
     * 异常消息
     */
    private final String msg;
    /**
     * 异常原因
     */
    private final String reason;

    public BaseException(String msg) {
        this(ExceptionCode.DEFAULT.getCode(), msg, "", null);
    }

    public BaseException(String msg, String reason) {
        this(ExceptionCode.DEFAULT.getCode(), msg, reason, null);
    }

    public BaseException(String msg, Throwable throwable) {
        this(ExceptionCode.DEFAULT.getCode(), msg, "", throwable);
    }

    public BaseException(String msg, String reason, Throwable throwable) {
        this(ExceptionCode.DEFAULT.getCode(), msg, reason, throwable);
    }

    public BaseException(ExceptionCode exCode) {
        this(exCode.getCode(), exCode.getMsg(), "", null);
    }

    public BaseException(ExceptionCode exCode, String reason) {
        this(exCode.getCode(), exCode.getMsg(), reason, null);
    }

    public BaseException(ExceptionCode exCode, Throwable throwable) {
        this(exCode.getCode(), exCode.getMsg(), "", throwable);
    }

    public BaseException(ExceptionCode exCode, String reason, Throwable throwable) {
        this(exCode.getCode(), exCode.getMsg(), reason, throwable);
    }

    public BaseException(int code, String msg, String reason, Throwable throwable) {
        super(buildMessage(code, msg, reason), throwable);
        this.code = code;
        this.msg = msg;
        this.reason = reason;
    }

    private static String buildMessage(int code, String msg, String reason) {
        List<String> messageList = new ArrayList<>();
        messageList.add(String.valueOf(code));
        if (StringUtils.isNotBlank(msg)) {
            messageList.add(msg);
        }
        if (StringUtils.isNotBlank(reason)) {
            messageList.add(reason);
        }
        return String.join(":", messageList);
    }
}
