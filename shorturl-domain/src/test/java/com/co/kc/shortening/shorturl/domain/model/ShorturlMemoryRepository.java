package com.co.kc.shortening.shorturl.domain.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShorturlMemoryRepository implements ShorturlRepository {
    private final Map<ShortId, Shorturl> shortIdMemory = new ConcurrentHashMap<>();
    private final Map<ShortCode, Shorturl> codeMemory = new ConcurrentHashMap<>();

    @Override
    public Shorturl find(ShortCode code) {
        return codeMemory.get(code);
    }

    @Override
    public Shorturl find(ShortId shortId) {
        return shortIdMemory.get(shortId);
    }

    @Override
    public void save(Shorturl shorturl) {
        shortIdMemory.put(shorturl.getShortId(), shorturl);
        codeMemory.put(shorturl.getShortCode(), shorturl);
    }
}
