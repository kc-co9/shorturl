package com.co.kc.shorturl.admin.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author kc
 */
@Data
public class BlacklistAddRequest {
    @NotBlank(message = "url不能为空")
    private String url;

    /**
     * 备注
     */
    private String remark;
}
