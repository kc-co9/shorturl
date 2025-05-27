package com.co.kc.shortening.application.provider;

/**
 * 短链域名-提供器
 *
 * @author kc
 */
public interface ShortDomainProvider {
    /**
     * 获取域名
     *
     * @return 域名
     */
    String getDomain();
}
