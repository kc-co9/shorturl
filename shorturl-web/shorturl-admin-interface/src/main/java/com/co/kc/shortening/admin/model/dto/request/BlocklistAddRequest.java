package com.co.kc.shortening.admin.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author kc
 */
@Data
public class BlocklistAddRequest {
    @NotBlank(message = "被禁链接不能为空")
    @ApiModelProperty(value = "被禁链接")
    private String url;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;
}
