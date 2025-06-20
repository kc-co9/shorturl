package com.co.kc.shortening.application.model.enums;

import lombok.Getter;

import java.util.IllegalFormatException;
import java.util.concurrent.TimeUnit;

/**
 * 缓存KEY
 *
 * @author kc
 */
@Getter
public enum CacheKey {
    /**
     * 短链Shorturl聚合根缓存
     */
    SHORTURL_BY_ID_CACHE("cache:shorturl:id:%s", 3, TimeUnit.DAYS),
    SHORTURL_BY_CODE_CACHE("cache:shorturl:code:%s", 3, TimeUnit.DAYS),
    BLOCKLIST_BY_ID_CACHE("cache:blocklist:id:%s", 3, TimeUnit.DAYS),
    BLOCKLIST_BY_LINK_CACHE("cache:blocklist:link:%s", 3, TimeUnit.DAYS),
    ;

    private final String pattern;
    private final long timeout;
    private final TimeUnit timeUnit;

    CacheKey(String pattern, long timeout, TimeUnit timeUnit) {
        this.pattern = pattern;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    public String get(Object... args) {
        if (args == null || args.length == 0) {
            // 如果没参数直接返回模板，或者抛异常根据业务需求
            return pattern;
        }
        try {
            return String.format(pattern, args);
        } catch (IllegalFormatException e) {
            throw new IllegalArgumentException(
                    "格式化缓存Key失败，pattern: " + pattern + ", args: " + java.util.Arrays.toString(args), e);
        }
    }
}
