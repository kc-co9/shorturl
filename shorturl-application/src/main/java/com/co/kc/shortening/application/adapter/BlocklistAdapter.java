package com.co.kc.shortening.application.adapter;

import com.co.kc.shortening.blocklist.domain.model.BlockStatus;

/**
 * 黑名单-防腐层
 *
 * @author kc
 */
public class BlocklistAdapter {
    private BlocklistAdapter() {
    }

    public static BlockStatus convertStatus(Integer status) {
        if (status == 1) {
            return BlockStatus.ONLINE;
        } else {
            return BlockStatus.OFFLINE;
        }
    }
}
