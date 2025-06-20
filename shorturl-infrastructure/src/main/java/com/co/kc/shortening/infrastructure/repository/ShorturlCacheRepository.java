package com.co.kc.shortening.infrastructure.repository;

import com.co.kc.shortening.application.client.CacheClient;
import com.co.kc.shortening.shorturl.domain.model.ShortCode;
import com.co.kc.shortening.shorturl.domain.model.ShortId;
import com.co.kc.shortening.shorturl.domain.model.Shorturl;
import com.co.kc.shortening.shorturl.domain.model.ShorturlRepository;

import java.util.Optional;

import static com.co.kc.shortening.application.model.enums.CacheKey.SHORTURL_BY_CODE_CACHE;
import static com.co.kc.shortening.application.model.enums.CacheKey.SHORTURL_BY_ID_CACHE;

/**
 * @author kc
 */
public class ShorturlCacheRepository implements ShorturlRepository {
    private final CacheClient cacheClient;
    private final ShorturlRepository delegate;

    public ShorturlCacheRepository(ShorturlRepository delegate, CacheClient cacheClient) {
        this.cacheClient = cacheClient;
        this.delegate = delegate;
    }

    @Override
    public Shorturl find(ShortId shortId) {
        Optional<Shorturl> cached = cacheClient.get(SHORTURL_BY_ID_CACHE.get(shortId.getId()), Shorturl.class);
        if (cached.isPresent()) {
            return cached.get();
        }
        Shorturl shorturl = delegate.find(shortId);
        // 缓存结果，支持缓存null以防缓存穿透
        cacheClient.set(SHORTURL_BY_ID_CACHE.get(shortId.getId()), shorturl, SHORTURL_BY_ID_CACHE.getTimeout(), SHORTURL_BY_ID_CACHE.getTimeUnit());
        return shorturl;
    }

    @Override
    public Shorturl find(ShortCode code) {
        Optional<Shorturl> cached = cacheClient.get(SHORTURL_BY_CODE_CACHE.get(code.getCode()), Shorturl.class);
        if (cached.isPresent()) {
            return cached.get();
        }
        Shorturl shorturl = delegate.find(code);
        // 缓存结果，支持缓存null以防缓存穿透
        cacheClient.set(SHORTURL_BY_CODE_CACHE.get(code.getCode()), shorturl, SHORTURL_BY_CODE_CACHE.getTimeout(), SHORTURL_BY_CODE_CACHE.getTimeUnit());
        return shorturl;
    }

    @Override
    public void save(Shorturl shorturl) {
        delegate.save(shorturl);
        cacheClient.remove(SHORTURL_BY_ID_CACHE.get(shorturl.getShortId().getId()));
        cacheClient.remove(SHORTURL_BY_CODE_CACHE.get(shorturl.getShortCode().getCode()));
    }
}
