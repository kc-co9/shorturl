package com.co.kc.shortening.admin.model.response;

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
public class ShorturlAddVO {
    @ApiModelProperty(value = "短链")
    private String shorturl;
}
