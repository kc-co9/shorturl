package com.co.kc.shorturl.admin.model.dto.response;

import com.co.kc.shorturl.repository.enums.UrlKeyStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author kc
 */
@Data
public class ShorturlDTO {
    @ApiModelProperty(value = "ID")
    private Long id;
    /**
     * 原始链接
     */
    @ApiModelProperty(value = "原始链接")
    private String url;
    /**
     * 短链
     */
    @ApiModelProperty(value = "短链")
    private String shorturl;
    /**
     * 状态 0-未知 1-激活 2-失效
     */
    @ApiModelProperty(value = "状态")
    private UrlKeyStatus status;
    /**
     * 有效期-开始时间
     */
    @ApiModelProperty(value = "有效期-开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validStart;
    /**
     * 有效期-结束时间
     */
    @ApiModelProperty(value = "有效期-结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validEnd;
}
