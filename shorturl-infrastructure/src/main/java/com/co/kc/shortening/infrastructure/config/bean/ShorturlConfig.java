package com.co.kc.shortening.infrastructure.config.bean;

import com.co.kc.shortening.application.provider.ShortDomainProvider;
import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.blocklist.service.BlocklistService;
import com.co.kc.shortening.application.service.appservice.ShorturlAppService;
import com.co.kc.shortening.infrastructure.client.id.bizid.ShorturlIdClient;
import com.co.kc.shortening.shorturl.domain.model.ShorturlRepository;
import com.co.kc.shortening.infrastructure.repository.ShorturlMySQLRepository;
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
    public ShorturlRepository shorturlRepository() {
        return new ShorturlMySQLRepository();
    }

    @Bean
    public IdClient<Long> shortIdGenerator() {
        return new ShorturlIdClient();
    }

    @Bean
    public ShorturlAppService shorturlAppService(IdClient<Long> idClient,
                                                 IdClient<String> codeClient,
                                                 ShortDomainProvider shortDomainProvider,
                                                 BlocklistService blocklistService,
                                                 ShorturlRepository shorturlRepository) {
        return new ShorturlAppService(idClient, codeClient, shortDomainProvider, blocklistService, shorturlRepository);
    }
}
