package com.co.kc.shortening.web.controller;

import com.co.kc.shortening.application.service.appservice.ShorturlAppService;
import com.co.kc.shortening.common.utils.JsonUtils;
import com.co.kc.shortening.shorturl.domain.model.ShorturlFactory;
import com.co.kc.shortening.web.common.Result;
import com.co.kc.shortening.web.common.constants.ResultCode;
import com.co.kc.shortening.web.model.vo.UrlPreviewVO;
import com.co.kc.shortening.web.starter.ShortUrlWebTestApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UrlController.class)  // 仅加载控制器和相关Bean
@ContextConfiguration(classes = ShortUrlWebTestApplication.class)
public class UrlControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShorturlAppService shorturlAppService;

    @Test
    public void testPreview() throws Exception {
        when(shorturlAppService.redirect(ShorturlFactory.testShortCode)).thenReturn(ShorturlFactory.testRawLink);

        MockHttpServletRequestBuilder httpGet =
                get("/preview/{code}", ShorturlFactory.testShortCode)
                        .contentType(MediaType.APPLICATION_JSON)  // 设置请求内容类型
                        .characterEncoding("UTF-8"); // 设置请求字符编码

        mockMvc.perform(httpGet)
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Result<UrlPreviewVO> result =
                            JsonUtils.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Result<UrlPreviewVO>>() {
                            });
                    Assert.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
                    Assert.assertNotNull(result.getData());
                    Assert.assertNotNull(result.getData().getUrl());
                    Assert.assertEquals(ShorturlFactory.testRawLink, result.getData().getUrl());
                })
                .andDo(print());
    }

    @Test
    public void testRedirect() throws Exception {
        when(shorturlAppService.redirect(ShorturlFactory.testShortCode)).thenReturn(ShorturlFactory.testRawLink);

        MockHttpServletRequestBuilder httpGet =
                get("/{code}", ShorturlFactory.testShortCode)
                        .contentType(MediaType.APPLICATION_JSON)  // 设置请求内容类型
                        .characterEncoding("UTF-8"); // 设置请求字符编码

        mockMvc.perform(httpGet)
                .andExpect(status().is3xxRedirection())
                .andExpect(mvcResult -> {
                    Assert.assertEquals(ShorturlFactory.testRawLink, mvcResult.getResponse().getHeader("Location"));
                })
                .andDo(print());
    }

}
