package com.co.kc.shorturl.admin.model.dto.request;

import com.co.kc.shorturl.repository.enums.UrlKeyStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author kc
 */
@Data
public class ShorturlStatusUpdateRequest {
    @NotNull(message = "ID不能为空")
    private Long id;

    @NotNull(message = "状态不能为空")
    private UrlKeyStatus status;
}
