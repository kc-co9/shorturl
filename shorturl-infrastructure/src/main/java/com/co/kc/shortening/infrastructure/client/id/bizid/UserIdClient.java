package com.co.kc.shortening.infrastructure.client.id.bizid;

import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.infrastructure.client.id.SnowflakeId;

/**
 * @author kc
 */
public class UserIdClient implements IdClient<Long> {

    private final SnowflakeId snowflakeId;

    public UserIdClient(SnowflakeId snowflakeId) {
        this.snowflakeId = snowflakeId;
    }


    @Override
    public Long next() {
        return snowflakeId.next();
    }
}
