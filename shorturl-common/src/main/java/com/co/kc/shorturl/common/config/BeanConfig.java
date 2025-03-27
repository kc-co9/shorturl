package com.co.kc.shorturl.common.config;

import com.co.kc.shorturl.common.sdk.CacheClient;
import com.co.kc.shorturl.common.sdk.RedisCacheClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author kc
 */
@Configuration
public class BeanConfig {

    @Bean
    public CacheClient cacheClient(RedisTemplate<String, String> redisTemplate) {
        return new RedisCacheClient(redisTemplate);
    }

}
