package com.co.kc.shortening.infrastructure.service.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.co.kc.shortening.application.model.cqrs.dto.UserQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserQuery;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.service.queryservice.UserQueryService;
import com.co.kc.shortening.infrastructure.mybatis.entity.Administrator;
import com.co.kc.shortening.infrastructure.mybatis.service.AdministratorService;
import com.co.kc.shortening.common.utils.FunctionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author kc
 */
public class UserQueryMySqlService implements UserQueryService {
    private final AdministratorService administratorService;

    public UserQueryMySqlService(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @Override
    public PagingResult<UserQueryDTO> queryUser(UserQuery query) {
        IPage<Administrator> administratorPage = administratorService.page(new Page<>(query.getPageNo(), query.getPageSize()), administratorService.getQueryWrapper()
                .eq(StringUtils.isNotBlank(query.getEmail()), Administrator::getEmail, query.getEmail())
                .eq(StringUtils.isNotBlank(query.getUsername()), Administrator::getUsername, query.getUsername())
                .orderByDesc(Administrator::getAdministratorId));
        List<UserQueryDTO> userQueryDtoList = FunctionUtils.mappingList(administratorPage.getRecords(), administrator -> {
            UserQueryDTO userQueryDto = new UserQueryDTO();
            userQueryDto.setUserId(administrator.getAdministratorId());
            userQueryDto.setUsername(administrator.getUsername());
            userQueryDto.setEmail(administrator.getEmail());
            userQueryDto.setCreateTime(administrator.getCreateTime());
            return userQueryDto;
        });
        return PagingResult.<UserQueryDTO>newBuilder()
                .paging(query.getPaging())
                .records(userQueryDtoList)
                .totalCount(administratorPage.getTotal())
                .totalPages(administratorPage.getPages())
                .build();
    }
}
