package com.co.kc.shortening.admin.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author kc
 */
@Data
public class BlocklistUpdateRequest {
    @NotNull(message = "ID为空")
    @ApiModelProperty(value = "blockId")
    private Long blockId;

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
