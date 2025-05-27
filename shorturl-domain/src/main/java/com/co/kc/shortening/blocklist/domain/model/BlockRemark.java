package com.co.kc.shortening.blocklist.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 黑名单-备注-值对象
 *
 * @author kc
 */
@Getter
@EqualsAndHashCode
public class BlockRemark {
    /**
     * 黑名单备注最大长度
     */
    private static final int MAX_LENGTH = 256;

    private final String remark;

    public BlockRemark(String remark) {
        if (remark == null) {
            remark = "";
        }
        if (remark.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("remark length is greater than 256");
        }
        this.remark = remark;
    }
}
