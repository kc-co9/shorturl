package com.co.kc.shortening.application.service.queryservice;

import com.co.kc.shortening.application.model.cqrs.dto.BlocklistQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.BlocklistQuery;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.blocklist.domain.model.BlocklistRepository;

public class BlocklistQueryMemoryService implements BlocklistQueryService {
    public BlocklistRepository blocklistRepository;

    public BlocklistQueryMemoryService(BlocklistRepository blocklistRepository) {
        this.blocklistRepository = blocklistRepository;
    }

    @Override
    public PagingResult<BlocklistQueryDTO> queryBlocklist(BlocklistQuery query) {
        return null;
    }
}
