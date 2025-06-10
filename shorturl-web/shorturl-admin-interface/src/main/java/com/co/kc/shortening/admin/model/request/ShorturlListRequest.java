package com.co.kc.shortening.admin.model.request;

import com.co.kc.shortening.application.model.io.Paging;
import com.co.kc.shortening.web.common.constants.enums.ShorturlFacadeStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author kc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShorturlListRequest extends Paging {

    @ApiModelProperty(value = "短链ID")
    private Long shortId;

    @ApiModelProperty(value = "短链KEY")
    private String code;

    @ApiModelProperty(value = "状态")
    private ShorturlFacadeStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "有效期开始")
    private LocalDateTime validTimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "有效期结算")
    private LocalDateTime validTimeEnd;
}
