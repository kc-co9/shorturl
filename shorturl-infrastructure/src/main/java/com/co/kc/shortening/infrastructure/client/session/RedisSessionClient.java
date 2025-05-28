package com.co.kc.shortening.infrastructure.client.session;

import com.alibaba.fastjson2.JSON;
import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.model.client.SessionDTO;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author kc
 */
public class RedisSessionClient implements SessionClient {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisSessionClient(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public SessionDTO get(String sessionId) {
        String session = redisTemplate.opsForValue().get(sessionId);
        return JSON.parseObject(session, SessionDTO.class);
    }

    @Override
    public void save(String sessionId, SessionDTO sessionDTO, Long expired, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(sessionId, JSON.toJSONString(sessionDTO), expired, timeUnit);
    }

    @Override
    public void remove(String sessionId) {
        redisTemplate.delete(sessionId);
    }
}
