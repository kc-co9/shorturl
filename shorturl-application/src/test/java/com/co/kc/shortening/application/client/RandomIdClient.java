package com.co.kc.shortening.application.client;

import org.apache.commons.lang3.RandomUtils;

/**
 * 随机数 ID生成器
 *
 * @author kc
 */
public class RandomIdClient implements IdClient<Long> {
    @Override
    public Long next() {
        return RandomUtils.nextLong(1, Long.MAX_VALUE);
    }
}
