package com.co.kc.shortening.shorturl.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 短链ID-值对象
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode
public class ShortId {

    private final Long id;

    public ShortId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("id is less than or equal to 0");
        }
        this.id = id;
    }
}
