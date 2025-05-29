package com.co.kc.shortening.infrastructure.client.id.bizid;

import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.infrastructure.client.id.SnowflakeId;

/**
 * 短链ID-生成器
 *
 * @author kc
 */
public class ShortIdClient implements IdClient<Long> {

    private final SnowflakeId snowflakeId = new SnowflakeId(1, 1);

    @Override
    public Long next() {
        return snowflakeId.next();
    }
}
