package com.co.kc.shortening.application.model.cqrs.dto;

import com.co.kc.shortening.shorturl.domain.model.ShorturlStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 短链DTO
 *
 * @author kc
 */
@Data
public class ShorturlQueryDTO {
    /**
     * 短链ID
     */
    private Long shortId;
    /**
     * 短链Code
     */
    private String code;
    /**
     * 原始链接
     */
    private String rawLink;
    /**
     * 短链
     */
    private String shortLink;
    /**
     * 状态
     */
    private ShorturlStatus status;
    /**
     * 有效期-开始时间
     */
    private LocalDateTime validStart;
    /**
     * 有效期-结束时间
     */
    private LocalDateTime validEnd;
}
