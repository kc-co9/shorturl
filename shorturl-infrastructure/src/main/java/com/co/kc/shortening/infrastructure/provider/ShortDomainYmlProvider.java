package com.co.kc.shortening.infrastructure.provider;

import com.co.kc.shortening.application.provider.ShortDomainProvider;
import com.co.kc.shortening.web.common.config.ShorturlProperties;

/**
 * @author kc
 */
public class ShortDomainYmlProvider implements ShortDomainProvider {
    private final ShorturlProperties shorturlProperties;

    public ShortDomainYmlProvider(ShorturlProperties shorturlProperties) {
        this.shorturlProperties = shorturlProperties;
    }

    @Override
    public String getDomain() {
        return shorturlProperties.getHost();
    }
}
