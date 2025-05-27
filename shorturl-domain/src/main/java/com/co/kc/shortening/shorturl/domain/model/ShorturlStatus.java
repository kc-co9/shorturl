package com.co.kc.shortening.shorturl.domain.model;

import lombok.Getter;

/**
 * 短链状态-值对象
 *
 * @author kc
 */
@Getter
public enum ShorturlStatus {
    /**
     * 上线
     */
    ONLINE,
    /**
     * 下线
     */
    OFFLINE;
}
