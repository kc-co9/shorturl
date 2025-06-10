package com.co.kc.shortening.web.common.constants.enums;

import com.co.kc.shortening.blocklist.domain.model.BlockStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 黑名单门面状态
 *
 * @author kc
 */
@Getter
@AllArgsConstructor
public enum BlockFacadeStatus implements BaseEnum {
    /**
     * 上线
     */
    ONLINE(1),
    /**
     * 下线
     */
    OFFLINE(0);

    private final Integer code;

    public static Optional<BlockStatus> convert(BlockFacadeStatus status) {
        if (status == null) {
            return Optional.empty();
        }
        switch (status) {
            case ONLINE:
                return Optional.of(BlockStatus.ONLINE);
            case OFFLINE:
                return Optional.of(BlockStatus.OFFLINE);
            default:
                return Optional.empty();
        }
    }

    public static Optional<BlockFacadeStatus> convert(BlockStatus status) {
        if (status == null) {
            return Optional.empty();
        }
        switch (status) {
            case ONLINE:
                return Optional.of(BlockFacadeStatus.ONLINE);
            case OFFLINE:
                return Optional.of(BlockFacadeStatus.OFFLINE);
            default:
                return Optional.empty();
        }
    }
}
