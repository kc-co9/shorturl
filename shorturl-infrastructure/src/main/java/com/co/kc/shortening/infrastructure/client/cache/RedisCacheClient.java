package com.co.kc.shortening.infrastructure.client.cache;

import com.co.kc.shortening.application.client.CacheClient;
import com.co.kc.shortening.common.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author kc
 */
public class RedisCacheClient implements CacheClient {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisCacheClient(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(String key, Object value) {
        Assert.isTrue(StringUtils.isNotBlank(key), "cache key is null");
        redisTemplate.opsForValue().set(key, JsonUtils.toJson(value));
    }

    @Override
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        Assert.isTrue(StringUtils.isNotBlank(key), "cache key is null");
        redisTemplate.opsForValue().set(key, JsonUtils.toJson(value), timeout, timeUnit);
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> clazz) {
        Assert.isTrue(StringUtils.isNotBlank(key), "cache key is null");

        String value = redisTemplate.opsForValue().get(key);
        if (Objects.isNull(value)) {
            return Optional.empty();
        }

        return Optional.ofNullable(JsonUtils.fromJson(value, clazz));
    }

    @Override
    public <T> Optional<T> get(String key, TypeReference<T> typeReference) {
        Assert.isTrue(StringUtils.isNotBlank(key), "cache key is null");

        String value = redisTemplate.opsForValue().get(key);
        if (Objects.isNull(value)) {
            return Optional.empty();
        }

        return Optional.ofNullable(JsonUtils.fromJson(value, typeReference));
    }

    @Override
    public Boolean remove(String key) {
        Assert.isTrue(StringUtils.isNotBlank(key), "cache key is null");

        return redisTemplate.delete(key);
    }

    @Override
    public void remove(List<String> keyList) {
        Assert.isTrue(!CollectionUtils.isEmpty(keyList), "cache key is null");

        redisTemplate.delete(keyList);
    }

    @Override
    public boolean hasKey(String key) {
        Assert.isTrue(StringUtils.isNotBlank(key), "cache key is null");

        Boolean hasKey = redisTemplate.hasKey(key);
        return Optional.ofNullable(hasKey).orElse(false);
    }

    @Override
    public Long getExpire(String key, TimeUnit timeUnit) {
        Assert.isTrue(StringUtils.isNotBlank(key), "cache key is null");
        return redisTemplate.getExpire(key, timeUnit);
    }

    @Override
    public Boolean refreshExpire(String key, long timeout, TimeUnit timeUnit) {
        Assert.isTrue(StringUtils.isNotBlank(key), "cache key is null");
        return redisTemplate.expire(key, timeout, timeUnit);
    }
}
