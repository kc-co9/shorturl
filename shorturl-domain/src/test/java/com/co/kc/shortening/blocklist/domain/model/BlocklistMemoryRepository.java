package com.co.kc.shortening.blocklist.domain.model;

import com.co.kc.shortening.shared.domain.model.Link;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlocklistMemoryRepository implements BlocklistRepository {
    private final Map<BlockId, Blocklist> blockIdMemory = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Blocklist>> hashMemory = new ConcurrentHashMap<>();

    @Override
    public Blocklist find(BlockId blockId) {
        return blockIdMemory.get(blockId);
    }

    @Override
    public Blocklist find(Link link) {
        return hashMemory.getOrDefault(link.getHash(), Collections.emptyMap()).get(link.getUrl());
    }

    @Override
    public void save(Blocklist blocklist) {
        blockIdMemory.put(blocklist.getBlockId(), blocklist);
        hashMemory.computeIfAbsent(blocklist.getLink().getHash(), k -> new ConcurrentHashMap<>())
                .put(blocklist.getLink().getUrl(), blocklist);
    }

    @Override
    public void remove(Blocklist blocklist) {
        blockIdMemory.remove(blocklist.getBlockId());
        hashMemory.getOrDefault(blocklist.getLink().getHash(), Collections.emptyMap()).remove(blocklist.getLink().getUrl());
    }
}
