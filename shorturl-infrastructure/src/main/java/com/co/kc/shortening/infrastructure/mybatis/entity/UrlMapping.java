package com.co.kc.shortening.infrastructure.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.co.kc.shortening.infrastructure.mybatis.enums.UrlMappingStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * url标识符(UrlKey)实体类
 *
 * @author makejava
 * @since 2025-03-27 11:21:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UrlMapping extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -67304243397547361L;
    /**
     * 短链ID
     */
    @TableField("`short_id`")
    private Long shortId;
    /**
     * 原始链接
     */
    @TableField("`url`")
    private String url;
    /**
     * 原始链接code
     */
    @TableField("`code`")
    private String code;
    /**
     * 原始链接hash值
     */
    @TableField("`hash`")
    private String hash;
    /**
     * 状态 0-未知 1-激活 2-失效
     */
    @TableField("`status`")
    private UrlMappingStatus status;
    /**
     * 有效期-开始时间
     */
    @TableField("`valid_start`")
    private LocalDateTime validStart;
    /**
     * 有效期-结束时间
     */
    @TableField("`valid_end`")
    private LocalDateTime validEnd;
}

