package com.co.kc.shortening.admin.advice;

import com.co.kc.shortening.common.constant.ErrorCode;
import com.co.kc.shortening.common.exception.*;
import com.co.kc.shortening.web.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
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
        return Result.error(ErrorCode.PARAMS_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        String message = Optional.ofNullable(ex.getBindingResult().getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse(ErrorCode.PARAMS_ERROR.getMsg());
        return Result.error(ErrorCode.PARAMS_ERROR, message);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Result<Map<String, Object>> authExceptionHandler(BadCredentialsException ex) {
        return Result.error(ErrorCode.AUTH_FAIL, ex.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    public Result<Map<String, Object>> baseExceptionHandler(BaseException ex) {
        return Result.error(ex);
    }

    @ExceptionHandler(Exception.class)
    public Result<Map<String, Object>> exceptionHandler(Exception ex) {
        return Result.error(ErrorCode.SYS_ERROR);
    }

}
