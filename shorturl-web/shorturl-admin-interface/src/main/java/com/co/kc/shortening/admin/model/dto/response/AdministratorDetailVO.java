package com.co.kc.shortening.admin.model.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministratorDetailVO {

    @ApiModelProperty(value = "管理者ID")
    private Long administratorId;

    @ApiModelProperty(value = "管理者名字")
    private String administratorName;
}
