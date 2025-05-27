package com.co.kc.shortening.blocklist.domain.model;

import com.co.kc.shortening.shared.domain.model.Identification;
import com.co.kc.shortening.shared.domain.model.Link;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 黑名单-聚合根
 *
 * @author kc
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class Blocklist extends Identification {
    /**
     * 黑名单聚合根-唯一标识
     */
    private final BlockId blockId;
    private final Link link;
    private BlockRemark remark;
    private BlockStatus status;

    public Blocklist(BlockId blockId, Link link, BlockRemark remark, BlockStatus status) {
        this.blockId = blockId;
        this.link = link;
        this.remark = remark;
        this.status = status;
    }

    public boolean isActive() {
        return BlockStatus.ONLINE.equals(this.status);
    }

    public void activate() {
        this.status = BlockStatus.ONLINE;
    }

    public void inactivate() {
        this.status = BlockStatus.OFFLINE;
    }

    public void changeRemark(BlockRemark remark) {
        this.remark = remark;
    }

    public void changeStatus(BlockStatus status) {
        if (this.status.equals(status)) {
            return;
        }
        if (isActive()) {
            inactivate();
        } else {
            activate();
        }
    }
}
