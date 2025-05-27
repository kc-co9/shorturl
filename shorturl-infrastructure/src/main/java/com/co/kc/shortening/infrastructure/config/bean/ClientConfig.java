package com.co.kc.shortening.infrastructure.config.bean;

import com.co.kc.shortening.application.client.CacheClient;
import com.co.kc.shortening.infrastructure.client.cache.RedisCacheClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author kc
 */
@Configuration
public class ClientConfig {

    @Bean
    public CacheClient cacheClient(RedisTemplate<String, String> redisTemplate) {
        return new RedisCacheClient(redisTemplate);
    }

}
