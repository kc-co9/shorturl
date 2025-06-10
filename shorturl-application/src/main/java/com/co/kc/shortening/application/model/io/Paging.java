package com.co.kc.shortening.application.model.io;

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
    public Paging getPaging() {
        return this;
    }

    /**
     * set Paging Info
     *
     * @param paging is set
     */
    @Transient
    @JsonIgnore
    public void setPaging(Paging paging) {
        this.pageNo = paging.getPageNo();
        this.pageSize = paging.getPageSize();
    }
}
