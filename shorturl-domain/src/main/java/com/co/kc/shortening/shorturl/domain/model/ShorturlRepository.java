package com.co.kc.shortening.shorturl.domain.model;

/**
 * 短链资源库
 *
 * @author kc
 */
public interface ShorturlRepository {
    /**
     * 根据短链ID查询shorturl
     *
     * @param shortId 短链ID
     * @return Shorturl-聚合根
     */
    Shorturl find(ShortId shortId);

    /**
     * 根据shortCode查询shorturl
     *
     * @param code 短链CODE
     * @return Shorturl-聚合根
     */
    Shorturl find(ShortCode code);

    /**
     * 保存shorturl
     *
     * @param shorturl 聚合根
     */
    void save(Shorturl shorturl);

}



