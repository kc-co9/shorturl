package com.co.kc.shorturl.admin.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author kc
 */
@Data
public class ShorturlCreateRequest {
    @NotBlank(message = "原始链接不能为空")
    @ApiModelProperty(value = "原始链接")
    private String url;

    @NotNull(message = "有效期开始时间不能为空")
    @ApiModelProperty(value = "有效期开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validStart;

    @NotNull(message = "有效期结束时间不能为空")
    @ApiModelProperty(value = "有效期结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validEnd;
}
