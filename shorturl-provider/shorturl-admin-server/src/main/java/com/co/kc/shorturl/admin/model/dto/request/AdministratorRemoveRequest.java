package com.co.kc.shorturl.admin.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author kc
 */
@Data
public class AdministratorRemoveRequest {
    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID")
    private Long id;

}
