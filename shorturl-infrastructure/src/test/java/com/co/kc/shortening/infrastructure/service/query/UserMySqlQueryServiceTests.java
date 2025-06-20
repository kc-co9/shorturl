package com.co.kc.shortening.infrastructure.service.query;


import com.co.kc.shortening.application.model.cqrs.command.user.UserAddCommand;
import com.co.kc.shortening.application.model.cqrs.dto.UserQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserQuery;
import com.co.kc.shortening.application.model.io.Paging;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.service.app.UserAppService;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class UserMySqlQueryServiceTests {
    @Autowired
    private UserAppService userAppService;
    @Autowired
    private UserMySqlQueryService userMySqlQueryService;

    @BeforeEach
    void initUser() {
        for (int i = 0; i < 21; i++) {
            UserAddCommand addCommand =
                    new UserAddCommand(i + "@shorturl.com", "testUserName" + i, "testRawPassword" + i);
            userAppService.addUser(addCommand);
        }
    }

    @Test
    void testQueryUser() {
        for (long pageNo = 1; ; pageNo++) {
            UserQuery userQuery = new UserQuery();
            userQuery.setPaging(new Paging(pageNo, 10L));
            PagingResult<UserQueryDTO> pageResult = userMySqlQueryService.queryUser(userQuery);
            if (CollectionUtils.isEmpty(pageResult.getRecords())) {
                break;
            }
            Assertions.assertEquals(21, pageResult.getTotalCount().longValue());
            Assertions.assertEquals(3, pageResult.getTotalPages().longValue());
            Assertions.assertEquals(userQuery.getPaging(), pageResult.getPaging());

            UserQueryDTO userQueryDTO = pageResult.getRecords().get(0);
            checkQueryByUserId(userQueryDTO);
            checkQueryByUserEmail(userQueryDTO);
            checkQueryByUserName(userQueryDTO);
        }
    }

    private void checkQueryByUserId(UserQueryDTO parentQueryDTO) {
        UserQuery query = new UserQuery();
        query.setUserId(parentQueryDTO.getUserId());
        query.setPaging(new Paging(1L, 1L));
        PagingResult<UserQueryDTO> pagingResult = userMySqlQueryService.queryUser(query);
        Assertions.assertEquals(1L, pagingResult.getTotalCount().longValue());
        Assertions.assertEquals(1L, pagingResult.getTotalPages().longValue());
        Assertions.assertEquals(1L, pagingResult.getRecords().size());

        UserQueryDTO queryDTO = pagingResult.getRecords().get(0);
        Assertions.assertEquals(parentQueryDTO.getUserId(), queryDTO.getUserId());
        Assertions.assertEquals(parentQueryDTO.getUsername(), queryDTO.getUsername());
        Assertions.assertEquals(parentQueryDTO.getEmail(), queryDTO.getEmail());
        Assertions.assertEquals(parentQueryDTO.getCreateTime(), queryDTO.getCreateTime());
    }

    private void checkQueryByUserEmail(UserQueryDTO parentQueryDTO) {
        UserQuery query = new UserQuery();
        query.setEmail(parentQueryDTO.getEmail());
        query.setPaging(new Paging(1L, 1L));
        PagingResult<UserQueryDTO> pagingResult = userMySqlQueryService.queryUser(query);
        Assertions.assertEquals(1L, pagingResult.getTotalCount().longValue());
        Assertions.assertEquals(1L, pagingResult.getTotalPages().longValue());
        Assertions.assertEquals(1L, pagingResult.getRecords().size());

        UserQueryDTO queryDTO = pagingResult.getRecords().get(0);
        Assertions.assertEquals(parentQueryDTO.getUserId(), queryDTO.getUserId());
        Assertions.assertEquals(parentQueryDTO.getUsername(), queryDTO.getUsername());
        Assertions.assertEquals(parentQueryDTO.getEmail(), queryDTO.getEmail());
        Assertions.assertEquals(parentQueryDTO.getCreateTime(), queryDTO.getCreateTime());
    }

    private void checkQueryByUserName(UserQueryDTO parentQueryDTO) {
        UserQuery query = new UserQuery();
        query.setUsername(parentQueryDTO.getUsername());
        query.setPaging(new Paging(1L, 1L));
        PagingResult<UserQueryDTO> pagingResult = userMySqlQueryService.queryUser(query);
        Assertions.assertEquals(1L, pagingResult.getTotalCount().longValue());
        Assertions.assertEquals(1L, pagingResult.getTotalPages().longValue());
        Assertions.assertEquals(1L, pagingResult.getRecords().size());

        UserQueryDTO queryDTO = pagingResult.getRecords().get(0);
        Assertions.assertEquals(parentQueryDTO.getUserId(), queryDTO.getUserId());
        Assertions.assertEquals(parentQueryDTO.getUsername(), queryDTO.getUsername());
        Assertions.assertEquals(parentQueryDTO.getEmail(), queryDTO.getEmail());
        Assertions.assertEquals(parentQueryDTO.getCreateTime(), queryDTO.getCreateTime());
    }
}
