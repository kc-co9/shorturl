package com.co.kc.shortening.infrastructure.service.query;


import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlAddCommand;
import com.co.kc.shortening.application.model.cqrs.dto.ShorturlQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.ShorturlQuery;
import com.co.kc.shortening.application.model.io.Paging;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.service.app.ShorturlAppService;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.shorturl.domain.model.ShorturlFactory;
import com.co.kc.shortening.shorturl.domain.model.ShorturlStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@TestPropertySource(properties = {
        "shorturl.shortDomain=" + ShorturlFactory.testShortDomain,
})
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class ShorturlQueryMySqlServiceTests {
    @Autowired
    private ShorturlAppService shorturlAppService;
    @Autowired
    private ShorturlQueryMySqlService shorturlQueryMySqlService;

    @BeforeEach
    void initShorturl() {
        for (int i = 0; i < 21; i++) {
            ShorturlAddCommand addCommand = new ShorturlAddCommand();
            addCommand.setRawLink("https://www.raw.com/" + i);
            addCommand.setStatus(ShorturlStatus.ONLINE);
            addCommand.setValidTimeStart(LocalDateTime.now().minusDays(1));
            addCommand.setValidTimeEnd(LocalDateTime.now().plusDays(1));
            shorturlAppService.add(addCommand);
        }
    }

    @Test
    void testQueryShorturl() {
        for (long pageNo = 1; ; pageNo++) {
            ShorturlQuery shorturlQuery = new ShorturlQuery();
            shorturlQuery.setPaging(new Paging(pageNo, 10L));
            PagingResult<ShorturlQueryDTO> pagingResult = shorturlQueryMySqlService.queryShorturl(shorturlQuery);
            if (CollectionUtils.isEmpty(pagingResult.getRecords())) {
                break;
            }
            Assertions.assertEquals(21, pagingResult.getTotalCount().longValue());
            Assertions.assertEquals(3, pagingResult.getTotalPages().longValue());
            Assertions.assertEquals(shorturlQuery.getPaging(), pagingResult.getPaging());

            ShorturlQueryDTO shorturlQueryDTO = pagingResult.getRecords().get(0);
            checkQueryByShortId(shorturlQueryDTO);
            checkQueryByCode(shorturlQueryDTO);
            checkQueryByStatus(shorturlQueryDTO);
            checkQueryByValidTime(shorturlQueryDTO);
        }
    }

    private void checkQueryByShortId(ShorturlQueryDTO parentQueryDTO) {
        ShorturlQuery shorturlQuery = new ShorturlQuery();
        shorturlQuery.setShortId(parentQueryDTO.getShortId());
        shorturlQuery.setPaging(new Paging(1L, 1L));
        PagingResult<ShorturlQueryDTO> pagingResult = shorturlQueryMySqlService.queryShorturl(shorturlQuery);
        Assertions.assertEquals(1L, pagingResult.getTotalCount().longValue());
        Assertions.assertEquals(1L, pagingResult.getTotalPages().longValue());
        Assertions.assertEquals(1L, pagingResult.getRecords().size());

        ShorturlQueryDTO queryDTO = pagingResult.getRecords().get(0);
        Assertions.assertEquals(parentQueryDTO.getShortId(), queryDTO.getShortId());
        Assertions.assertEquals(parentQueryDTO.getCode(), queryDTO.getCode());
        Assertions.assertEquals(parentQueryDTO.getRawLink(), queryDTO.getRawLink());
        Assertions.assertEquals(parentQueryDTO.getShortLink(), queryDTO.getShortLink());
        Assertions.assertEquals(parentQueryDTO.getValidStart(), queryDTO.getValidStart());
        Assertions.assertEquals(parentQueryDTO.getValidEnd(), queryDTO.getValidEnd());
        Assertions.assertEquals(parentQueryDTO.getStatus(), queryDTO.getStatus());
    }

    private void checkQueryByCode(ShorturlQueryDTO parentQueryDTO) {
        ShorturlQuery shorturlQuery = new ShorturlQuery();
        shorturlQuery.setCode(parentQueryDTO.getCode());
        shorturlQuery.setPaging(new Paging(1L, 1L));
        PagingResult<ShorturlQueryDTO> pagingResult = shorturlQueryMySqlService.queryShorturl(shorturlQuery);
        Assertions.assertEquals(1L, pagingResult.getTotalCount().longValue());
        Assertions.assertEquals(1L, pagingResult.getTotalPages().longValue());
        Assertions.assertEquals(1L, pagingResult.getRecords().size());

        ShorturlQueryDTO queryDTO = pagingResult.getRecords().get(0);
        Assertions.assertEquals(parentQueryDTO.getShortId(), queryDTO.getShortId());
        Assertions.assertEquals(parentQueryDTO.getCode(), queryDTO.getCode());
        Assertions.assertEquals(parentQueryDTO.getRawLink(), queryDTO.getRawLink());
        Assertions.assertEquals(parentQueryDTO.getShortLink(), queryDTO.getShortLink());
        Assertions.assertEquals(parentQueryDTO.getValidStart(), queryDTO.getValidStart());
        Assertions.assertEquals(parentQueryDTO.getValidEnd(), queryDTO.getValidEnd());
        Assertions.assertEquals(parentQueryDTO.getStatus(), queryDTO.getStatus());
    }

    private void checkQueryByStatus(ShorturlQueryDTO parentQueryDTO) {
        ShorturlQuery shorturlQuery = new ShorturlQuery();
        shorturlQuery.setStatus(parentQueryDTO.getStatus());
        shorturlQuery.setPaging(new Paging(1L, 10L));
        PagingResult<ShorturlQueryDTO> pagingResult = shorturlQueryMySqlService.queryShorturl(shorturlQuery);
        Assertions.assertEquals(21L, pagingResult.getTotalCount().longValue());
        Assertions.assertEquals(3L, pagingResult.getTotalPages().longValue());
    }

    private void checkQueryByValidTime(ShorturlQueryDTO parentQueryDTO) {
        ShorturlQuery shorturlQuery = new ShorturlQuery();
        shorturlQuery.setValidTimeStart(parentQueryDTO.getValidStart());
        shorturlQuery.setValidTimeEnd(parentQueryDTO.getValidEnd());
        shorturlQuery.setPaging(new Paging(1L, 10L));
        PagingResult<ShorturlQueryDTO> pagingResult = shorturlQueryMySqlService.queryShorturl(shorturlQuery);
        Assertions.assertEquals(21L, pagingResult.getTotalCount().longValue());
        Assertions.assertEquals(3L, pagingResult.getTotalPages().longValue());
    }
}
