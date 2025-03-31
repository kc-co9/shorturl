package com.co.kc.shorturl.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.co.kc.shorturl.admin.model.dto.BlacklistDTO;
import com.co.kc.shorturl.admin.model.dto.ShorturlDTO;
import com.co.kc.shorturl.common.model.Paging;
import com.co.kc.shorturl.common.model.PagingResult;
import com.co.kc.shorturl.common.utils.FunctionUtils;
import com.co.kc.shorturl.common.utils.HashUtils;
import com.co.kc.shorturl.repository.dao.UrlBlacklistRepository;
import com.co.kc.shorturl.repository.dao.UrlKeyRepository;
import com.co.kc.shorturl.repository.enums.UrlKeyStatus;
import com.co.kc.shorturl.repository.po.entity.UrlBlacklist;
import com.co.kc.shorturl.repository.po.entity.UrlKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author kc
 */
@Service
public class UrlBizService {
    @Autowired
    private UrlKeyRepository urlKeyRepository;
    @Autowired
    private UrlBlacklistRepository urlBlacklistRepository;

    public PagingResult<ShorturlDTO> getShorturlList(String key, UrlKeyStatus status, Paging paging) {
        IPage<UrlKey> urlKeyPage =
                urlKeyRepository.page(new Page<>(paging.getPageNo(), paging.getPageSize()), urlKeyRepository.getQueryWrapper()
                        .eq(StringUtils.isNotBlank(key), UrlKey::getKey, key)
                        .eq(Objects.nonNull(status), UrlKey::getStatus, status)
                        .orderByDesc(UrlKey::getId));
        List<ShorturlDTO> shorturlList = FunctionUtils.mappingList(urlKeyPage.getRecords(), urlKey -> {
            ShorturlDTO shorturlDTO = new ShorturlDTO();
            shorturlDTO.setUrl(urlKey.getUrl());
            shorturlDTO.setShorturl(urlKey.getKey());
            shorturlDTO.setStatus(urlKey.getStatus());
            shorturlDTO.setValidStart(urlKey.getValidStart());
            shorturlDTO.setValidEnd(urlKey.getValidEnd());
            return shorturlDTO;
        });
        return PagingResult.<ShorturlDTO>newBuilder()
                .paging(paging)
                .records(shorturlList)
                .totalCount(urlKeyPage.getTotal())
                .totalPages(urlKeyPage.getPages())
                .build();
    }

    public String createShorturl(String url, LocalDateTime validStart, LocalDateTime validEnd) {
        // 计算HASH
        String hash = HashUtils.murmurHash32(url);
        // 查询数据库
        UrlKey urlKey = urlKeyRepository.findUrlKeyByHash(hash, url).orElse(null);
        if (urlKey != null) {
            return urlKey.getKey();
        }
        // 创建short url
        urlKey = new UrlKey();
        urlKey.setUrl(url);
        // TODO 设计key填充
        urlKey.setKey("");
        urlKey.setHash(hash);
        urlKey.setStatus(UrlKeyStatus.ACTIVE);
        urlKey.setValidStart(validStart);
        urlKey.setValidEnd(validEnd);
        urlKeyRepository.save(urlKey);
        return urlKey.getKey();
    }

    public PagingResult<BlacklistDTO> getBlacklistList(Paging paging) {
        IPage<UrlBlacklist> blacklistPage =
                urlBlacklistRepository.page(new Page<>(paging.getPageNo(), paging.getPageSize()),
                        urlBlacklistRepository.getQueryWrapper().orderByDesc(UrlBlacklist::getId));
        List<BlacklistDTO> blacklistList = FunctionUtils.mappingList(blacklistPage.getRecords(), blacklist -> {
            BlacklistDTO shorturlDTO = new BlacklistDTO();
            shorturlDTO.setId(blacklist.getId());
            shorturlDTO.setUrl(blacklist.getUrl());
            shorturlDTO.setRemark(blacklist.getUrl());
            return shorturlDTO;
        });
        return PagingResult.<BlacklistDTO>newBuilder()
                .paging(paging)
                .records(blacklistList)
                .totalCount(blacklistPage.getTotal())
                .totalPages(blacklistPage.getPages())
                .build();
    }

    public void addBlacklist(String url, String remark) {
        // 计算HASH
        String hash = HashUtils.murmurHash32(url);
        // 查询数据库
        UrlBlacklist blacklist = urlBlacklistRepository.findBlacklistByHash(hash, url).orElse(null);
        if (blacklist != null) {
            return;
        }
        blacklist = new UrlBlacklist();
        blacklist.setUrl(url);
        blacklist.setHash(hash);
        blacklist.setRemark(remark);
        urlBlacklistRepository.save(blacklist);
    }

    public void removeBlacklist(Long id) {
        urlBlacklistRepository.removeById(id);
    }

}
