package com.co.kc.shortening.web.common;

/**
 * 枚举基类
 *
 * @author kc
 */
public interface BaseEnum {
    /**
     * 获取枚举CODE
     *
     * @return 枚举CODE
     */
    Integer getCode();

    /**
     * 获取枚举描述
     *
     * @return 枚举描述
     */
    default String getDesc() {
        return String.valueOf(getCode());
    }
}
