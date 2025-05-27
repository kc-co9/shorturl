package com.co.kc.shortening.application.model.cqrs.query;

import com.co.kc.shortening.application.model.io.Paging;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlocklistQuery extends Paging {
    /**
     * 黑名单ID
     */
    private Long blockId;
    /**
     * 状态
     */
    private Integer status;
}
