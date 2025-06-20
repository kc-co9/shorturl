package com.co.kc.shortening.infrastructure.config.bean;

import com.co.kc.shortening.application.provider.ShortDomainProvider;
import com.co.kc.shortening.blocklist.service.BlocklistService;
import com.co.kc.shortening.application.service.app.ShorturlAppService;
import com.co.kc.shortening.infrastructure.client.cache.RedisCacheClient;
import com.co.kc.shortening.infrastructure.client.id.bizid.ShortIdClient;
import com.co.kc.shortening.infrastructure.client.id.code.ShortCodeClient;
import com.co.kc.shortening.infrastructure.config.properties.ShorturlProperties;
import com.co.kc.shortening.infrastructure.mybatis.service.CodeGenService;
import com.co.kc.shortening.infrastructure.mybatis.service.UrlMappingService;
import com.co.kc.shortening.infrastructure.provider.ShortDomainYmlProvider;
import com.co.kc.shortening.infrastructure.repository.ShorturlCacheRepository;
import com.co.kc.shortening.infrastructure.service.query.ShorturlMySqlQueryService;
import com.co.kc.shortening.infrastructure.repository.ShorturlMySqlRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean配置
 *
 * @author kc
 */
@Configuration
public class ShorturlConfig {
    @Bean
    public ShortIdClient shortIdClient() {
        return new ShortIdClient();
    }

    @Bean
    public ShortCodeClient shortCodeClient(CodeGenService codeGenService) {
        return new ShortCodeClient(codeGenService);
    }

    @Bean
    public ShortDomainYmlProvider shortDomainYmlProvider(ShorturlProperties shorturlProperties) {
        return new ShortDomainYmlProvider(shorturlProperties);
    }

    @Bean
    public ShorturlMySqlRepository shorturlMySqlRepository(UrlMappingService urlMappingService) {
        return new ShorturlMySqlRepository(urlMappingService);
    }

    @Bean
    public ShorturlCacheRepository shorturlCacheRepository(RedisCacheClient cacheClient,
                                                           ShorturlMySqlRepository shorturlRepository) {
        return new ShorturlCacheRepository(shorturlRepository, cacheClient);
    }

    @Bean
    public ShorturlMySqlQueryService shorturlQueryMySqlService(UrlMappingService urlMappingService,
                                                               ShortDomainYmlProvider shortDomainProvider) {
        return new ShorturlMySqlQueryService(urlMappingService, shortDomainProvider);
    }

    @Bean
    public ShorturlAppService shorturlAppService(ShortIdClient shortIdClient,
                                                 ShortCodeClient shortCodeClient,
                                                 ShortDomainProvider shortDomainProvider,
                                                 BlocklistService blocklistService,
                                                 ShorturlCacheRepository shorturlRepository) {
        return new ShorturlAppService(shortIdClient, shortCodeClient, shortDomainProvider, blocklistService, shorturlRepository);
    }
}
