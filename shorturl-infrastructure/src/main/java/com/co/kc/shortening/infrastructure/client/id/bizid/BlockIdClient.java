package com.co.kc.shortening.infrastructure.client.id.bizid;

import com.co.kc.shortening.application.client.IdClient;

/**
 * 黑名单ID-生成器
 *
 * @author kc
 */
public class BlockIdClient implements IdClient<Long> {
    @Override
    public Long next() {
        return null;
    }
}
