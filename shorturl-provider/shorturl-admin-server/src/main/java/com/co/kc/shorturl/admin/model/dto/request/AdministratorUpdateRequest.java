package com.co.kc.shorturl.admin.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author kc
 */
@Data
public class AdministratorUpdateRequest {
    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @NotBlank(message = "管理者账号不能为空")
    @ApiModelProperty(value = "管理者账号")
    private String account;

    @NotBlank(message = "管理者密码不能为空")
    @ApiModelProperty(value = "管理者密码")
    private String password;

    @NotBlank(message = "管理者用户名不能为空")
    @ApiModelProperty(value = "管理者用户名")
    private String username;

    @NotBlank(message = "管理者邮箱不能为空")
    @ApiModelProperty(value = "管理者邮箱")
    private String email;
}
