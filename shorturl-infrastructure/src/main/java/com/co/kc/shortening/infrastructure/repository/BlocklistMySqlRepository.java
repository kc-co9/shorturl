package com.co.kc.shortening.infrastructure.repository;

import com.co.kc.shortening.blocklist.domain.model.*;
import com.co.kc.shortening.infrastructure.mybatis.entity.UrlBlocklist;
import com.co.kc.shortening.infrastructure.mybatis.enums.UrlBlocklistStatus;
import com.co.kc.shortening.infrastructure.mybatis.service.UrlBlocklistService;
import com.co.kc.shortening.shared.domain.model.Link;
import org.springframework.stereotype.Service;

/**
 * 黑名单资源库MySQL实现
 *
 * @author kc
 */
@Service
public class BlocklistMySqlRepository implements BlocklistRepository {
    private final UrlBlocklistService urlBlocklistService;

    public BlocklistMySqlRepository(UrlBlocklistService urlBlocklistService) {
        this.urlBlocklistService = urlBlocklistService;
    }

    @Override
    public Blocklist find(BlockId blockId) {
        UrlBlocklist urlBlocklist = urlBlocklistService.findByBlockId(blockId.getId()).orElse(null);
        if (urlBlocklist == null) {
            return null;
        }

        Blocklist blocklist = new Blocklist(
                new BlockId(urlBlocklist.getBlockId()),
                new Link(urlBlocklist.getUrl()),
                new BlockRemark(urlBlocklist.getRemark()),
                UrlBlocklistStatus.convert(urlBlocklist.getStatus()).get());
        blocklist.setId(urlBlocklist.getId());
        return blocklist;
    }

    @Override
    public Blocklist find(Link link) {
        UrlBlocklist urlBlocklist = urlBlocklistService.findByHash(link.getHash(), link.getUrl()).orElse(null);
        if (urlBlocklist == null) {
            return null;
        }

        Blocklist blocklist = new Blocklist(
                new BlockId(urlBlocklist.getBlockId()),
                new Link(urlBlocklist.getUrl()),
                new BlockRemark(urlBlocklist.getRemark()),
                UrlBlocklistStatus.convert(urlBlocklist.getStatus()).get());
        blocklist.setId(urlBlocklist.getId());
        return blocklist;
    }

    @Override
    public void save(Blocklist blocklist) {
        UrlBlocklist urlBlocklist = new UrlBlocklist();
        urlBlocklist.setBlockId(blocklist.getBlockId().getId());
        urlBlocklist.setUrl(blocklist.getLink().getUrl());
        urlBlocklist.setHash(blocklist.getLink().getHash());
        urlBlocklist.setRemark(blocklist.getRemark().getRemark());
        urlBlocklist.setStatus(UrlBlocklistStatus.convert(blocklist.getStatus()).get());
        urlBlocklistService.save(urlBlocklist);
    }

    @Override
    public void remove(Blocklist blocklist) {
        urlBlocklistService.remove(urlBlocklistService.getQueryWrapper()
                .eq(UrlBlocklist::getBlockId, blocklist.getBlockId().getId()));
    }
}
