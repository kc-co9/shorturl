package com.co.kc.shortening.admin.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author kc
 */
@Data
public class BlocklistAddRequest {
    @NotBlank(message = "被禁链接不能为空")
    @ApiModelProperty(value = "被禁链接")
    private String blockLink;

    /**
     * 状态
     */
    @NotNull(message = "状态为空")
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


}
