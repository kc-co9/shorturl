package com.co.kc.shortening.infrastructure.service.query;


import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistAddCommand;
import com.co.kc.shortening.application.model.cqrs.dto.BlocklistQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.BlocklistQuery;
import com.co.kc.shortening.application.model.io.Paging;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.service.app.BlocklistAppService;
import com.co.kc.shortening.blocklist.domain.model.BlockStatus;
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
class BlocklistMySqlQueryServiceTests {
    @Autowired
    private BlocklistAppService blocklistAppService;
    @Autowired
    private BlocklistMySqlQueryService blocklistMySqlQueryService;

    @BeforeEach
    void initBlocklist() {
        for (int i = 0; i < 21; i++) {
            BlocklistAddCommand addCommand = new BlocklistAddCommand();
            addCommand.setBlockLink("https://www.block.com/" + i);
            addCommand.setRemark("testRemark" + i);
            addCommand.setStatus(BlockStatus.ONLINE);
            blocklistAppService.add(addCommand);
        }
    }

    @Test
    void testQueryBlocklist() {
        for (long pageNo = 1; ; pageNo++) {
            BlocklistQuery blocklistQuery = new BlocklistQuery();
            blocklistQuery.setPaging(new Paging(pageNo, 10L));
            PagingResult<BlocklistQueryDTO> pagingResult = blocklistMySqlQueryService.queryBlocklist(blocklistQuery);
            if (CollectionUtils.isEmpty(pagingResult.getRecords())) {
                break;
            }
            Assertions.assertEquals(21, pagingResult.getTotalCount().longValue());
            Assertions.assertEquals(3, pagingResult.getTotalPages().longValue());
            Assertions.assertEquals(blocklistQuery.getPaging(), pagingResult.getPaging());
            BlocklistQueryDTO blocklistQueryDTO = pagingResult.getRecords().get(0);
            checkQueryByBlockId(blocklistQueryDTO);
            checkQueryByStatus(blocklistQueryDTO);
        }
    }

    private void checkQueryByBlockId(BlocklistQueryDTO parentQueryDTO) {
        BlocklistQuery blocklistQuery = new BlocklistQuery();
        blocklistQuery.setBlockId(parentQueryDTO.getBlockId());
        blocklistQuery.setPaging(new Paging(1L, 1L));
        PagingResult<BlocklistQueryDTO> pagingResult = blocklistMySqlQueryService.queryBlocklist(blocklistQuery);
        Assertions.assertEquals(1L, pagingResult.getTotalCount().longValue());
        Assertions.assertEquals(1L, pagingResult.getTotalPages().longValue());
        Assertions.assertEquals(1L, pagingResult.getRecords().size());

        BlocklistQueryDTO queryDTO = pagingResult.getRecords().get(0);
        Assertions.assertEquals(parentQueryDTO.getBlockId(), queryDTO.getBlockId());
        Assertions.assertEquals(parentQueryDTO.getBlockLink(), queryDTO.getBlockLink());
        Assertions.assertEquals(parentQueryDTO.getRemark(), queryDTO.getRemark());
        Assertions.assertEquals(parentQueryDTO.getStatus(), queryDTO.getStatus());
        Assertions.assertEquals(parentQueryDTO.getCreateTime(), queryDTO.getCreateTime());
    }

    private void checkQueryByStatus(BlocklistQueryDTO parentQueryDTO) {
        BlocklistQuery blocklistQuery = new BlocklistQuery();
        blocklistQuery.setStatus(parentQueryDTO.getStatus());
        blocklistQuery.setPaging(new Paging(1L, 10L));
        PagingResult<BlocklistQueryDTO> pagingResult = blocklistMySqlQueryService.queryBlocklist(blocklistQuery);
        Assertions.assertEquals(21, pagingResult.getTotalCount().longValue());
        Assertions.assertEquals(3, pagingResult.getTotalPages().longValue());
    }


}
