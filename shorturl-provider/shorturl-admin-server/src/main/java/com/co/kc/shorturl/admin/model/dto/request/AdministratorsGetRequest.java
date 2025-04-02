package com.co.kc.shorturl.admin.model.dto.request;

import com.co.kc.shorturl.common.model.io.Paging;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdministratorsGetRequest extends Paging {
    /**
     * 管理者账号
     */
    @ApiModelProperty(value = "管理者账号")
    private String account;

    /**
     * 管理者用户名
     */
    @ApiModelProperty(value = "管理者用户名")
    private String username;

    /**
     * 管理者邮箱
     */
    @ApiModelProperty(value = "管理者邮箱")
    private String email;
}
