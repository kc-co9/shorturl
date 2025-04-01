package com.co.kc.shorturl.admin.model.dto.request;

import com.co.kc.shorturl.repository.enums.UrlKeyStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author kc
 */
@Data
public class ShorturlUpdateRequest {
    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "ID")
    private Long id;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态")
    private UrlKeyStatus status;

    @NotNull(message = "有效期开始时间不能为空")
    @ApiModelProperty(value = "有效期开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validStart;

    @NotNull(message = "有效期结束时间不能为空")
    @ApiModelProperty(value = "有效期结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validEnd;
}
