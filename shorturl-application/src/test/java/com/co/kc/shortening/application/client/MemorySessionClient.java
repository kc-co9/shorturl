package com.co.kc.shortening.application.client;

import com.co.kc.shortening.application.model.client.SessionDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Session内存客户端
 *
 * @author kc
 */
public class MemorySessionClient implements SessionClient {
    private final Map<String, SessionDTO> memory = new HashMap<>();
    private final Map<String, Long> memoryTtl = new HashMap<>();

    @Override
    public synchronized SessionDTO get(String sessionId) {
        Long ttl = memoryTtl.get(sessionId);
        if (ttl == null || ttl - System.currentTimeMillis() <= 0) {
            memory.remove(sessionId);
            memoryTtl.remove(sessionId);
            return null;
        }
        return memory.get(sessionId);
    }

    @Override
    public synchronized void save(String sessionId, SessionDTO sessionDTO, Long expired, TimeUnit timeUnit) {
        memory.put(sessionId, sessionDTO);
        memoryTtl.put(sessionId, System.currentTimeMillis() + timeUnit.toMillis(expired));
    }

    @Override
    public synchronized void remove(String sessionId) {
        memory.remove(sessionId);
        memoryTtl.remove(sessionId);
    }
}
