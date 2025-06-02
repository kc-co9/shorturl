package com.co.kc.shortening.infrastructure.config.bean;

import com.co.kc.shortening.application.provider.ShortDomainProvider;
import com.co.kc.shortening.application.service.queryservice.ShorturlQueryService;
import com.co.kc.shortening.blocklist.service.BlocklistService;
import com.co.kc.shortening.application.service.appservice.ShorturlAppService;
import com.co.kc.shortening.infrastructure.client.id.bizid.ShortIdClient;
import com.co.kc.shortening.infrastructure.client.id.code.ShortCodeClient;
import com.co.kc.shortening.infrastructure.config.properties.ShorturlProperties;
import com.co.kc.shortening.infrastructure.mybatis.service.CodeGenService;
import com.co.kc.shortening.infrastructure.mybatis.service.UrlMappingService;
import com.co.kc.shortening.infrastructure.provider.ShortDomainYmlProvider;
import com.co.kc.shortening.infrastructure.service.query.ShorturlQueryMySqlService;
import com.co.kc.shortening.shorturl.domain.model.ShorturlRepository;
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
    public ShortDomainProvider shortDomainProvider(ShorturlProperties shorturlProperties) {
        return new ShortDomainYmlProvider(shorturlProperties);
    }

    @Bean
    public ShortCodeClient shortCodeClient(CodeGenService codeGenService) {
        return new ShortCodeClient(codeGenService);
    }

    @Bean
    public ShorturlRepository shorturlRepository(UrlMappingService urlMappingService) {
        return new ShorturlMySqlRepository(urlMappingService);
    }

    @Bean
    public ShorturlQueryService shorturlQueryService(UrlMappingService urlMappingService,
                                                     ShortDomainProvider shortDomainProvider) {
        return new ShorturlQueryMySqlService(urlMappingService, shortDomainProvider);
    }

    @Bean
    public ShorturlAppService shorturlAppService(ShortIdClient shortIdClient,
                                                 ShortCodeClient shortCodeClient,
                                                 ShortDomainProvider shortDomainProvider,
                                                 BlocklistService blocklistService,
                                                 ShorturlRepository shorturlRepository) {
        return new ShorturlAppService(shortIdClient, shortCodeClient, shortDomainProvider, blocklistService, shorturlRepository);
    }
}
