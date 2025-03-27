package com.co.kc.shorturl.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    public Paging getPaging() {
        return new Paging(pageNo, pageSize);
    }
}
