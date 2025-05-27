package com.co.kc.shortening.application.model.cqrs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author kc
 */
@Data
@AllArgsConstructor
public class BlocklistDTO {
    /**
     * 黑名单ID
     */
    private Long blockId;
    /**
     * 被禁链接
     */
    private String blockLink;
}
