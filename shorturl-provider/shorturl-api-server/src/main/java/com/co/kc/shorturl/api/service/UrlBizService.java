package com.co.kc.shorturl.api.service;

import com.co.kc.shorturl.common.exception.ToastException;
import com.co.kc.shorturl.common.utils.HashUtils;
import com.co.kc.shorturl.repository.dao.UrlBlocklistRepository;
import com.co.kc.shorturl.repository.dao.UrlKeyRepository;
import com.co.kc.shorturl.repository.enums.UrlKeyStatus;
import com.co.kc.shorturl.repository.po.entity.UrlBlocklist;
import com.co.kc.shorturl.repository.po.entity.UrlKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author kc
 */
@Service
public class UrlBizService {
    @Autowired
    private UrlKeyRepository urlKeyRepository;
    @Autowired
    private UrlBlocklistRepository urlBlocklistRepository;

    public String parseShorturl(String key) {
        UrlKey urlKey = urlKeyRepository.findByKey(key).orElseThrow(() -> new ToastException("链接解析失败"));
        if (!UrlKeyStatus.ACTIVE.equals(urlKey.getStatus())) {
            throw new ToastException("该链接被屏蔽，无法访问");
        }
        if (urlKey.getValidStart().isAfter(LocalDateTime.now()) || urlKey.getValidEnd().isBefore(LocalDateTime.now())) {
            throw new ToastException("该链接已过期，无法访问");
        }
        String hash = HashUtils.murmurHash32(urlKey.getUrl());
        UrlBlocklist blocklist = urlBlocklistRepository.findByHash(hash, urlKey.getUrl()).orElse(null);
        if (blocklist != null) {
            throw new ToastException("该链接被封禁，无法访问");
        }
        return urlKey.getUrl();
    }
}
