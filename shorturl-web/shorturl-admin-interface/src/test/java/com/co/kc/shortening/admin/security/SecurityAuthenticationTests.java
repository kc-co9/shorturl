package com.co.kc.shortening.admin.security;

import com.co.kc.shortening.admin.controller.AccountController;
import com.co.kc.shortening.admin.mock.SecurityMock;
import com.co.kc.shortening.admin.model.request.AdministratorSignInRequest;
import com.co.kc.shortening.web.common.constants.ParamsConstants;
import com.co.kc.shortening.admin.starter.ShortUrlAdminTestApplication;
import com.co.kc.shortening.application.client.SessionClient;
import com.co.kc.shortening.application.client.TokenClient;
import com.co.kc.shortening.application.model.cqrs.command.user.SignInCommand;
import com.co.kc.shortening.application.model.cqrs.dto.SignInDTO;
import com.co.kc.shortening.application.model.cqrs.dto.UserDetailDTO;
import com.co.kc.shortening.application.model.cqrs.query.UserDetailQuery;
import com.co.kc.shortening.application.service.app.UserAppService;
import com.co.kc.shortening.common.constant.ErrorCode;
import com.co.kc.shortening.common.utils.JsonUtils;
import com.co.kc.shortening.user.domain.model.UserFactory;
import com.co.kc.shortening.web.common.Result;
import com.co.kc.shortening.web.common.constants.ResultCode;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@Import({MethodSecurityConfig.class, WebSecurityConfig.class})
@ContextConfiguration(classes = ShortUrlAdminTestApplication.class)
class SecurityAuthenticationTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenClient tokenClient;
    @MockBean
    private SessionClient sessionClient;
    @MockBean
    private UserAppService userAppService;

    private MockHttpServletRequestBuilder signInRequest;
    private MockHttpServletRequestBuilder accountDetailRequest;

    @BeforeEach
    public void initMockRequestBuilder() {
        signInRequest =
                post("/account/v1/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
        accountDetailRequest =
                get("/account/v1/accountDetail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8.name());
    }

    @Test
    void testSignInWhenUserIsNotAuthenticated() throws Exception {
        SignInDTO signInDTO = new SignInDTO(UserFactory.testUserId, UserFactory.testUserToken);
        doReturn(signInDTO).when(userAppService).signIn(any(SignInCommand.class));
        AdministratorSignInRequest body = new AdministratorSignInRequest(UserFactory.testUserEmail, UserFactory.testUserRawPassword);
        MockHttpServletRequestBuilder httpPost = signInRequest.content(JsonUtils.toJson(body));
        mockMvc.perform(httpPost)
                .andExpect(status().isOk())
                .andExpect(this::success);
    }

    @Test
    void testAccountDetailWhenUserHasAuthenticated() throws Exception {
        SecurityMock.mockTestUserHasAuthenticated(tokenClient, sessionClient);

        UserDetailDTO userDetailDTO = new UserDetailDTO(UserFactory.testUserId, UserFactory.testUserEmail, UserFactory.testUserName);
        doReturn(userDetailDTO).when(userAppService).userDetail(any(UserDetailQuery.class));

        mockMvc.perform(accountDetailRequest.header(ParamsConstants.TOKEN, UserFactory.testUserToken))
                .andExpect(status().isOk())
                .andExpect(this::success);
    }

    @Test
    void testAccountDetailWhenUserIsNotAuthenticated() throws Exception {
        SecurityMock.mockTestUserHasAuthenticated(tokenClient, sessionClient);
        mockMvc.perform(accountDetailRequest)
                .andExpect(status().isOk())
                .andExpect(this::isNotAuthenticated);
    }

    @Test
    void testAccountDetailWhenUserAuthenticationHasExpired() throws Exception {
        SecurityMock.mockTestUserAuthenticationHasExpired(tokenClient, sessionClient);
        mockMvc.perform(accountDetailRequest.header(ParamsConstants.TOKEN, UserFactory.testUserToken))
                .andExpect(status().isOk())
                .andExpect(this::isNotAuthenticated);
    }

    private void success(MvcResult mvcResult) throws UnsupportedEncodingException {
        Assertions.assertEquals(ResultCode.SUCCESS.getCode(), parseResultCode(mvcResult));
    }

    private void isNotAuthenticated(MvcResult mvcResult) throws UnsupportedEncodingException {
        Assertions.assertEquals(ErrorCode.AUTH_FAIL.getCode(), parseResultCode(mvcResult));
    }

    private Integer parseResultCode(MvcResult mvcResult) throws UnsupportedEncodingException {
        Result<?> result = JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<?>>() {
        });
        return result.getCode();
    }

}
