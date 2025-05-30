package com.co.kc.shortening.admin.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author kc
 */
@Data
public class AdministratorDetailGetRequest {
    @NotNull(message = "管理者ID不能为空")
    @ApiModelProperty(value = "管理者ID")
    private Long administratorId;
}
