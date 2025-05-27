package com.co.kc.shortening.infrastructure.mybatis.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * url黑名单(UrlBlocklist)实体类
 *
 * @author makejava
 * @since 2025-03-27 12:03:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UrlBlocklist extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 694861458806579982L;
    /**
     * 链接
     */
    private String url;
    /**
     * 链接hash值
     */
    private String hash;
    /**
     * 备注
     */
    private String remark;
}

