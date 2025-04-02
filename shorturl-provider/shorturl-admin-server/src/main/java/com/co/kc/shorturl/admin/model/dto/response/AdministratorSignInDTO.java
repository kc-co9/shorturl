package com.co.kc.shorturl.admin.model.dto.response;

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
public class AdministratorSignInDTO {

    @ApiModelProperty(value = "授权TOKEN")
    private String authToken;
}
