package com.co.kc.shortening.infrastructure.mybatis.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * key生成器(KeyGen)实体类
 *
 * @author makejava
 * @since 2025-03-27 11:21:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KeyGen extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 115853032859581267L;
    /**
     * 长链接key
     */
    private Long keyStart;
    /**
     * 长链接key
     */
    private Long keyEnd;
}

