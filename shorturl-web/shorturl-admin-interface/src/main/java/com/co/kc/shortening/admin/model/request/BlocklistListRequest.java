package com.co.kc.shortening.admin.model.request;

import com.co.kc.shortening.web.common.constants.enums.BlockFacadeStatus;
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
    @ApiModelProperty(value = "黑名单ID")
    private Long blockId;

    @ApiModelProperty(value = "状态")
    private BlockFacadeStatus status;
}
