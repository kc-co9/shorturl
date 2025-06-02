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
public class BlocklistListRequest extends Paging {
    @ApiModelProperty(value = "状态")
    private Integer status;
}
