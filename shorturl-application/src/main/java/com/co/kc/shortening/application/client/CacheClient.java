package com.co.kc.shortening.application.client;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author kc
 */
public interface CacheClient {

    /**
     * 设置缓存
     *
     * @param key   缓存KEY
     * @param value 缓存VALUE
     */
    void set(String key, Object value);

    /**
     * 设置缓存
     *
     * @param key      缓存KEY
     * @param value    缓存VALUE
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     */
    void set(String key, Object value, long timeout, TimeUnit timeUnit);

    /**
     * 获取缓存
     *
     * @param key   缓存KEY
     * @param clazz 缓存类型
     * @param <T>   泛型-返回值
     * @return 返回对象
     */
    <T> Optional<T> get(String key, Class<T> clazz);

    /**
     * 获取缓存
     *
     * @param key           缓存KEY
     * @param typeReference 缓存类型
     * @param <T>           泛型-返回值
     * @return 返回对象
     */
    <T> Optional<T> get(String key, TypeReference<T> typeReference);

    /**
     * 删除缓存
     *
     * @param key 缓存KEY
     * @return
     */
    Boolean remove(String key);

    /**
     * 批量删除缓存
     *
     * @param keyList KEY列表
     */
    void remove(List<String> keyList);

    /**
     * 是否存在KEY
     *
     * @param key 缓存KEY
     * @return true/false
     */
    boolean hasKey(String key);

    /**
     * 获取超时时间
     *
     * @param key      缓存KEY
     * @param timeUnit 时间单位
     * @return 返回过期时间
     * - reply: TTL.
     * - reply: -1 if the key exists but has no associated expiration.
     * - reply: -2 if the key does not exist.
     */
    Long getExpire(String key, TimeUnit timeUnit);

    /**
     * 刷新缓存时间
     *
     * @param key      缓存KEY
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return 刷新是否成功
     */
    Boolean refreshExpire(String key, long timeout, TimeUnit timeUnit);

}
