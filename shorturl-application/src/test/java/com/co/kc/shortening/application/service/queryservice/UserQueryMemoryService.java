package com.co.kc.shortening.application.service.queryservice;

import com.co.kc.shortening.application.model.cqrs.dto.UserQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserQuery;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.user.domain.model.UserRepository;

public class UserQueryMemoryService implements UserQueryService {

    public UserRepository userRepository;

    public UserQueryMemoryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PagingResult<UserQueryDTO> queryUser(UserQuery query) {
        return null;
    }
}
