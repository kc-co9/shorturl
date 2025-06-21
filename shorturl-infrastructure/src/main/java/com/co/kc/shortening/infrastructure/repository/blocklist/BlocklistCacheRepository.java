package com.co.kc.shortening.infrastructure.repository.blocklist;

import com.co.kc.shortening.application.client.CacheClient;
import com.co.kc.shortening.blocklist.domain.model.BlockId;
import com.co.kc.shortening.blocklist.domain.model.Blocklist;
import com.co.kc.shortening.blocklist.domain.model.BlocklistRepository;
import com.co.kc.shortening.shared.domain.model.Link;

import java.util.Optional;

import static com.co.kc.shortening.application.model.enums.CacheKey.*;

/**
 * @author kc
 */
public class BlocklistCacheRepository implements BlocklistRepository {
    private final CacheClient cacheClient;
    private final BlocklistRepository delegate;

    public BlocklistCacheRepository(BlocklistRepository delegate, CacheClient cacheClient) {
        this.cacheClient = cacheClient;
        this.delegate = delegate;
    }

    @Override
    public Blocklist find(BlockId blockId) {
        Optional<Blocklist> cached = cacheClient.get(BLOCKLIST_BY_ID_CACHE.get(blockId.getId()), Blocklist.class);
        if (cached.isPresent()) {
            return cached.get();
        }
        Blocklist blocklist = delegate.find(blockId);
        cacheClient.set(BLOCKLIST_BY_ID_CACHE.get(blockId.getId()), blocklist, BLOCKLIST_BY_ID_CACHE.getTimeout(), BLOCKLIST_BY_ID_CACHE.getTimeUnit());
        return blocklist;
    }

    @Override
    public Blocklist find(Link link) {
        Optional<Blocklist> cached = cacheClient.get(BLOCKLIST_BY_LINK_CACHE.get(link.getUrl()), Blocklist.class);
        if (cached.isPresent()) {
            return cached.get();
        }
        Blocklist blocklist = delegate.find(link);
        cacheClient.set(BLOCKLIST_BY_LINK_CACHE.get(link.getUrl()), blocklist, BLOCKLIST_BY_LINK_CACHE.getTimeout(), BLOCKLIST_BY_LINK_CACHE.getTimeUnit());
        return blocklist;
    }

    @Override
    public void save(Blocklist blocklist) {
        delegate.save(blocklist);
        cacheClient.remove(BLOCKLIST_BY_ID_CACHE.get(blocklist.getBlockId().getId()));
        cacheClient.remove(BLOCKLIST_BY_LINK_CACHE.get(blocklist.getLink().getUrl()));
    }

    @Override
    public void remove(Blocklist blocklist) {
        delegate.remove(blocklist);
        cacheClient.remove(BLOCKLIST_BY_ID_CACHE.get(blocklist.getBlockId().getId()));
        cacheClient.remove(BLOCKLIST_BY_LINK_CACHE.get(blocklist.getLink().getUrl()));
    }
}
