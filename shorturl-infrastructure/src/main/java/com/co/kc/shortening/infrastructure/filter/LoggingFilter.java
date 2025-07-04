package com.co.kc.shortening.infrastructure.filter;

import com.co.kc.shortening.common.utils.JsonUtils;
import com.co.kc.shortening.common.utils.NetworkUtils;
import com.co.kc.shortening.infrastructure.config.properties.LogProperties;
import com.co.kc.shortening.infrastructure.constant.LogConstants;
import com.co.kc.shortening.infrastructure.constant.ParamsConstants;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author kc
 */
@Slf4j
@Component
@WebFilter(urlPatterns = "/*")
@Order(Ordered.LOWEST_PRECEDENCE - 10)
public class LoggingFilter extends OncePerRequestFilter {

    @Autowired
    private LogProperties logProperties;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        StopWatch stopWatch = StopWatch.createStarted();

        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request, 10 * 1024);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }

        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("异常请求 | 请求唯一id:[{}] | path:[{}] | error:[{}]", getTraceId(), request.getRequestURI(), ex.getMessage(), ex);
            throw ex;
        } finally {
            stopWatch.stop();

            if (!ServletFileUpload.isMultipartContent(request)) {
                printLog(request, response, stopWatch);
            }

            // 在过滤器中使用了 ContentCachingResponseWrapper 包装了原始的 HttpServletResponse。
            // ContentCachingResponseWrapper 会把响应的内容先缓存到内存里（缓存响应体），这样你可以多次读取响应内容，比如用来日志记录。
            // 但是，缓存的内容默认并不会自动写回给客户端，如果不手动调用 copyBodyToResponse()，客户端就收不到响应体数据，也就是浏览器等会拿到空响应或不完整的响应。
            // 所以，copyBodyToResponse() 是负责把缓存的内容写回到真正的响应流，确保客户端能正确收到响应体。
            copyBodyToResponse(response);
        }
    }

    private void printLog(HttpServletRequest request, HttpServletResponse response, StopWatch stopWatch) {
        LogRecord logRecord =
                LogRecord.builder()
                        .traceId(getTraceId())
                        .requestPath(request.getRequestURI())
                        .requestMethod(request.getMethod())
                        .requestIp(request.getRemoteAddr())
                        .requestHeaders(getHeaders(request))
                        .requestParameters(getParameter(request))
                        .requestBody(getRequestBody(request))
                        .responseStatus(getResponseStatus(response))
                        .responseTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                        .responseHeaders(getHeaders(response))
                        .responseBody(getResponseBody(response))
                        .durationMs(stopWatch.getTime(TimeUnit.MILLISECONDS))
                        .build();
        if (LogConstants.LOG_JSON_FORMAT.equals(logProperties.getFormat())) {
            log.info("请求响应日志: {}", JsonUtils.toJson(logRecord));
        } else {
            log.info("请求响应日志: {}", logRecord.toPrettyLog());
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        return NetworkUtils.getHeader(request);
    }

    private Map<String, String> getParameter(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> Arrays.toString(e.getValue())));
    }

    private String getRequestBody(HttpServletRequest request) {
        String requestBody = "{}";
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            try {
                requestBody = IOUtils.toString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
            } catch (Exception ex) {
                log.error("获取请求Body时发生异常", ex);
            }
        }
        return requestBody;
    }

    private Map<String, String> getHeaders(HttpServletResponse response) {
        return NetworkUtils.getHeader(response);
    }

    private String getResponseBody(HttpServletResponse response) {
        String responseBody = "{}";
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            try {
                responseBody = IOUtils.toString(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8.name());
            } catch (Exception ex) {
                log.error("获取响应Body时发生异常", ex);
            }
        }
        return responseBody;
    }

    private void copyBodyToResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        Objects.requireNonNull(responseWrapper).copyBodyToResponse();
    }

    private String getTraceId() {
        String traceId = MDC.get(ParamsConstants.TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = "N/A";
        }
        return traceId;
    }

    private int getResponseStatus(HttpServletResponse response) {
        int status = response.getStatus();
        return status > 0 ? status : HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    @Data
    @Builder
    private static class LogRecord {
        private String traceId;
        private String requestMethod;
        private String requestPath;
        private String requestIp;
        private Map<String, String> requestHeaders;
        private Map<String, String> requestParameters;
        private String requestBody;
        private int responseStatus;
        private String responseTime;
        private Map<String, String> responseHeaders;
        private String responseBody;
        private long durationMs;

        public String toPrettyLog() {
            return "\n================== 请求响应日志 ==================\n" +
                    buildLogLine("请求id", traceId) +
                    buildLogLine("请求路径", requestPath) +
                    buildLogLine("请求方法", requestMethod) +
                    buildLogLine("请求IP", requestIp) +
                    buildLogLine("请求头", JsonUtils.toJson(requestHeaders)) +
                    buildLogLine("请求参数", JsonUtils.toJson(requestParameters)) +
                    buildLogLine("请求Body", requestBody) +
                    buildLogLine("响应状态", String.valueOf(responseStatus)) +
                    buildLogLine("响应时间", responseTime) +
                    buildLogLine("响应头", JsonUtils.toJson(responseHeaders)) +
                    buildLogLine("响应Body", responseBody) +
                    buildLogLine("响应耗时", String.format("%sms", durationMs)) +
                    "===================================================";
        }

        private StringBuilder buildLogLine(String name, String value) {
            return new StringBuilder()
                    .append(name)
                    .append(":")
                    .append(value)
                    .append(System.lineSeparator());
        }
    }

}