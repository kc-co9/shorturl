package com.co.kc.shortening.admin.model.domain;

import com.co.kc.shortening.admin.utils.SecurityUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author kc
 */
@Data
public class SecurityAuth {

    private String userId;

    public SecurityAuth() {
    }

    public SecurityAuth(String userId) {
        this.userId = userId;
    }

    /**
     * 生成token
     *
     * @return 返回值
     */
    public String echoToken() {
        if (StringUtils.isBlank(this.getUserId())) {
            return "";
        }
        return SecurityUtils.echoToken(this);
    }

    public static class Builder {

        private Builder() {
        }

        /**
         * 解析token
         *
         * @param token 传入token
         * @return 返回解析数据
         */
        public static SecurityAuth parseToken(String token) {
            if (StringUtils.isBlank(token)) {
                return null;
            }
            return SecurityUtils.parseToken(token);
        }
    }

}
