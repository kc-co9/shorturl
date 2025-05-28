package com.co.kc.shortening.admin.model.dto.request;

import com.co.kc.shortening.application.model.io.Paging;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author kc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShorturlListRequest extends Paging {

    @ApiModelProperty(value = "短链KEY")
    private String code;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "有效期开始")
    private LocalDateTime validTimeStart;

    @ApiModelProperty(value = "有效期结算")
    private LocalDateTime validTimeEnd;
}
