package com.co.kc.shortening.admin.utils;

import org.springframework.test.web.servlet.MvcResult;

public class MockMvcUtils {
    /**
     * 用于异常时打印异常堆栈
     */
    public static void printExceptionStackTrace(MvcResult mvcResult) {
        if (mvcResult.getResolvedException() != null) {
            mvcResult.getResolvedException().printStackTrace(); // 打印堆栈
        }
    }
}
