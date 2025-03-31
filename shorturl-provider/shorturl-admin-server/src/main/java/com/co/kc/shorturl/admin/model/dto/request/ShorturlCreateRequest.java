package com.co.kc.shorturl.admin.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author kc
 */
@Data
public class ShorturlCreateRequest {
    @NotBlank(message = "url不能为空")
    private String url;

    @NotNull(message = "有效期开始时间")
    private LocalDateTime validStart;

    @NotNull(message = "有效期结束时间")
    private LocalDateTime validEnd;
}
