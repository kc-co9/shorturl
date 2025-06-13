package com.co.kc.shortening.application.client;

import com.co.kc.shortening.common.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.platform.commons.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author kc
 */
public class MemoryCacheClient implements CacheClient {
    private final Map<String, String> cache = new HashMap<>();
    private final Map<String, Long> expired = new HashMap<>();

    @Override
    public synchronized void set(String key, Object value) {
        cache.put(key, JsonUtils.toJson(value));
    }

    @Override
    public synchronized void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        cache.put(key, JsonUtils.toJson(value));
        expired.put(key, System.currentTimeMillis() + timeUnit.toMillis(timeout));
    }

    @Override
    public synchronized <T> Optional<T> get(String key, Class<T> clazz) {
        String jsonVal = cache.get(key);
        if (StringUtils.isBlank(jsonVal)) {
            return Optional.empty();
        }
        if (expired.containsKey(key) && expired.get(key) - System.currentTimeMillis() <= 0) {
            cache.remove(key);
            expired.remove(key);
            return Optional.empty();
        }
        return Optional.of(JsonUtils.fromJson(jsonVal, clazz));
    }

    @Override
    public synchronized <T> Optional<T> get(String key, TypeReference<T> typeReference) {
        String jsonVal = cache.get(key);
        if (StringUtils.isBlank(jsonVal)) {
            return Optional.empty();
        }
        if (expired.containsKey(key) && expired.get(key) - System.currentTimeMillis() <= 0) {
            cache.remove(key);
            expired.remove(key);
            return Optional.empty();
        }
        return Optional.of(JsonUtils.fromJson(jsonVal, typeReference));
    }

    @Override
    public synchronized Boolean remove(String key) {
        cache.remove(key);
        expired.remove(key);
        return true;
    }

    @Override
    public synchronized void remove(List<String> keyList) {
        for (String key : keyList) {
            cache.remove(key);
            expired.remove(key);
        }
    }

    @Override
    public synchronized boolean hasKey(String key) {
        if (!cache.containsKey(key)) {
            return false;
        }
        if (expired.containsKey(key) && expired.get(key) - System.currentTimeMillis() <= 0) {
            cache.remove(key);
            expired.remove(key);
            return false;
        }
        return true;
    }

    @Override
    public synchronized Long getExpire(String key, TimeUnit timeUnit) {
        if (!cache.containsKey(key)) {
            return -2L;
        }
        Long ttl = expired.get(key);
        if (ttl == null) {
            return -1L;
        }
        if (ttl - System.currentTimeMillis() <= 0) {
            return 0L;
        }
        long distance = ttl - System.currentTimeMillis();
        return timeUnit.convert(distance, TimeUnit.MILLISECONDS);
    }

    @Override
    public synchronized Boolean refreshExpire(String key, long timeout, TimeUnit timeUnit) {
        if (!cache.containsKey(key)) {
            return false;
        }
        expired.put(key, System.currentTimeMillis() + timeUnit.toMillis(timeout));
        return true;
    }
}
