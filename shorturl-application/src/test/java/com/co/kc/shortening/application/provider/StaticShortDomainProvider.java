package com.co.kc.shortening.application.provider;

/**
 * 锻炼
 */
public class StaticShortDomainProvider implements ShortDomainProvider {
    @Override
    public String getDomain() {
        return "https://short.com";
    }
}
