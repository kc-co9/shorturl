package com.co.kc.shortening.blocklist.domain.model;

import com.co.kc.shortening.shared.domain.model.Link;

public class BlocklistFactory {
    public static final Long testBlockId = 10L;
    public static final String testBlockLink = "http://www.test.com";
    public static final String testBlockRemark = "test block remark";
    public static final String testBlockChangedRemark = "test block changed remark";
    public static final BlockStatus testBlockStatus = BlockStatus.ONLINE;
    public static final BlockStatus testBlockChangedStatus = BlockStatus.OFFLINE;


    public static Blocklist createBlocklist() {
        return new Blocklist(getTestBlockId(), getTestBlockLink(), getTestBlockRemark(), testBlockStatus);
    }

    public static BlockId getTestBlockId() {
        return new BlockId(testBlockId);
    }

    public static Link getTestBlockLink() {
        return new Link(testBlockLink);
    }

    public static BlockRemark getTestBlockRemark() {
        return new BlockRemark(testBlockRemark);
    }

    public static BlockRemark getTestBlockChangedRemark() {
        return new BlockRemark(testBlockChangedRemark);
    }
}
