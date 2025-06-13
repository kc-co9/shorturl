package com.co.kc.shortening.application.service.app;

import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.application.client.RandomIdClient;
import com.co.kc.shortening.blocklist.domain.model.BlocklistRepository;

public class BlocklistAppServiceFactory {

    public static BlocklistAppService createBlocklistAppService(BlocklistRepository blocklistRepository) {
        IdClient<Long> blockIdClient = new RandomIdClient();
        return new BlocklistAppService(blockIdClient, blocklistRepository);
    }
}
