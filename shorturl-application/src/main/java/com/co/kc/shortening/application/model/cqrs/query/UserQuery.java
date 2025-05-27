package com.co.kc.shortening.application.model.cqrs.query;

import com.co.kc.shortening.application.model.io.Paging;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQuery extends Paging {
    /**
     * 管理者ID
     */
    private Long userId;
    /**
     * 管理者账号
     */
    private String email;

    /**
     * 管理者用户名
     */
    private String username;
}
