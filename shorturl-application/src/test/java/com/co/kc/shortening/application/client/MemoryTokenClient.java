package com.co.kc.shortening.application.client;

import com.co.kc.shortening.application.model.client.TokenDTO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryTokenClient implements TokenClient {
    private static final Map<String, TokenDTO> memory = new ConcurrentHashMap<>();

    @Override
    public String create(TokenDTO tokenDTO) {
        String token = String.valueOf(tokenDTO.getUserId());
        memory.put(token, tokenDTO);
        return token;
    }

    @Override
    public TokenDTO parse(String token) {
        return memory.get(token);
    }
}
