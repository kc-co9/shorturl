package com.co.kc.shorturl.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.co.kc.shorturl.admin.config.ShorturlConfig;
import com.co.kc.shorturl.admin.model.domain.UrlKeyGenerator;
import com.co.kc.shorturl.admin.model.dto.response.BlacklistDTO;
import com.co.kc.shorturl.admin.model.dto.response.ShorturlDTO;
import com.co.kc.shorturl.common.exception.ToastException;
import com.co.kc.shorturl.common.model.io.Paging;
import com.co.kc.shorturl.common.model.io.PagingResult;
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
import java.util.*;

/**
 * @author kc
 */
@Service
public class UrlBizService {

    private static final char[] CHARSET = new char[]{
            'e', 'H', 'M', 'F', 'x', 'X', 'm', 'l', '4',
            'L', 'g', '6', '1', 'B', 'n', 'C', 't', 'q',
            'w', '2', 'a', 'o', 'K', 'P', 'W', 'R', 'S',
            'A', 'k', '0', 'U', 'Y', '9', 's', '5', 'c',
            'N', 'Q', 'T', 'r', 'I', 'h', 'V', 'b', 'E',
            '3', '7', 'D', 'i', 'O', 'z', 'G', 'u', 'p',
            'f', 'j', 'v', '8', 'd', 'Z', 'y', 'J'};

    @Autowired
    private ShorturlConfig shorturlConfig;
    @Autowired
    private UrlKeyGenerator urlKeyGenerator;
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
            shorturlDTO.setId(urlKey.getId());
            shorturlDTO.setUrl(urlKey.getUrl());
            shorturlDTO.setShorturl(buildShorturl(urlKey.getKey()));
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
        if (validStart.isAfter(validEnd)) {
            throw new ToastException("有效期校验异常");
        }
        String hash = HashUtils.murmurHash32(url);
        UrlBlacklist blacklist = urlBlacklistRepository.findBlacklistByHash(hash, url).orElse(null);
        if (blacklist != null) {
            throw new ToastException("该链接被封禁，无法创建短链");
        }
        UrlKey urlKey = urlKeyRepository.findByHash(hash, url).orElse(null);
        if (urlKey != null) {
            return urlKey.getKey();
        }
        urlKey = new UrlKey();
        urlKey.setUrl(url);
        urlKey.setKey(base62(urlKeyGenerator.next()));
        urlKey.setHash(hash);
        urlKey.setStatus(UrlKeyStatus.ACTIVE);
        urlKey.setValidStart(validStart);
        urlKey.setValidEnd(validEnd);
        urlKeyRepository.save(urlKey);
        return urlKey.getKey();
    }

    public void updateShorturl(Long id, UrlKeyStatus status, LocalDateTime validStart, LocalDateTime validEnd) {
        UrlKey urlKey = urlKeyRepository.getById(id);
        if (urlKey == null) {
            throw new ToastException("数据不存在");
        }
        String hash = HashUtils.murmurHash32(urlKey.getUrl());
        UrlBlacklist blacklist = urlBlacklistRepository.findBlacklistByHash(hash, urlKey.getUrl()).orElse(null);
        if (blacklist != null) {
            throw new ToastException("该链接被封禁，无法变更");
        }
        urlKeyRepository.update(urlKeyRepository.getUpdateWrapper()
                .set(UrlKey::getStatus, status)
                .set(UrlKey::getValidStart, validStart)
                .set(UrlKey::getValidEnd, validEnd)
                .eq(UrlKey::getId, id));
    }

    public PagingResult<BlacklistDTO> getBlacklistList(Paging paging) {
        IPage<UrlBlacklist> blacklistPage =
                urlBlacklistRepository.page(new Page<>(paging.getPageNo(), paging.getPageSize()),
                        urlBlacklistRepository.getQueryWrapper().orderByDesc(UrlBlacklist::getId));
        List<BlacklistDTO> blacklistList = FunctionUtils.mappingList(blacklistPage.getRecords(), blacklist -> {
            BlacklistDTO shorturlDTO = new BlacklistDTO();
            shorturlDTO.setId(blacklist.getId());
            shorturlDTO.setUrl(blacklist.getUrl());
            shorturlDTO.setRemark(blacklist.getRemark());
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
        String hash = HashUtils.murmurHash32(url);
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


    private String buildShorturl(String key) {
        return shorturlConfig.getHost() + "/" + key;
    }

    private String base62(Long num) {
        // 定义62进制的字符集
        if (num == 0) {
            return String.valueOf(CHARSET[0]);
        }
        StringBuilder result = new StringBuilder();
        while (num > 0) {
            // 取模得到当前位的索引
            int remainder = (int) (num % 62);
            // 根据索引从字符集中获取对应的字符
            result.insert(0, CHARSET[remainder]);
            // 整除62更新十进制数
            num = num / 62;
        }
        return result.toString();
    }
}
