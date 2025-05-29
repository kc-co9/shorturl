package com.co.kc.shortening.infrastructure.config.bean;

import com.co.kc.shortening.application.client.CacheClient;
import com.co.kc.shortening.application.client.LockClient;
import com.co.kc.shortening.infrastructure.client.cache.RedisCacheClient;
import com.co.kc.shortening.infrastructure.client.lock.RedisLockClient;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author kc
 */
@Configuration
public class BasicConfig {

    @Bean
    public CacheClient cacheClient(RedisTemplate<String, String> cacheRedisTemplate) {
        return new RedisCacheClient(cacheRedisTemplate);
    }

    @Bean
    public LockClient lockClient(RedissonClient redissonClient) {
        return new RedisLockClient(redissonClient);
    }

}
