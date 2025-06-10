package com.co.kc.shortening.infrastructure.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.co.kc.shortening.blocklist.domain.model.BlockStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

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

    public static Optional<BlockStatus> convert(UrlBlocklistStatus status) {
        if (status == null) {
            return Optional.empty();
        }
        switch (status) {
            case ACTIVE:
                return Optional.of(BlockStatus.ONLINE);
            case INVALID:
                return Optional.of(BlockStatus.OFFLINE);
            case NONE:
            default:
                return Optional.empty();
        }
    }

    public static Optional<UrlBlocklistStatus> convert(BlockStatus status) {
        if (status == null) {
            return Optional.empty();
        }
        switch (status) {
            case ONLINE:
                return Optional.of(UrlBlocklistStatus.ACTIVE);
            case OFFLINE:
                return Optional.of(UrlBlocklistStatus.INVALID);
            default:
                return Optional.empty();
        }
    }

}
