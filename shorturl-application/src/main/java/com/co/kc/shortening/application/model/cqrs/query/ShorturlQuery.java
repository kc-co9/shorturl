package com.co.kc.shortening.application.model.cqrs.query;

import com.co.kc.shortening.application.model.io.Paging;
import com.co.kc.shortening.shorturl.domain.model.ShorturlStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author kc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShorturlQuery extends Paging {
    /**
     * 短链ID
     */
    private Long shortId;

    /**
     * 短链Code
     */
    private String code;

    /**
     * 状态
     */
    private ShorturlStatus status;

    /**
     * 有效期开始
     */
    private LocalDateTime validTimeStart;

    /**
     * 有效期结束
     */
    private LocalDateTime validTimeEnd;
}
