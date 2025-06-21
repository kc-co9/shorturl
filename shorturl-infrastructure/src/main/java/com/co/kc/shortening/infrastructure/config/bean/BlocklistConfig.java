package com.co.kc.shortening.infrastructure.config.bean;

import com.co.kc.shortening.application.service.app.BlocklistAppService;
import com.co.kc.shortening.infrastructure.client.cache.RedisCacheClient;
import com.co.kc.shortening.infrastructure.mybatis.service.UrlBlocklistService;
import com.co.kc.shortening.infrastructure.repository.blocklist.BlocklistCacheRepository;
import com.co.kc.shortening.infrastructure.repository.blocklist.BlocklistMySqlRepository;
import com.co.kc.shortening.blocklist.service.BlocklistService;
import com.co.kc.shortening.infrastructure.client.id.bizid.BlockIdClient;
import com.co.kc.shortening.infrastructure.service.query.BlocklistMySqlQueryService;
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
    public BlocklistMySqlRepository blocklistMySqlRepository(UrlBlocklistService urlBlocklistService) {
        return new BlocklistMySqlRepository(urlBlocklistService);
    }

    @Bean
    public BlocklistCacheRepository blocklistCacheRepository(BlocklistMySqlRepository blocklistRepository, RedisCacheClient cacheClient) {
        return new BlocklistCacheRepository(blocklistRepository, cacheClient);
    }

    @Bean
    public BlocklistService blocklistService(BlocklistCacheRepository blocklistRepository) {
        return new BlocklistService(blocklistRepository);
    }

    @Bean
    public BlocklistMySqlQueryService blocklistMySqlQueryService(UrlBlocklistService urlBlocklistService) {
        return new BlocklistMySqlQueryService(urlBlocklistService);
    }

    @Bean
    public BlocklistAppService blocklistAppService(BlockIdClient blockIdClient, BlocklistCacheRepository blocklistRepository) {
        return new BlocklistAppService(blockIdClient, blocklistRepository);
    }
}
