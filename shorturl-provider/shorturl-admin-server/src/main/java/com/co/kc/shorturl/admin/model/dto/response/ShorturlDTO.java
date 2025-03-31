package com.co.kc.shorturl.admin.model.dto.response;

import com.co.kc.shorturl.repository.enums.UrlKeyStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author kc
 */
@Data
public class ShorturlDTO {
    /**
     * 原始链接
     */
    private String url;
    /**
     * 短链
     */
    private String shorturl;
    /**
     * 状态 0-未知 1-激活 2-失效
     */
    private UrlKeyStatus status;
    /**
     * 有效期-开始时间
     */
    private LocalDateTime validStart;
    /**
     * 有效期-结束时间
     */
    private LocalDateTime validEnd;
}
