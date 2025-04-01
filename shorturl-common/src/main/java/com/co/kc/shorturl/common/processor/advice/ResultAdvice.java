package com.co.kc.shorturl.common.processor.advice;

import com.co.kc.shorturl.common.model.io.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collections;
import java.util.Optional;

/**
 * @author kc
 */
@ConditionalOnWebApplication
@RestControllerAdvice(basePackages = {"com.co.kc.shorturl"})
public class ResultAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result) {
            @SuppressWarnings("unchecked")
            Result<Object> result = (Result<Object>) body;
            result.setData(decorateData(result.getData()));
            return body;
        }
        return Result.success(decorateData(body));
    }

    private Object decorateData(Object data) {
        return Optional.ofNullable(data).orElse(Collections.emptyMap());
    }
}
