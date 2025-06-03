package com.co.kc.shortening.web.common.constants.enums;

import com.co.kc.shortening.application.model.enums.BaseEnum;
import com.co.kc.shortening.blocklist.domain.model.BlockStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

    public static BlockStatus convert(BlockFacadeStatus status) {
        if (BlockFacadeStatus.ONLINE.equals(status)) {
            return BlockStatus.ONLINE;
        } else {
            return BlockStatus.OFFLINE;
        }
    }

    public static BlockFacadeStatus convert(BlockStatus status) {
        if (BlockStatus.ONLINE.equals(status)) {
            return BlockFacadeStatus.ONLINE;
        } else {
            return BlockFacadeStatus.OFFLINE;
        }
    }
}
