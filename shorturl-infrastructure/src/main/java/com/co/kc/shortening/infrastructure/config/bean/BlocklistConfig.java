package com.co.kc.shortening.infrastructure.config.bean;

import com.co.kc.shortening.application.service.appservice.BlocklistAppService;
import com.co.kc.shortening.application.service.queryservice.BlocklistQueryService;
import com.co.kc.shortening.blocklist.domain.model.BlocklistRepository;
import com.co.kc.shortening.infrastructure.mybatis.service.UrlBlocklistService;
import com.co.kc.shortening.infrastructure.repository.BlocklistMySqlRepository;
import com.co.kc.shortening.blocklist.service.BlocklistService;
import com.co.kc.shortening.infrastructure.client.id.bizid.BlockIdClient;
import com.co.kc.shortening.infrastructure.service.query.BlocklistQueryMySqlService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean配置
 *
 * @author kc
 */
@Configuration
public class BlocklistConfig {
    @Bean
    public BlockIdClient blockIdClient() {
        return new BlockIdClient();
    }

    @Bean
    public BlocklistRepository blocklistRepository(UrlBlocklistService urlBlocklistService) {
        return new BlocklistMySqlRepository(urlBlocklistService);
    }

    @Bean
    public BlocklistService blocklistService(BlocklistRepository blocklistRepository) {
        return new BlocklistService(blocklistRepository);
    }

    @Bean
    public BlocklistQueryService blocklistQueryService(UrlBlocklistService urlBlocklistService) {
        return new BlocklistQueryMySqlService(urlBlocklistService);
    }

    @Bean
    public BlocklistAppService blocklistAppService(BlockIdClient blockIdClient, BlocklistRepository blocklistRepository) {
        return new BlocklistAppService(blockIdClient, blocklistRepository);
    }
}
