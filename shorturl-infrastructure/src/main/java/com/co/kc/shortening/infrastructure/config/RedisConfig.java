package com.co.kc.shortening.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author kc
 */
@Configuration
public class RedisConfig {
    @Bean(name = "cacheRedisTemplate")
    public RedisTemplate<String, String> cacheRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate(redisConnectionFactory);
        redisTemplate.setKeySerializer(new PrefixedStringRedisSerializer("cache"));
        return redisTemplate;
    }

    @Bean(name = "sessionRedisTemplate")
    public RedisTemplate<String, String> sessionRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate(redisConnectionFactory);
        redisTemplate.setKeySerializer(new PrefixedStringRedisSerializer("session"));
        return redisTemplate;
    }

    @Primary
    @Bean(name = "shorturlObjectRedisTemplate")
    public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();

        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        template.setConnectionFactory(redisConnectionFactory);
        // key序列化方式
        template.setKeySerializer(redisSerializer);
        // value序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // value hashmap序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }

    @Primary
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();
        String address = "redis://" + redisProperties.host + ":" + redisProperties.port;
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(address);
        if (StringUtils.isNotBlank(redisProperties.password)) {
            singleServerConfig.setPassword(redisProperties.password);
        }
        if (StringUtils.isNotBlank(redisProperties.database)) {
            singleServerConfig.setDatabase(Integer.parseInt(redisProperties.database));
        }
        return Redisson.create(config);
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "spring.redis")
    public static class RedisProperties {
        private String host;
        private Integer port;
        private String password;
        private String database;
    }

    private static class PrefixedStringRedisSerializer extends StringRedisSerializer {
        private final String prefix;
        private final char separator;

        public PrefixedStringRedisSerializer(String prefix) {
            this(prefix, ':');
        }

        public PrefixedStringRedisSerializer(String prefix, char separator) {
            super();
            this.prefix = prefix;
            this.separator = separator;
        }

        @Override
        public String deserialize(byte[] bytes) {
            String key = super.deserialize(bytes);
            if (key == null) {
                return null;
            }
            return key.startsWith(prefix + separator) ? key.substring(prefix.length() + 1) : key;
        }

        @Override
        public byte[] serialize(String key) {
            if (key == null) {
                return new byte[0];
            }
            return super.serialize(prefix + separator + key);
        }
    }

}
