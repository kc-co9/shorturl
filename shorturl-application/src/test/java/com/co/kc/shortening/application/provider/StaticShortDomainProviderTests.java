package com.co.kc.shortening.application.provider;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class StaticShortDomainProviderTests {
    @Test
    public void testGetDomain() {
        ShortDomainProvider shortDomainProvider = new StaticShortDomainProvider();
        Assert.assertEquals("https://short.com", shortDomainProvider.getDomain());
    }
}
