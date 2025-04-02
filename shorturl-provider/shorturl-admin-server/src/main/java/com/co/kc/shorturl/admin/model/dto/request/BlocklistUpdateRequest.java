package com.co.kc.shorturl.admin.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author kc
 */
@Data
public class BlocklistUpdateRequest {
    @NotNull(message = "ID")
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
