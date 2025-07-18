package com.co.kc.shortening.admin.controller;

import com.co.kc.shortening.admin.mock.SecurityMock;
import com.co.kc.shortening.admin.model.request.*;
import com.co.kc.shortening.admin.model.response.AdministratorDetailVO;
import com.co.kc.shortening.admin.model.response.AdministratorListVO;
import com.co.kc.shortening.admin.security.MethodSecurityConfig;
import com.co.kc.shortening.admin.security.WebSecurityConfig;
import com.co.kc.shortening.web.common.constants.ParamsConstants;
import com.co.kc.shortening.admin.starter.ShortUrlAdminTestApplication;
import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.client.TokenClient;
import com.co.kc.shortening.application.model.cqrs.command.user.UserAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.user.UserRemoveCommand;
import com.co.kc.shortening.application.model.cqrs.command.user.UserUpdateCommand;
import com.co.kc.shortening.application.model.cqrs.dto.UserAddDTO;
import com.co.kc.shortening.application.model.cqrs.dto.UserDetailDTO;
import com.co.kc.shortening.application.model.cqrs.dto.UserQueryDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserDetailQuery;
import com.co.kc.shortening.application.model.cqrs.query.UserQuery;
import com.co.kc.shortening.application.model.io.PagingResult;
import com.co.kc.shortening.application.service.app.UserAppService;
import com.co.kc.shortening.application.service.query.UserQueryService;
import com.co.kc.shortening.common.utils.JsonUtils;
import com.co.kc.shortening.user.domain.model.UserFactory;
import com.co.kc.shortening.web.common.Result;
import com.co.kc.shortening.web.common.constants.ResultCode;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
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

