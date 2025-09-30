package com.co.kc.shortening.infrastructure.config.bean;

import com.co.kc.shortening.infrastructure.client.cache.RedisCacheClient;
import com.co.kc.shortening.infrastructure.client.id.SnowflakeId;
import com.co.kc.shortening.infrastructure.client.id.MachineIdAllocator;
import com.co.kc.shortening.infrastructure.client.lock.RedisLockClient;
import com.co.kc.shortening.infrastructure.config.properties.BaseProperties;
import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
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
    public MachineIdAllocator snowflakeMachineId(BaseProperties baseProperties, CuratorFramework curatorFramework) throws Exception {
        return new MachineIdAllocator(curatorFramework, baseProperties.getSnowflakeDataCenterId(), SnowflakeId.MAX_MACHINE);
    }

    @Bean
    @SneakyThrows
    public SnowflakeId snowflakeId(MachineIdAllocator machineIdAllocator) {
        return new SnowflakeId(machineIdAllocator);
    }

    @Bean
    public RedisCacheClient cacheClient(RedisTemplate<String, String> cacheRedisTemplate) {
        return new RedisCacheClient(cacheRedisTemplate);
    }

    @Bean
    public RedisLockClient lockClient(RedissonClient redissonClient) {
        return new RedisLockClient(redissonClient);
    }

}
