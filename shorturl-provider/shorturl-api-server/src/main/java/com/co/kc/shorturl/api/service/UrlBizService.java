package com.co.kc.shorturl.api.service;

import com.co.kc.shorturl.common.exception.ToastException;
import com.co.kc.shorturl.common.utils.HashUtils;
import com.co.kc.shorturl.repository.dao.UrlBlacklistRepository;
import com.co.kc.shorturl.repository.dao.UrlKeyRepository;
import com.co.kc.shorturl.repository.enums.UrlKeyStatus;
import com.co.kc.shorturl.repository.po.entity.UrlBlacklist;
import com.co.kc.shorturl.repository.po.entity.UrlKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kc
 */
@Service
public class UrlBizService {
    @Autowired
    private UrlKeyRepository urlKeyRepository;
    @Autowired
    private UrlBlacklistRepository urlBlacklistRepository;

    public String parseShorturl(String key) {
        UrlKey urlKey = urlKeyRepository.findByKey(key).orElseThrow(() -> new ToastException("链接解析失败"));
        if (!UrlKeyStatus.ACTIVE.equals(urlKey.getStatus())) {
            throw new ToastException("该链接被屏蔽，无法访问");
        }
        // 计算HASH
        String hash = HashUtils.murmurHash32(urlKey.getUrl());
        UrlBlacklist blacklist = urlBlacklistRepository.findBlacklistByHash(hash, urlKey.getUrl()).orElse(null);
        if (blacklist != null) {
            throw new ToastException("该链接被封禁，无法访问");
        }
        return urlKey.getUrl();
    }
}
