package com.co.kc.shortening.application.service.queryservice;

import com.co.kc.shortening.application.model.cqrs.dto.ShorturlQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.ShorturlQuery;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.provider.ShortDomainProvider;
import com.co.kc.shortening.shorturl.domain.model.ShorturlRepository;

public class ShorturlQueryMemoryService implements ShorturlQueryService {
    private ShorturlRepository shorturlRepository;
    private ShortDomainProvider shortDomainProvider;

    public ShorturlQueryMemoryService(ShorturlRepository shorturlRepository, ShortDomainProvider shortDomainProvider) {
        this.shorturlRepository = shorturlRepository;
        this.shortDomainProvider = shortDomainProvider;
    }

    @Override
    public PagingResult<ShorturlQueryDTO> queryShorturl(ShorturlQuery query) {
        return null;
    }
}
