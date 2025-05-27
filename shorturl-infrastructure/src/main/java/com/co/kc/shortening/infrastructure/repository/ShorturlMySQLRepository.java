package com.co.kc.shortening.infrastructure.repository;

import com.co.kc.shortening.shorturl.domain.model.ShortCode;
import com.co.kc.shortening.shorturl.domain.model.ShortId;
import com.co.kc.shortening.shorturl.domain.model.Shorturl;
import com.co.kc.shortening.shorturl.domain.model.ShorturlRepository;

/**
 * 短链资源库MySQL实现
 *
 * @author kc
 */
public class ShorturlMySQLRepository implements ShorturlRepository {
    @Override
    public Shorturl find(ShortId shortId) {
        return null;
    }

    @Override
    public Shorturl find(ShortCode code) {
        return null;
    }

    @Override
    public void save(Shorturl shorturl) {

    }
}
