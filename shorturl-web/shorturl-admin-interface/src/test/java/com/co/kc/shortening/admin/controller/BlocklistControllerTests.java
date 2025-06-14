package com.co.kc.shortening.admin.controller;

import com.co.kc.shortening.admin.mock.SecurityMock;
import com.co.kc.shortening.admin.model.request.BlocklistAddRequest;
import com.co.kc.shortening.admin.model.request.BlocklistRemoveRequest;
import com.co.kc.shortening.admin.model.request.BlocklistUpdateRequest;
import com.co.kc.shortening.admin.model.response.BlocklistListVO;
import com.co.kc.shortening.admin.security.MethodSecurityConfig;
import com.co.kc.shortening.admin.security.WebSecurityConfig;
import com.co.kc.shortening.web.common.constants.ParamsConstants;
import com.co.kc.shortening.admin.starter.ShortUrlAdminTestApplication;
import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.client.TokenClient;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistRemoveCommand;
import com.co.kc.shortening.application.model.cqrs.command.blocklist.BlocklistUpdateCommand;
import com.co.kc.shortening.application.model.cqrs.dto.BlocklistAddDTO;
import com.co.kc.shortening.application.model.cqrs.dto.BlocklistQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.BlocklistQuery;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.service.app.BlocklistAppService;
import com.co.kc.shortening.application.service.query.BlocklistQueryService;
import com.co.kc.shortening.blocklist.domain.model.BlocklistFactory;
import com.co.kc.shortening.common.utils.JsonUtils;
import com.co.kc.shortening.user.domain.model.UserFactory;
import com.co.kc.shortening.web.common.Result;
import com.co.kc.shortening.web.common.constants.ResultCode;
import com.co.kc.shortening.web.common.constants.enums.BaseEnum;
import com.co.kc.shortening.web.common.constants.enums.BlockFacadeStatus;
import com.co.kc.shortening.web.common.serializer.BaseEnumSerializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Collections;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlocklistController.class)  // 仅加载控制器和相关Bean
@Import({MethodSecurityConfig.class, WebSecurityConfig.class})
@ContextConfiguration(classes = ShortUrlAdminTestApplication.class)
class BlocklistControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenClient tokenClient;
    @MockBean
    private SessionClient sessionClient;
    @MockBean
    private BlocklistAppService blocklistAppService;
    @MockBean
    private BlocklistQueryService blocklistQueryService;

    private MockHttpServletRequestBuilder blocklistListRequest;
    private MockHttpServletRequestBuilder addBlocklistRequest;
    private MockHttpServletRequestBuilder updateBlocklistRequest;
    private MockHttpServletRequestBuilder removeBlocklistRequest;

    @BeforeAll
    static void initBlocklistController() {
        // 用于在Request对象序列化为JSON时，将Enum类型的对象转化为CODE
        SimpleModule baseEnumModule = new SimpleModule();
        baseEnumModule.addSerializer(BaseEnum.class, new BaseEnumSerializer());
        JsonUtils.getMapper().registerModule(baseEnumModule);
    }

    @BeforeEach
    public void initMockRequestBuilder() {
        SecurityMock.mockTestUserHasAuthenticated(tokenClient, sessionClient);

        blocklistListRequest =
                get("/blocklist/v1/blocklistList")
                        .header(ParamsConstants.TOKEN, UserFactory.testUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
        addBlocklistRequest =
                post("/blocklist/v1/addBlocklist")
                        .header(ParamsConstants.TOKEN, UserFactory.testUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
        updateBlocklistRequest =
                post("/blocklist/v1/updateBlocklist")
                        .header(ParamsConstants.TOKEN, UserFactory.testUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
        removeBlocklistRequest =
                post("/blocklist/v1/removeBlocklist")
                        .header(ParamsConstants.TOKEN, UserFactory.testUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
    }

    @Test
    void testBlocklistListByBlockId() throws Exception {
        mockOnlyTestBlocklistInBlocklistList();

        MockHttpServletRequestBuilder httpGet =
                blocklistListRequest
                        .param("blockId", String.valueOf(BlocklistFactory.testBlockId))
                        .param("status", "")
                        .param("pageNo", "1")
                        .param("pageSize", "10");

        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(this::shouldOnlyContainTestBlocklistInBlocklistList);

        ArgumentCaptor<BlocklistQuery> argumentCaptor = ArgumentCaptor.forClass(BlocklistQuery.class);
        verify(blocklistQueryService).queryBlocklist(argumentCaptor.capture());
        BlocklistQuery blocklistQuery = argumentCaptor.getValue();
        Assertions.assertEquals(BlocklistFactory.testBlockId, blocklistQuery.getBlockId());
        Assertions.assertNull(blocklistQuery.getStatus());
        Assertions.assertEquals(1L, blocklistQuery.getPageNo().longValue());
        Assertions.assertEquals(10L, blocklistQuery.getPageSize().longValue());
    }

    @Test
    void testBlocklistListByStatus() throws Exception {
        mockOnlyTestBlocklistInBlocklistList();

        Integer status = BlockFacadeStatus.convert(BlocklistFactory.testBlockStatus).get().getCode();
        MockHttpServletRequestBuilder httpGet =
                blocklistListRequest
                        .param("blockId", "")
                        .param("status", String.valueOf(status))
                        .param("pageNo", "1")
                        .param("pageSize", "10");

        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(this::shouldOnlyContainTestBlocklistInBlocklistList);

        ArgumentCaptor<BlocklistQuery> argumentCaptor = ArgumentCaptor.forClass(BlocklistQuery.class);
        verify(blocklistQueryService).queryBlocklist(argumentCaptor.capture());
        BlocklistQuery blocklistQuery = argumentCaptor.getValue();
        Assertions.assertNull(blocklistQuery.getBlockId());
        Assertions.assertEquals(BlocklistFactory.testBlockStatus, blocklistQuery.getStatus());
        Assertions.assertEquals(1L, blocklistQuery.getPageNo().longValue());
        Assertions.assertEquals(10L, blocklistQuery.getPageSize().longValue());
    }

    @Test
    void testAddBlocklist() throws Exception {
        BlocklistAddDTO blocklistAddDTO = new BlocklistAddDTO(BlocklistFactory.testBlockId, BlocklistFactory.testBlockLink);
        doReturn(blocklistAddDTO).when(blocklistAppService).add(any(BlocklistAddCommand.class));

        BlocklistAddRequest body = new BlocklistAddRequest();
        body.setBlockLink(BlocklistFactory.testBlockLink);
        body.setStatus(BlockFacadeStatus.convert(BlocklistFactory.testBlockStatus).get());
        body.setRemark(BlocklistFactory.testBlockRemark);
        MockHttpServletRequestBuilder httpPost = addBlocklistRequest.content(JsonUtils.toJson(body));
        mockMvc.perform(httpPost)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<?> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<?>>() {
                    });
                    Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
                });

        ArgumentCaptor<BlocklistAddCommand> argumentCaptor = ArgumentCaptor.forClass(BlocklistAddCommand.class);
        verify(blocklistAppService).add(argumentCaptor.capture());
        BlocklistAddCommand addCommand = argumentCaptor.getValue();
        Assertions.assertEquals(BlocklistFactory.testBlockLink, addCommand.getBlockLink());
        Assertions.assertEquals(BlocklistFactory.testBlockRemark, addCommand.getRemark());
        Assertions.assertEquals(BlocklistFactory.testBlockStatus, addCommand.getStatus());
    }

    @Test
    void testUpdateBlocklist() throws Exception {
        doNothing().when(blocklistAppService).update(any(BlocklistUpdateCommand.class));

        BlocklistUpdateRequest body = new BlocklistUpdateRequest();
        body.setBlockId(BlocklistFactory.testBlockId);
        body.setStatus(BlockFacadeStatus.convert(BlocklistFactory.testBlockStatus).get());
        body.setRemark(BlocklistFactory.testBlockRemark);
        MockHttpServletRequestBuilder httpPost = updateBlocklistRequest.content(JsonUtils.toJson(body));
        mockMvc.perform(httpPost)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<?> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<?>>() {
                    });
                    Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
                });

        ArgumentCaptor<BlocklistUpdateCommand> argumentCaptor = ArgumentCaptor.forClass(BlocklistUpdateCommand.class);
        verify(blocklistAppService).update(argumentCaptor.capture());
        BlocklistUpdateCommand updateCommand = argumentCaptor.getValue();
        Assertions.assertEquals(BlocklistFactory.testBlockId, updateCommand.getBlockId());
        Assertions.assertEquals(BlocklistFactory.testBlockRemark, updateCommand.getRemark());
        Assertions.assertEquals(BlocklistFactory.testBlockStatus, updateCommand.getStatus());
    }

    @Test
    public void removeBlocklist() throws Exception {
        doNothing().when(blocklistAppService).remove(any(BlocklistRemoveCommand.class));

        BlocklistRemoveRequest body = new BlocklistRemoveRequest();
        body.setBlockId(BlocklistFactory.testBlockId);
        MockHttpServletRequestBuilder httpPost = removeBlocklistRequest.content(JsonUtils.toJson(body));
        mockMvc.perform(httpPost)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<?> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<?>>() {
                    });
                    Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
                });

        ArgumentCaptor<BlocklistRemoveCommand> argumentCaptor = ArgumentCaptor.forClass(BlocklistRemoveCommand.class);
        verify(blocklistAppService).remove(argumentCaptor.capture());
        BlocklistRemoveCommand removeCommand = argumentCaptor.getValue();
        Assertions.assertEquals(BlocklistFactory.testBlockId, removeCommand.getBlockId());
    }

    private void mockOnlyTestBlocklistInBlocklistList() {
        doAnswer((invocation) -> {
            BlocklistQuery invocationParams = invocation.getArgument(0);
            if (BlocklistFactory.testBlockId.equals(invocationParams.getBlockId())
                    || BlocklistFactory.testBlockStatus.equals(invocationParams.getStatus())) {
                BlocklistQueryDTO blocklistQueryDTO = new BlocklistQueryDTO();
                blocklistQueryDTO.setBlockId(BlocklistFactory.testBlockId);
                blocklistQueryDTO.setBlockLink(BlocklistFactory.testBlockLink);
                blocklistQueryDTO.setRemark(BlocklistFactory.testBlockRemark);
                blocklistQueryDTO.setStatus(BlocklistFactory.testBlockStatus);
                blocklistQueryDTO.setCreateTime(LocalDateTime.now());
                return PagingResult.<BlocklistQueryDTO>newBuilder()
                        .paging(invocationParams.getPaging())
                        .records(Collections.singletonList(blocklistQueryDTO))
                        .totalCount(1L)
                        .build();
            }
            return PagingResult.newBuilder()
                    .paging(invocationParams.getPaging())
                    .records(Collections.emptyList())
                    .totalCount(0L)
                    .build();
        }).when(blocklistQueryService).queryBlocklist(any(BlocklistQuery.class));
    }

    private void shouldOnlyContainTestBlocklistInBlocklistList(MvcResult mvcResult) throws UnsupportedEncodingException {
        String resultJson = mvcResult.getResponse().getContentAsString();
        Result<PagingResult<BlocklistListVO>> result = JsonUtils.fromJson(resultJson, new TypeReference<Result<PagingResult<BlocklistListVO>>>() {
        });
        Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
        Assertions.assertEquals(1L, result.getData().getTotalPages().longValue());
        Assertions.assertEquals(1L, result.getData().getTotalCount().longValue());
        Assertions.assertEquals(1L, result.getData().getPaging().getPageNo().longValue());
        Assertions.assertEquals(10L, result.getData().getPaging().getPageSize().longValue());
        Assertions.assertEquals(1L, result.getData().getRecords().size());

        BlocklistListVO blocklistListVO = result.getData().getRecords().get(0);
        Assertions.assertEquals(BlocklistFactory.testBlockId, blocklistListVO.getBlockId());
        Assertions.assertEquals(BlockFacadeStatus.convert(BlocklistFactory.testBlockStatus).get(), blocklistListVO.getStatus());
        Assertions.assertEquals(BlocklistFactory.testBlockLink, blocklistListVO.getBlockLink());
        Assertions.assertEquals(BlocklistFactory.testBlockRemark, blocklistListVO.getRemark());
    }
}
