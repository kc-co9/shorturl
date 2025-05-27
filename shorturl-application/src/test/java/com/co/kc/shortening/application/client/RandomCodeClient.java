package com.co.kc.shortening.application.client;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * CODE生成客户端
 *
 * @author kc
 */
public class RandomCodeClient implements IdClient<String> {
    @Override
    public String next() {
        return RandomStringUtils.random(20);
    }
}
