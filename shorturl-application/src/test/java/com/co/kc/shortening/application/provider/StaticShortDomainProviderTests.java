package com.co.kc.shortening.application.provider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class StaticShortDomainProviderTests {
    @Test
    void testGetDomain() {
        ShortDomainProvider shortDomainProvider = new StaticShortDomainProvider();
        Assertions.assertEquals("https://short.com", shortDomainProvider.getDomain());
    }
}
