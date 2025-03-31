package com.co.kc.shorturl.admin.model.dto.request;

import com.co.kc.shorturl.repository.enums.UrlKeyStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author kc
 */
@Data
public class ShorturlUpdateRequest {
    @NotNull(message = "ID不能为空")
    private Long id;

    @NotNull(message = "状态不能为空")
    private UrlKeyStatus status;

    @NotNull(message = "有效期开始时间")
    private LocalDateTime validStart;

    @NotNull(message = "有效期结束时间")
    private LocalDateTime validEnd;
}
