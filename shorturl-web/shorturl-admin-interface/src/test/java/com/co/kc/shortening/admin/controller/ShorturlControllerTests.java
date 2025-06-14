package com.co.kc.shortening.admin.controller;

import com.co.kc.shortening.admin.mock.SecurityMock;
import com.co.kc.shortening.admin.model.request.ShorturlAddRequest;
import com.co.kc.shortening.admin.model.request.ShorturlUpdateRequest;
import com.co.kc.shortening.admin.model.response.ShorturlAddVO;
import com.co.kc.shortening.admin.model.response.ShorturlListVO;
import com.co.kc.shortening.admin.security.MethodSecurityConfig;
import com.co.kc.shortening.admin.security.WebSecurityConfig;
import com.co.kc.shortening.web.common.constants.ParamsConstants;
import com.co.kc.shortening.admin.starter.ShortUrlAdminTestApplication;
import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.client.TokenClient;
import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlUpdateCommand;
import com.co.kc.shortening.application.model.cqrs.dto.ShorturlAddDTO;
import com.co.kc.shortening.application.model.cqrs.dto.ShorturlQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.ShorturlQuery;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.service.app.ShorturlAppService;
import com.co.kc.shortening.application.service.query.ShorturlQueryService;
import com.co.kc.shortening.common.utils.DateUtils;
import com.co.kc.shortening.common.utils.JsonUtils;
import com.co.kc.shortening.shorturl.domain.model.ShorturlFactory;
import com.co.kc.shortening.user.domain.model.UserFactory;
import com.co.kc.shortening.web.common.Result;
import com.co.kc.shortening.web.common.constants.ResultCode;
import com.co.kc.shortening.web.common.constants.enums.BaseEnum;
import com.co.kc.shortening.web.common.constants.enums.ShorturlFacadeStatus;
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
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShorturlController.class)
@Import({MethodSecurityConfig.class, WebSecurityConfig.class})
@ContextConfiguration(classes = ShortUrlAdminTestApplication.class)
class ShorturlControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenClient tokenClient;
    @MockBean
    private SessionClient sessionClient;
    @MockBean
    private ShorturlAppService shorturlAppService;
    @MockBean
    private ShorturlQueryService shorturlQueryService;

    private MockHttpServletRequestBuilder shoturlListRequest;
    private MockHttpServletRequestBuilder addShorturlRequest;
    private MockHttpServletRequestBuilder updateShorturlRequest;

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

        shoturlListRequest =
                get("/shorturl/v1/shorturlList")
                        .header(ParamsConstants.TOKEN, UserFactory.testUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
        addShorturlRequest =
                post("/shorturl/v1/addShorturl")
                        .header(ParamsConstants.TOKEN, UserFactory.testUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
        updateShorturlRequest =
                post("/shorturl/v1/updateShorturl")
                        .header(ParamsConstants.TOKEN, UserFactory.testUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
    }

    @Test
    void testShorturlListByShortId() throws Exception {
        mockOnlyTestShorturlInShorturlList();

        MockHttpServletRequestBuilder httpGet =
                shoturlListRequest
                        .param("shortId", String.valueOf(ShorturlFactory.testShortId))
                        .param("code", "")
                        .param("status", "")
                        .param("validTimeStart", "")
                        .param("validTimeEnd", "")
                        .param("pageNo", "1")
                        .param("pageSize", "10");
        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(this::shouldOnlyContainTestShorturlInShorturlList);

        ArgumentCaptor<ShorturlQuery> argumentCaptor = ArgumentCaptor.forClass(ShorturlQuery.class);
        verify(shorturlQueryService).queryShorturl(argumentCaptor.capture());
        ShorturlQuery shorturlQuery = argumentCaptor.getValue();
        Assertions.assertEquals(ShorturlFactory.testShortId, shorturlQuery.getShortId());
        Assertions.assertEquals("", shorturlQuery.getCode());
        Assertions.assertNull(shorturlQuery.getStatus());
        Assertions.assertNull(shorturlQuery.getValidTimeStart());
        Assertions.assertNull(shorturlQuery.getValidTimeEnd());
        Assertions.assertEquals(1L, shorturlQuery.getPageNo().longValue());
        Assertions.assertEquals(10L, shorturlQuery.getPageSize().longValue());
    }

    @Test
    void testShorturlListByShortCode() throws Exception {
        mockOnlyTestShorturlInShorturlList();

        MockHttpServletRequestBuilder httpGet =
                shoturlListRequest
                        .param("shortId", "")
                        .param("code", ShorturlFactory.testShortCode)
                        .param("status", "")
                        .param("validTimeStart", "")
                        .param("validTimeEnd", "")
                        .param("pageNo", "1")
                        .param("pageSize", "10");

        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(this::shouldOnlyContainTestShorturlInShorturlList);

        ArgumentCaptor<ShorturlQuery> argumentCaptor = ArgumentCaptor.forClass(ShorturlQuery.class);
        verify(shorturlQueryService).queryShorturl(argumentCaptor.capture());
        ShorturlQuery shorturlQuery = argumentCaptor.getValue();
        Assertions.assertNull(shorturlQuery.getShortId());
        Assertions.assertEquals(ShorturlFactory.testShortCode, shorturlQuery.getCode());
        Assertions.assertNull(shorturlQuery.getStatus());
        Assertions.assertNull(shorturlQuery.getValidTimeStart());
        Assertions.assertNull(shorturlQuery.getValidTimeEnd());
        Assertions.assertEquals(1L, shorturlQuery.getPageNo().longValue());
        Assertions.assertEquals(10L, shorturlQuery.getPageSize().longValue());
    }

    @Test
    void testShorturlListByStatus() throws Exception {
        mockOnlyTestShorturlInShorturlList();

        ShorturlFacadeStatus shorturlFacadeStatus = ShorturlFacadeStatus.convert(ShorturlFactory.testStatus).get();
        MockHttpServletRequestBuilder httpGet =
                shoturlListRequest
                        .param("shortId", "")
                        .param("code", "")
                        .param("status", String.valueOf(shorturlFacadeStatus.getCode()))
                        .param("validTimeStart", "")
                        .param("validTimeEnd", "")
                        .param("pageNo", "1")
                        .param("pageSize", "10");

        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(this::shouldOnlyContainTestShorturlInShorturlList);

        ArgumentCaptor<ShorturlQuery> argumentCaptor = ArgumentCaptor.forClass(ShorturlQuery.class);
        verify(shorturlQueryService).queryShorturl(argumentCaptor.capture());
        ShorturlQuery shorturlQuery = argumentCaptor.getValue();
        Assertions.assertNull(shorturlQuery.getShortId());
        Assertions.assertEquals("", shorturlQuery.getCode());
        Assertions.assertEquals(ShorturlFactory.testStatus, shorturlQuery.getStatus());
        Assertions.assertNull(shorturlQuery.getValidTimeStart());
        Assertions.assertNull(shorturlQuery.getValidTimeEnd());
        Assertions.assertEquals(1L, shorturlQuery.getPageNo().longValue());
        Assertions.assertEquals(10L, shorturlQuery.getPageSize().longValue());
    }

    @Test
    void testShorturlListByValidTime() throws Exception {
        mockOnlyTestShorturlInShorturlList();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateUtils.FORMAT_COMMON_DATETIME);
        MockHttpServletRequestBuilder httpGet =
                shoturlListRequest
                        .param("shortId", "")
                        .param("code", "")
                        .param("status", "")
                        .param("validTimeStart", ShorturlFactory.testValidTime.getStartTime().format(dateTimeFormatter))
                        .param("validTimeEnd", ShorturlFactory.testValidTime.getEndTime().format(dateTimeFormatter))
                        .param("pageNo", "1")
                        .param("pageSize", "10");

        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(this::shouldOnlyContainTestShorturlInShorturlList);
    }


    @Test
    void testAddShorturl() throws Exception {
        ShorturlAddDTO shorturlAddDTO = new ShorturlAddDTO(ShorturlFactory.testShortId, ShorturlFactory.testShortCode, ShorturlFactory.testShortLink);
        doReturn(shorturlAddDTO).when(shorturlAppService).add(any(ShorturlAddCommand.class));

        ShorturlAddRequest body = new ShorturlAddRequest();
        body.setRawLink(ShorturlFactory.testRawLink);
        body.setStatus(ShorturlFacadeStatus.convert(ShorturlFactory.testStatus).get());
        body.setValidTimeStart(ShorturlFactory.testValidTime.getStartTime());
        body.setValidTimeEnd(ShorturlFactory.testValidTime.getEndTime());
        MockHttpServletRequestBuilder httpPost = addShorturlRequest.content(JsonUtils.toJson(body));
        mockMvc.perform(httpPost)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<ShorturlAddVO> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<ShorturlAddVO>>() {
                    });
                    Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
                    Assertions.assertEquals(ShorturlFactory.testShortLink, result.getData().getShorturl());
                });

        ArgumentCaptor<ShorturlAddCommand> argumentCaptor = ArgumentCaptor.forClass(ShorturlAddCommand.class);
        verify(shorturlAppService).add(argumentCaptor.capture());
        ShorturlAddCommand addCommand = argumentCaptor.getValue();
        Assertions.assertEquals(ShorturlFactory.testRawLink, addCommand.getRawLink());
        Assertions.assertEquals(ShorturlFactory.testStatus, addCommand.getStatus());
        Assertions.assertTrue(DateUtils.equals(ShorturlFactory.testValidTime.getStartTime(), addCommand.getValidTimeStart()));
        Assertions.assertTrue(DateUtils.equals(ShorturlFactory.testValidTime.getEndTime(), addCommand.getValidTimeEnd()));
    }

    @Test
    void testUpdateShorturl() throws Exception {
        doNothing().when(shorturlAppService).update(any(ShorturlUpdateCommand.class));

        ShorturlUpdateRequest body = new ShorturlUpdateRequest();
        body.setShortId(ShorturlFactory.testShortId);
        body.setStatus(ShorturlFacadeStatus.convert(ShorturlFactory.testStatus).get());
        body.setValidTimeStart(ShorturlFactory.testValidTime.getStartTime());
        body.setValidTimeEnd(ShorturlFactory.testValidTime.getEndTime());
        MockHttpServletRequestBuilder httpPost = updateShorturlRequest.content(JsonUtils.toJson(body));
        mockMvc.perform(httpPost)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<ShorturlAddVO> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<ShorturlAddVO>>() {
                    });
                    Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
                });

        ArgumentCaptor<ShorturlUpdateCommand> argumentCaptor = ArgumentCaptor.forClass(ShorturlUpdateCommand.class);
        verify(shorturlAppService).update(argumentCaptor.capture());
        ShorturlUpdateCommand updateCommand = argumentCaptor.getValue();
        Assertions.assertEquals(ShorturlFactory.testShortId, updateCommand.getShortId());
        Assertions.assertEquals(ShorturlFactory.testStatus, updateCommand.getStatus());
        Assertions.assertTrue(DateUtils.equals(ShorturlFactory.testValidTime.getStartTime(), updateCommand.getValidTimeStart()));
        Assertions.assertTrue(DateUtils.equals(ShorturlFactory.testValidTime.getEndTime(), updateCommand.getValidTimeEnd()));
    }

    private void mockOnlyTestShorturlInShorturlList() {
        doAnswer((invocation) -> {
            ShorturlQuery invocationParams = invocation.getArgument(0);
            if (ShorturlFactory.testShortId.equals(invocationParams.getShortId()) ||
                    ShorturlFactory.testShortCode.equals(invocationParams.getCode()) ||
                    ShorturlFactory.testStatus.equals(invocationParams.getStatus()) ||
                    (DateUtils.equals(ShorturlFactory.testValidTime.getStartTime(), invocationParams.getValidTimeStart())
                            && DateUtils.equals(ShorturlFactory.testValidTime.getEndTime(), invocationParams.getValidTimeEnd()))) {
                ShorturlQueryDTO shorturlQueryDTO = new ShorturlQueryDTO();
                shorturlQueryDTO.setShortId(ShorturlFactory.testShortId);
                shorturlQueryDTO.setCode(ShorturlFactory.testShortCode);
                shorturlQueryDTO.setRawLink(ShorturlFactory.testRawLink);
                shorturlQueryDTO.setShortLink(ShorturlFactory.testShortLink);
                shorturlQueryDTO.setStatus(ShorturlFactory.testStatus);
                shorturlQueryDTO.setValidStart(ShorturlFactory.testValidTime.getStartTime());
                shorturlQueryDTO.setValidEnd(ShorturlFactory.testValidTime.getEndTime());
                return PagingResult.<ShorturlQueryDTO>newBuilder()
                        .paging(invocationParams.getPaging())
                        .records(Collections.singletonList(shorturlQueryDTO))
                        .totalCount(1L)
                        .build();
            }
            return PagingResult.newBuilder()
                    .paging(invocationParams.getPaging())
                    .records(Collections.emptyList())
                    .totalCount(0L)
                    .build();
        }).when(shorturlQueryService).queryShorturl(any(ShorturlQuery.class));
    }

    private void shouldOnlyContainTestShorturlInShorturlList(MvcResult mvcResult) throws UnsupportedEncodingException {
        String resultJson = mvcResult.getResponse().getContentAsString();
        Result<PagingResult<ShorturlListVO>> result = JsonUtils.fromJson(resultJson, new TypeReference<Result<PagingResult<ShorturlListVO>>>() {
        });
        Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
        Assertions.assertEquals(1L, result.getData().getTotalPages().longValue());
        Assertions.assertEquals(1L, result.getData().getTotalCount().longValue());
        Assertions.assertEquals(1L, result.getData().getPaging().getPageNo().longValue());
        Assertions.assertEquals(10L, result.getData().getPaging().getPageSize().longValue());
        Assertions.assertEquals(1L, result.getData().getRecords().size());

        ShorturlListVO shorturlListVO = result.getData().getRecords().get(0);
        Assertions.assertEquals(ShorturlFactory.testShortId, shorturlListVO.getShortId());
        Assertions.assertEquals(ShorturlFactory.testRawLink, shorturlListVO.getRawLink());
        Assertions.assertEquals(ShorturlFactory.testShortLink, shorturlListVO.getShortLink());
        Assertions.assertEquals(ShorturlFacadeStatus.convert(ShorturlFactory.testStatus).get(), shorturlListVO.getStatus());
        Assertions.assertTrue(DateUtils.equals(ShorturlFactory.testValidTime.getStartTime(), shorturlListVO.getValidTimeStart()));
        Assertions.assertTrue(DateUtils.equals(ShorturlFactory.testValidTime.getEndTime(), shorturlListVO.getValidTimeEnd()));
    }
}
