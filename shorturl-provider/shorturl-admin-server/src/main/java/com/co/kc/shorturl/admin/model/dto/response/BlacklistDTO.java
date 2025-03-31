package com.co.kc.shorturl.admin.model.dto.response;

import lombok.Data;

/**
 * @author kc
 */
@Data
public class BlacklistDTO {
    /**
     * ID
     */
    private Long id;
    /**
     * 链接
     */
    private String url;
    /**
     * 备注
     */
    private String remark;
}