import java.time.LocalDateTime;
import java.util.Collections;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdministratorController.class)
@Import({MethodSecurityConfig.class, WebSecurityConfig.class})
@ContextConfiguration(classes = ShortUrlAdminTestApplication.class)
class AdministratorControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenClient tokenClient;
    @MockBean
    private SessionClient sessionClient;
    @MockBean
    private UserAppService userAppService;
    @MockBean
    private UserQueryService userQueryService;

    private MockHttpServletRequestBuilder administratorListRequest;
    private MockHttpServletRequestBuilder administratorDetailRequest;
    private MockHttpServletRequestBuilder addAdministratorRequest;
    private MockHttpServletRequestBuilder updateAdministratorRequest;
    private MockHttpServletRequestBuilder removeAdministratorRequest;

    @BeforeEach
    public void initMockRequestBuilder() {
        SecurityMock.mockTestUserHasAuthenticated(tokenClient, sessionClient);

        administratorListRequest =
                get("/administrator/v1/administratorList")
                        .header(ParamsConstants.TOKEN, UserFactory.testUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
        administratorDetailRequest =
                get("/administrator/v1/administratorDetail")
                        .header(ParamsConstants.TOKEN, UserFactory.testUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
        addAdministratorRequest =
                post("/administrator/v1/addAdministrator")
                        .header(ParamsConstants.TOKEN, UserFactory.testUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
        updateAdministratorRequest =
                post("/administrator/v1/updateAdministrator")
                        .header(ParamsConstants.TOKEN, UserFactory.testUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
        removeAdministratorRequest =
                post("/administrator/v1/removeAdministrator")
                        .header(ParamsConstants.TOKEN, UserFactory.testUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
    }

    @Test
    void testAdministratorListByEmail() throws Exception {
        mockOnlyTestUserInAdministratorList();

        MockHttpServletRequestBuilder httpGet =
                administratorListRequest
                        .param("email", UserFactory.testUserEmail)
                        .param("username", "")
                        .param("pageNo", "1")
                        .param("pageSize", "10");

        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(this::shouldOnlyContainTestUserInAdministratorList);

        ArgumentCaptor<UserQuery> argumentCaptor = ArgumentCaptor.forClass(UserQuery.class);
        verify(userQueryService).queryUser(argumentCaptor.capture());
        UserQuery userQuery = argumentCaptor.getValue();
        Assertions.assertEquals(UserFactory.testUserEmail, userQuery.getEmail());
        Assertions.assertEquals("", userQuery.getUsername());
        Assertions.assertEquals(1L, userQuery.getPageNo().longValue());
        Assertions.assertEquals(10L, userQuery.getPageSize().longValue());
    }

    @Test
    void testAdministratorListByUserName() throws Exception {
        mockOnlyTestUserInAdministratorList();

        MockHttpServletRequestBuilder httpGet =
                administratorListRequest
                        .param("email", "")
                        .param("username", UserFactory.testUserName)
                        .param("pageNo", "1")
                        .param("pageSize", "10");
        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(this::shouldOnlyContainTestUserInAdministratorList);

        ArgumentCaptor<UserQuery> argumentCaptor = ArgumentCaptor.forClass(UserQuery.class);
        verify(userQueryService).queryUser(argumentCaptor.capture());
        UserQuery userQuery = argumentCaptor.getValue();
        Assertions.assertEquals("", userQuery.getEmail());
        Assertions.assertEquals(UserFactory.testUserName, userQuery.getUsername());
        Assertions.assertEquals(1L, userQuery.getPageNo().longValue());
        Assertions.assertEquals(10L, userQuery.getPageSize().longValue());
    }

    @Test
    void testAdministratorListByUserEmailAndUserName() throws Exception {
        mockOnlyTestUserInAdministratorList();

        MockHttpServletRequestBuilder httpGet =
                administratorListRequest
                        .param("email", UserFactory.testUserEmail)
                        .param("username", UserFactory.testUserName)
                        .param("pageNo", "1")
                        .param("pageSize", "10");
        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(this::shouldOnlyContainTestUserInAdministratorList);

        ArgumentCaptor<UserQuery> argumentCaptor = ArgumentCaptor.forClass(UserQuery.class);
        verify(userQueryService).queryUser(argumentCaptor.capture());
        UserQuery userQuery = argumentCaptor.getValue();
        Assertions.assertEquals(UserFactory.testUserEmail, userQuery.getEmail());
        Assertions.assertEquals(UserFactory.testUserName, userQuery.getUsername());
        Assertions.assertEquals(1L, userQuery.getPageNo().longValue());
        Assertions.assertEquals(10L, userQuery.getPageSize().longValue());
    }

    @Test
    void testAdministratorListByUserWrongEmail() throws Exception {
        mockOnlyTestUserInAdministratorList();

        MockHttpServletRequestBuilder httpGet =
                administratorListRequest
                        .param("email", UserFactory.testUserWrongEmail)
                        .param("username", "")
                        .param("pageNo", "1")
                        .param("pageSize", "10");
        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(this::shouldNothingInAdministratorList);

        ArgumentCaptor<UserQuery> argumentCaptor = ArgumentCaptor.forClass(UserQuery.class);
        verify(userQueryService).queryUser(argumentCaptor.capture());
        UserQuery userQuery = argumentCaptor.getValue();
        Assertions.assertEquals(UserFactory.testUserWrongEmail, userQuery.getEmail());
        Assertions.assertEquals("", userQuery.getUsername());
        Assertions.assertEquals(1L, userQuery.getPageNo().longValue());
        Assertions.assertEquals(10L, userQuery.getPageSize().longValue());
    }

    @Test
    void testAdministratorDetail() throws Exception {
        UserDetailDTO userDetailDTO = new UserDetailDTO(UserFactory.testUserId, UserFactory.testUserEmail, UserFactory.testUserName);
        doReturn(userDetailDTO).when(userAppService).userDetail(any(UserDetailQuery.class));

        MockHttpServletRequestBuilder httpGet =
                administratorDetailRequest.param("administratorId", String.valueOf(UserFactory.testUserId));
        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<AdministratorDetailVO> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<AdministratorDetailVO>>() {
                    });
                    Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
                    Assertions.assertEquals(UserFactory.testUserId, result.getData().getAdministratorId());
                    Assertions.assertEquals(UserFactory.testUserName, result.getData().getAdministratorName());
                });

        ArgumentCaptor<UserDetailQuery> argumentCaptor = ArgumentCaptor.forClass(UserDetailQuery.class);
        verify(userAppService).userDetail(argumentCaptor.capture());
        UserDetailQuery userDetailQuery = argumentCaptor.getValue();
        Assertions.assertEquals(UserFactory.testUserId, userDetailQuery.getUserId());
    }

    @Test
    void testAddAdministrator() throws Exception {
        doReturn(new UserAddDTO(UserFactory.testUserId)).when(userAppService).addUser(any(UserAddCommand.class));

        AdministratorAddRequest request = new AdministratorAddRequest();
        request.setUsername(UserFactory.testUserName);
        request.setEmail(UserFactory.testUserEmail);
        request.setPassword(UserFactory.testUserRawPassword);

        MockHttpServletRequestBuilder httpPost = addAdministratorRequest.content(JsonUtils.toJson(request));
        mockMvc.perform(httpPost)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<?> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<?>>() {
                    });
                    Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
                });

        ArgumentCaptor<UserAddCommand> argumentCaptor = ArgumentCaptor.forClass(UserAddCommand.class);
        verify(userAppService).addUser(argumentCaptor.capture());
        UserAddCommand userAddCommand = argumentCaptor.getValue();
        Assertions.assertEquals(UserFactory.testUserEmail, userAddCommand.getEmail());
        Assertions.assertEquals(UserFactory.testUserName, userAddCommand.getUsername());
        Assertions.assertEquals(UserFactory.testUserRawPassword, userAddCommand.getPassword());
    }

    @Test
    void testUpdateAdministrator() throws Exception {
        doNothing().when(userAppService).updateUser(any(UserUpdateCommand.class));

        AdministratorUpdateRequest request = new AdministratorUpdateRequest();
        request.setAdministratorId(UserFactory.testUserId);
        request.setUsername(UserFactory.testUserName);
        request.setEmail(UserFactory.testUserEmail);
        request.setPassword(UserFactory.testUserRawPassword);

        MockHttpServletRequestBuilder httpPost = updateAdministratorRequest.content(JsonUtils.toJson(request));
        mockMvc.perform(httpPost)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<?> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<?>>() {
                    });
                    Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
                });

        ArgumentCaptor<UserUpdateCommand> argumentCaptor = ArgumentCaptor.forClass(UserUpdateCommand.class);
        verify(userAppService).updateUser(argumentCaptor.capture());
        UserUpdateCommand userUpdateCommand = argumentCaptor.getValue();
        Assertions.assertEquals(UserFactory.testUserId, userUpdateCommand.getUserId());
        Assertions.assertEquals(UserFactory.testUserEmail, userUpdateCommand.getEmail());
        Assertions.assertEquals(UserFactory.testUserName, userUpdateCommand.getUsername());
        Assertions.assertEquals(UserFactory.testUserRawPassword, userUpdateCommand.getPassword());
    }

    @Test
    public void removeAdministrator() throws Exception {
        doNothing().when(userAppService).removeUser(any(UserRemoveCommand.class));

        AdministratorRemoveRequest request = new AdministratorRemoveRequest();
        request.setAdministratorId(UserFactory.testUserId);

        MockHttpServletRequestBuilder httpGet = removeAdministratorRequest.content(JsonUtils.toJson(request));
        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<?> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<?>>() {
                    });
                    Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
                });

        ArgumentCaptor<UserRemoveCommand> argumentCaptor = ArgumentCaptor.forClass(UserRemoveCommand.class);
        verify(userAppService).removeUser(argumentCaptor.capture());
        UserRemoveCommand userRemoveCommand = argumentCaptor.getValue();
        Assertions.assertEquals(UserFactory.testUserId, userRemoveCommand.getUserId());
    }

    private void mockOnlyTestUserInAdministratorList() {
        doAnswer((invocation) -> {
            UserQuery invocationParams = invocation.getArgument(0);
            if (UserFactory.testUserEmail.equals(invocationParams.getEmail())
                    || UserFactory.testUserName.equals(invocationParams.getUsername())) {
                UserQueryDTO userQueryDTO = new UserQueryDTO();
                userQueryDTO.setUserId(UserFactory.testUserId);
                userQueryDTO.setEmail(UserFactory.testUserEmail);
                userQueryDTO.setUsername(UserFactory.testUserName);
                userQueryDTO.setCreateTime(LocalDateTime.now());
                return PagingResult.<UserQueryDTO>newBuilder()
                        .paging(invocationParams.getPaging())
                        .records(Collections.singletonList(userQueryDTO))
                        .totalCount(1L)
                        .build();
            }
            return PagingResult.newBuilder()
                    .paging(invocationParams.getPaging())
                    .records(Collections.emptyList())
                    .totalCount(0L)
                    .build();
        }).when(userQueryService).queryUser(any(UserQuery.class));
    }

    private void shouldOnlyContainTestUserInAdministratorList(MvcResult mvcResult) throws Exception {
        String resultJson = mvcResult.getResponse().getContentAsString();
        Result<PagingResult<AdministratorListVO>> result = JsonUtils.fromJson(resultJson, new TypeReference<Result<PagingResult<AdministratorListVO>>>() {
        });
        Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
        Assertions.assertEquals(1L, result.getData().getTotalPages().longValue());
        Assertions.assertEquals(1L, result.getData().getTotalCount().longValue());
        Assertions.assertEquals(1L, result.getData().getPaging().getPageNo().longValue());
        Assertions.assertEquals(10L, result.getData().getPaging().getPageSize().longValue());
        Assertions.assertEquals(1L, result.getData().getRecords().size());

        AdministratorListVO administratorListVO = result.getData().getRecords().get(0);
        Assertions.assertEquals(UserFactory.testUserId, administratorListVO.getAdministratorId());
        Assertions.assertEquals(UserFactory.testUserName, administratorListVO.getUsername());
        Assertions.assertEquals(UserFactory.testUserEmail, administratorListVO.getEmail());
    }

    private void shouldNothingInAdministratorList(MvcResult mvcResult) throws Exception {
        String resultJson = mvcResult.getResponse().getContentAsString();
        Result<PagingResult<AdministratorListVO>> result = JsonUtils.fromJson(resultJson, new TypeReference<Result<PagingResult<AdministratorListVO>>>() {
        });
        Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
        Assertions.assertEquals(0L, result.getData().getTotalPages().longValue());
        Assertions.assertEquals(0L, result.getData().getTotalCount().longValue());
        Assertions.assertEquals(1L, result.getData().getPaging().getPageNo().longValue());
        Assertions.assertEquals(10L, result.getData().getPaging().getPageSize().longValue());
        Assertions.assertEquals(0L, result.getData().getRecords().size());
    }
}
