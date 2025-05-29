package com.co.kc.shortening.application.client;

import com.co.kc.shortening.application.model.client.TokenDTO;

/**
 * @author kc
 */
public interface TokenClient {
    /**
     * 生成TOKEN
     *
     * @param tokenDTO 生成TOKEN的DTO
     * @return 返回Token
     */
    String create(TokenDTO tokenDTO);

    /**
     * 解析TOKEN
     *
     * @param token 生成的TOKEN
     * @return 返回TOKEN信息
     */
    TokenDTO parse(String token);
}
