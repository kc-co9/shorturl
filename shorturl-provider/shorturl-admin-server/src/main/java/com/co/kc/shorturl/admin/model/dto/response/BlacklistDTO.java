package com.co.kc.shorturl.admin.model.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kc
 */
@Data
public class BlacklistDTO {
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Long id;
    /**
     * 链接
     */
    @ApiModelProperty(value = "被禁链接")
    private String url;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
