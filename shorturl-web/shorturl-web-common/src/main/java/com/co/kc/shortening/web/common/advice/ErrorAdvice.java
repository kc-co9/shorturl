package com.co.kc.shortening.web.common.advice;

import com.co.kc.shortening.common.exception.*;
import com.co.kc.shortening.web.common.Result;
import com.co.kc.shortening.web.common.constants.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Optional;

/**
 * @author kc
 */
@RestControllerAdvice
@ResponseStatus(HttpStatus.OK)
public class ErrorAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Map<String, Object>> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return Result.error(ResultCode.PARAMS_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        String message = Optional.ofNullable(ex.getBindingResult().getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse(ResultCode.PARAMS_ERROR.getMsg());
        return Result.error(ResultCode.PARAMS_ERROR, message);
    }

    @ExceptionHandler(HttpException.class)
    public Result<Map<String, Object>> httpExceptionHandler(HttpException ex) {
        return Result.error(ResultCode.NETWORK_ERROR);
    }

    @ExceptionHandler(ToastException.class)
    public Result<Map<String, Object>> toastExceptionHandler(ToastException ex) {
        return Result.error(ResultCode.OPERATE_ERROR, ex);
    }

    @ExceptionHandler(AuthException.class)
    public Result<Map<String, Object>> authExceptionHandler(AuthException ex) {
        return Result.error(ResultCode.AUTH_FAIL, ex);
    }

    @ExceptionHandler(PermissionException.class)
    public Result<Map<String, Object>> permissionExceptionHandler(PermissionException ex) {
        return Result.error(ResultCode.AUTH_DENY, ex);
    }

    @ExceptionHandler(BusinessException.class)
    public Result<Map<String, Object>> businessExceptionHandler(BusinessException ex) {
        return Result.error(ResultCode.BUSINESS_ERROR, ex);
    }

    @ExceptionHandler(Exception.class)
    public Result<Map<String, Object>> exceptionHandler(Exception ex) {
        return Result.error(ResultCode.ERROR);
    }

}
