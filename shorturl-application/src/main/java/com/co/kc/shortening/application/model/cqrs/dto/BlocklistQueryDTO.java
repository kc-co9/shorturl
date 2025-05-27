package com.co.kc.shortening.application.model.cqrs.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 黑名单DTO
 *
 * @author kc
 */
@Data
public class BlocklistQueryDTO {
    /**
     * 黑名单ID
     */
    private Long blockId;
    /**
     * 被禁链接
     */
    private String blockLink;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
