package com.co.kc.shortening.application.model.io;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;

/**
 * @author kc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paging {

    /**
     * page no
     */
    private Long pageNo;

    /**
     * page size
     */
    private Long pageSize;

    /**
     * get paging info
     */
    @Transient
    @JsonIgnore
    @JSONField(serialize = false, deserialize = false)
    public Paging getPaging() {
        return this;
    }
}
