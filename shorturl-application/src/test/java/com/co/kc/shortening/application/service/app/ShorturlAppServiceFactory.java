package com.co.kc.shortening.application.service.app;

import com.co.kc.shortening.application.client.*;
import com.co.kc.shortening.application.provider.ShortDomainProvider;
import com.co.kc.shortening.application.provider.StaticShortDomainProvider;
import com.co.kc.shortening.blocklist.domain.model.BlocklistMemoryRepository;
import com.co.kc.shortening.blocklist.domain.model.BlocklistRepository;
import com.co.kc.shortening.blocklist.service.BlocklistService;
import com.co.kc.shortening.shorturl.domain.model.ShorturlMemoryRepository;
import com.co.kc.shortening.shorturl.domain.model.ShorturlRepository;

public class ShorturlAppServiceFactory {
    public static ShorturlAppService createShorturlAppService() {
        ShorturlRepository shorturlRepository = new ShorturlMemoryRepository();
        return createShorturlAppService(shorturlRepository);
    }

    public static ShorturlAppService createShorturlAppService(ShorturlRepository shorturlRepository) {
        IdClient<Long> shortIdClient = new RandomIdClient();
        IdClient<String> shortCodeClient = new RandomCodeClient();
        ShortDomainProvider shortDomainProvider = new StaticShortDomainProvider();
        BlocklistRepository blocklistRepository = new BlocklistMemoryRepository();
        BlocklistService blocklistService = new BlocklistService(blocklistRepository);
        return new ShorturlAppService(shortIdClient, shortCodeClient, shortDomainProvider, blocklistService, shorturlRepository);
    }
}
