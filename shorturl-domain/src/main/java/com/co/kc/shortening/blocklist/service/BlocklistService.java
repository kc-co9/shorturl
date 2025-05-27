package com.co.kc.shortening.blocklist.service;

import com.co.kc.shortening.blocklist.domain.model.*;
import com.co.kc.shortening.shared.domain.model.Link;
import com.co.kc.shorturl.common.exception.BusinessException;

/**
 * 黑名单-领域服务
 *
 * @author kc
 */
public class BlocklistService {
    private final BlocklistRepository blocklistRepository;

    public BlocklistService(BlocklistRepository blocklistRepository) {
        this.blocklistRepository = blocklistRepository;
    }

    public void validate(Link rawLink) {
        Blocklist blocklist = blocklistRepository.find(rawLink);
        if (blocklist != null && blocklist.isActive()) {
            throw new BusinessException("短链已被禁用");
        }
    }
}
