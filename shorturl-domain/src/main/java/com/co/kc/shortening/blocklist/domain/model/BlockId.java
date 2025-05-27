package com.co.kc.shortening.blocklist.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 黑名单ID-值对象
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode
public class BlockId {

    private final Long id;

    public BlockId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("id is less than or equal to 0");
        }
        this.id = id;
    }
}
