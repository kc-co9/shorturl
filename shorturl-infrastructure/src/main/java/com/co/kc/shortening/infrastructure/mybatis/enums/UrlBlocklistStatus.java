package com.co.kc.shortening.infrastructure.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.co.kc.shortening.blocklist.domain.model.BlockStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author kc
 */
@Getter
@AllArgsConstructor
public enum UrlBlocklistStatus {
    /**
     * 未知
     */
    NONE(0),
    /**
     * 激活
     */
    ACTIVE(1),
    /**
     * 失效
     */
    INVALID(2),
    ;
    @EnumValue
    private final Integer code;

    public static BlockStatus convert(UrlBlocklistStatus status) {
        if (ACTIVE.equals(status)) {
            return BlockStatus.ONLINE;
        } else if (INVALID.equals(status)) {
            return BlockStatus.OFFLINE;
        } else {
            return null;
        }
    }

    public static UrlBlocklistStatus convert(BlockStatus status) {
        if (BlockStatus.ONLINE.equals(status)) {
            return UrlBlocklistStatus.ACTIVE;
        } else {
            return UrlBlocklistStatus.INVALID;
        }
    }

}
