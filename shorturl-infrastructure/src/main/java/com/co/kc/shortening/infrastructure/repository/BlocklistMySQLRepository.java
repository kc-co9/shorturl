package com.co.kc.shortening.infrastructure.repository;

import com.co.kc.shortening.blocklist.domain.model.BlockId;
import com.co.kc.shortening.blocklist.domain.model.Blocklist;
import com.co.kc.shortening.blocklist.domain.model.BlocklistRepository;
import com.co.kc.shortening.shared.domain.model.Link;

/**
 * 黑名单资源库MySQL实现
 *
 * @author kc
 */
public class BlocklistMySQLRepository implements BlocklistRepository {

    @Override
    public Blocklist find(BlockId blockId) {
        return null;
    }

    @Override
    public Blocklist find(Link link) {
        return null;
    }

    @Override
    public void save(Blocklist blocklist) {

    }

    @Override
    public void remove(Blocklist blocklist) {

    }
}
