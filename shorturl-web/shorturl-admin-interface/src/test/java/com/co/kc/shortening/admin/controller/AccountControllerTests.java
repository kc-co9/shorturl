package com.co.kc.shortening.admin.controller;

import com.co.kc.shortening.admin.mock.SecurityMock;
import com.co.kc.shortening.admin.model.request.AdministratorSignInRequest;
import com.co.kc.shortening.admin.model.response.AdministratorDetailVO;
import com.co.kc.shortening.admin.model.response.AdministratorSignInVO;
import com.co.kc.shortening.admin.security.MethodSecurityConfig;
import com.co.kc.shortening.admin.security.WebSecurityConfig;
import com.co.kc.shortening.admin.security.constant.ParamsConstants;
import com.co.kc.shortening.admin.starter.ShortUrlAdminTestApplication;
import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.client.TokenClient;
import com.co.kc.shortening.application.model.cqrs.command.user.SignInCommand;
import com.co.kc.shortening.application.model.cqrs.command.user.SignOutCommand;
import com.co.kc.shortening.application.model.cqrs.dto.SignInDTO;
import com.co.kc.shortening.application.model.cqrs.dto.UserDetailDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserDetailQuery;
import com.co.kc.shortening.application.service.app.UserAppService;
import com.co.kc.shortening.common.exception.AuthException;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@Import({MethodSecurityConfig.class, WebSecurityConfig.class})
@ContextConfiguration(classes = ShortUrlAdminTestApplication.class)
class AccountControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenClient tokenClient;
    @MockBean
    private SessionClient sessionClient;
    @MockBean
    private UserAppService userAppService;

    private MockHttpServletRequestBuilder signInRequest;
    private MockHttpServletRequestBuilder signOutRequest;
    private MockHttpServletRequestBuilder accountDetailRequest;

    @BeforeEach
    public void initMockRequestBuilder() {
        signInRequest = post("/account/v1/signIn").contentType(MediaType.APPLICATION_JSON).characterEncoding(UTF_8.name());
        signOutRequest = post("/account/v1/signOut").contentType(MediaType.APPLICATION_JSON).characterEncoding(UTF_8.name());
        accountDetailRequest = get("/account/v1/accountDetail").contentType(MediaType.APPLICATION_JSON).characterEncoding(UTF_8.name());
    }

    @Test
    void testSignInWhenAccountIsCorrect() throws Exception {
        SignInDTO signInDTO = new SignInDTO(UserFactory.testUserId, UserFactory.testUserToken);
        doReturn(signInDTO).when(userAppService).signIn(any(SignInCommand.class));

        AdministratorSignInRequest body = new AdministratorSignInRequest(UserFactory.testUserEmail, UserFactory.testUserRawPassword);
        MockHttpServletRequestBuilder httpPost = signInRequest.content(JsonUtils.toJson(body));
        mockMvc.perform(httpPost)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<AdministratorSignInVO> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<AdministratorSignInVO>>() {
                    });
                    Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
                    Assertions.assertEquals(UserFactory.testUserToken, result.getData().getAuthToken());
                });

        ArgumentCaptor<SignInCommand> argumentCaptor = ArgumentCaptor.forClass(SignInCommand.class);
        verify(userAppService).signIn(argumentCaptor.capture());
        SignInCommand signInCommand = argumentCaptor.getValue();
        Assertions.assertEquals(UserFactory.testUserEmail, signInCommand.getEmail());
        Assertions.assertEquals(UserFactory.testUserRawPassword, signInCommand.getPassword());
    }

    @Test
    void testSignInWhenEmailIsWrong() throws Exception {
        doThrow(new AuthException("user is not exist")).when(userAppService).signIn(any(SignInCommand.class));


        AdministratorSignInRequest body = new AdministratorSignInRequest(UserFactory.testUserWrongEmail, UserFactory.testUserRawPassword);
        MockHttpServletRequestBuilder httpPost = signInRequest.content(JsonUtils.toJson(body));
        mockMvc.perform(httpPost)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<AdministratorSignInVO> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<AdministratorSignInVO>>() {
                    });
                    Assertions.assertEquals(ResultCode.AUTH_FAIL.getCode(), result.getCode());
                    Assertions.assertEquals("user is not exist", result.getMsg());
                });

        ArgumentCaptor<SignInCommand> argumentCaptor = ArgumentCaptor.forClass(SignInCommand.class);
        verify(userAppService).signIn(argumentCaptor.capture());
        SignInCommand signInCommand = argumentCaptor.getValue();
        Assertions.assertEquals(UserFactory.testUserWrongEmail, signInCommand.getEmail());
        Assertions.assertEquals(UserFactory.testUserRawPassword, signInCommand.getPassword());
    }

    @Test
    void testSignInWhenPasswordIsWrong() throws Exception {
        doThrow(new AuthException("user is failed to authenticate")).when(userAppService).signIn(any(SignInCommand.class));

        AdministratorSignInRequest body = new AdministratorSignInRequest(UserFactory.testUserEmail, UserFactory.testUserWrongRawPassword);
        MockHttpServletRequestBuilder httpPost = signInRequest.content(JsonUtils.toJson(body));
        mockMvc.perform(httpPost)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<AdministratorSignInVO> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<AdministratorSignInVO>>() {
                    });
                    Assertions.assertEquals(ResultCode.AUTH_FAIL.getCode(), result.getCode());
                    Assertions.assertEquals("user is failed to authenticate", result.getMsg());
                });

        ArgumentCaptor<SignInCommand> argumentCaptor = ArgumentCaptor.forClass(SignInCommand.class);
        verify(userAppService).signIn(argumentCaptor.capture());
        SignInCommand signInCommand = argumentCaptor.getValue();
        Assertions.assertEquals(UserFactory.testUserEmail, signInCommand.getEmail());
        Assertions.assertEquals(UserFactory.testUserWrongRawPassword, signInCommand.getPassword());
    }

    @Test
    void testSignOut() throws Exception {
        SecurityMock.mockTestUserHasAuthenticated(tokenClient, sessionClient);

        doNothing().when(userAppService).signOut(any(SignOutCommand.class));

        MockHttpServletRequestBuilder httpPost = signOutRequest.header(ParamsConstants.TOKEN, UserFactory.testUserToken);
        mockMvc.perform(httpPost)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<?> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<?>>() {
                    });
                    Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
                });

        ArgumentCaptor<SignOutCommand> argumentCaptor = ArgumentCaptor.forClass(SignOutCommand.class);
        verify(userAppService).signOut(argumentCaptor.capture());
        SignOutCommand signOutCommand = argumentCaptor.getValue();
        Assertions.assertEquals(UserFactory.testUserId, signOutCommand.getUserId());
    }

    @Test
    void testAccountDetail() throws Exception {
        SecurityMock.mockTestUserHasAuthenticated(tokenClient, sessionClient);

        UserDetailDTO userDetailDTO = new UserDetailDTO(UserFactory.testUserId, UserFactory.testUserEmail, UserFactory.testUserName);
        doReturn(userDetailDTO).when(userAppService).userDetail(any(UserDetailQuery.class));

        MockHttpServletRequestBuilder httpGet = accountDetailRequest.header(ParamsConstants.TOKEN, UserFactory.testUserToken);
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
}
