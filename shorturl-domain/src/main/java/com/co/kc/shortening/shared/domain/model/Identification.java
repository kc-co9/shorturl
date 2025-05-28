package com.co.kc.shortening.shared.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

/**
 * 委托ID-共享领域对象
 *
 * @author kc
 */
@Getter
@EqualsAndHashCode
public class Identification {
    /**
     * 数据库主键ID
     */
    private Long id;

    public Identification() {
    }

    public void setId(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id is null");
        }
        if (id <= 0L) {
            throw new IllegalArgumentException("id is less than or equal to 0");
        }
        this.id = id;
    }
}
