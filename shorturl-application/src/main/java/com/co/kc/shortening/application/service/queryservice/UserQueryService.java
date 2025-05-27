package com.co.kc.shortening.application.service.queryservice;

import com.co.kc.shortening.application.model.cqrs.dto.UserQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserQuery;
import com.co.kc.shortening.application.model.io.PagingResult;

/**
 * 用户-查询服务
 *
 * @author kc
 */
public interface UserQueryService {
    /**
     * 查询用户
     *
     * @param query 查询参数
     * @return UserDTO
     */
    PagingResult<UserQueryDTO> queryUser(UserQuery query);
}
