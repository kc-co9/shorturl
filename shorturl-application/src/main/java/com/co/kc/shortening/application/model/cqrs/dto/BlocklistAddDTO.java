package com.co.kc.shortening.application.model.cqrs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlocklistAddDTO {
    /**
     * 黑名单ID
     */
    private Long blockId;
    /**
     * 被禁链接
     */
    private String blockLink;
}
