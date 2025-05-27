package com.co.kc.shortening.infrastructure.config.bean;

import com.co.kc.shortening.application.service.appservice.BlocklistAppService;
import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.blocklist.domain.model.BlocklistRepository;
import com.co.kc.shortening.infrastructure.repository.BlocklistMySQLRepository;
import com.co.kc.shortening.blocklist.service.BlocklistService;
import com.co.kc.shortening.infrastructure.client.id.bizid.BlocklistIdClient;
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
    public BlocklistRepository blocklistRepository() {
        return new BlocklistMySQLRepository();
    }

    @Bean
    public BlocklistService blocklistService(BlocklistRepository blocklistRepository) {
        return new BlocklistService(blocklistRepository);
    }

    @Bean
    public IdClient<Long> blocklistService() {
        return new BlocklistIdClient();
    }

    @Bean
    public BlocklistAppService blocklistAppService(IdClient<Long> idClient, BlocklistRepository blocklistRepository) {
        return new BlocklistAppService(idClient, blocklistRepository);
    }
}
