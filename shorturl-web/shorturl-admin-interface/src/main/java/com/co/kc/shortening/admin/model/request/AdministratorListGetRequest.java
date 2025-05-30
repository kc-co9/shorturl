package com.co.kc.shortening.admin.model.request;

import com.co.kc.shortening.application.model.io.Paging;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdministratorListGetRequest extends Paging {

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
