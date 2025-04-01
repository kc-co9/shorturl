package com.co.kc.shorturl.admin.model.dto.request;

import com.co.kc.shorturl.common.model.io.Paging;
import com.co.kc.shorturl.repository.enums.UrlKeyStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShorturlListRequest extends Paging {

    @ApiModelProperty(value = "短链KEY")
    private String key;

    @ApiModelProperty(value = "状态")
    private UrlKeyStatus status;

}
