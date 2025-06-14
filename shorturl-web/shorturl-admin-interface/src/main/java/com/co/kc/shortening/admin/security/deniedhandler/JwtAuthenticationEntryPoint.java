package com.co.kc.shortening.admin.security.deniedhandler;

import com.co.kc.shortening.common.constant.ErrorCode;
import com.co.kc.shortening.common.utils.JsonUtils;
import com.co.kc.shortening.web.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败会调用此方法，例如：通过注解{@code @PreAuthorize("isAuthenticated()")}认证失败
 * <p>
 * 详情可阅读<a href=https://docs.spring.io/spring-security/site/docs/5.5.2-SNAPSHOT/reference/html5/#servlet-authentication-authenticationentrypoint></a>
 * <p>
 * 注意，此处虽然有实现相应逻辑，但是过滤器在advice之后执行，所以在advice就被拦截了，即异常逻辑在advice中处理。
 *
 * @author kcl.co
 * @since 2022/02/19
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        LOG.error("身份认证失败, request:{}, response:{}, ex:{}", httpServletRequest, httpServletResponse, e);
        Result<?> response = Result.error(ErrorCode.AUTH_FAIL);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().print(JsonUtils.toJson(response));
    }
}
