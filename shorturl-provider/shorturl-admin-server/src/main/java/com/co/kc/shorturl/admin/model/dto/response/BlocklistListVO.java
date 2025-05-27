package com.co.kc.shorturl.admin.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author kc
 */
@Data
public class BlocklistListVO {
    /**
     * ID
     */
    @ApiModelProperty(value = "黑名单ID")
    private Long blockId;
    /**
     * 链接
     */
    @ApiModelProperty(value = "被禁链接")
    private String blockLink;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
