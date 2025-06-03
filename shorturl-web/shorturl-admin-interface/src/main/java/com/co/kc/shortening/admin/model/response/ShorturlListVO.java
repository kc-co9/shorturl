package com.co.kc.shortening.admin.model.response;

import com.co.kc.shortening.web.common.constants.enums.ShorturlFacadeStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author kc
 */
@Data
public class ShorturlListVO {
    @ApiModelProperty(value = "短链ID")
    private Long shortId;
    /**
     * 原始链接
     */
    @ApiModelProperty(value = "原始链接")
    private String rawLink;
    /**
     * 短链
     */
    @ApiModelProperty(value = "短链")
    private String shortLink;
    /**
     * 状态 0-未知 1-激活 2-失效
     */
    @ApiModelProperty(value = "状态")
    private ShorturlFacadeStatus status;
    /**
     * 有效期-开始时间
     */
    @ApiModelProperty(value = "有效期-开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validTimeStart;
    /**
     * 有效期-结束时间
     */
    @ApiModelProperty(value = "有效期-结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validTimeEnd;
}
