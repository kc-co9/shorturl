package com.co.kc.shortening.web.common;

import com.co.kc.shortening.common.exception.BaseException;
import com.co.kc.shortening.web.common.constants.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

/**
 * @author kc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * result code
     */
    private Integer code;
    /**
     * result additional info
     */
    private String msg;
    /**
     * result content
     */
    private T data;

    public static Result<Map<String, Object>> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), Collections.emptyMap());
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static Result<Map<String, Object>> error() {
        return new Result<>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg(), Collections.emptyMap());
    }

    public static Result<Map<String, Object>> error(ResultCode errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMsg(), Collections.emptyMap());
    }

    public static Result<Map<String, Object>> error(ResultCode errorCode, String msg) {
        return new Result<>(errorCode.getCode(), msg, Collections.emptyMap());
    }

    public static Result<Map<String, Object>> error(ResultCode errorCode, BaseException ex) {
        return new Result<>(errorCode.getCode(), ex.getMsg(), Collections.emptyMap());
    }

    public static <T> Result<T> error(T data) {
        return new Result<>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg(), data);
    }

    public static <T> Result<T> error(ResultCode errorCode, T data) {
        return new Result<>(errorCode.getCode(), errorCode.getMsg(), data);
    }

}
