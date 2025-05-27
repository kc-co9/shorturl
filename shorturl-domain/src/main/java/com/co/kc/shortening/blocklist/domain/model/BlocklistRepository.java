package com.co.kc.shortening.blocklist.domain.model;

import com.co.kc.shortening.shared.domain.model.Link;

/**
 * 黑名单资源库
 *
 * @author kc
 */
public interface BlocklistRepository {
    /**
     * 根据blockId查找黑名单
     *
     * @param blockId 黑名单ID
     * @return 黑名单
     */
    Blocklist find(BlockId blockId);

    /**
     * 根据链接查找黑名单
     *
     * @param link 链接
     * @return 黑名单
     */
    Blocklist find(Link link);

    /**
     * 保存黑名单
     *
     * @param blocklist 黑名单
     */
    void save(Blocklist blocklist);

    /**
     * 移除黑名单
     *
     * @param blocklist 黑名单
     */
    void remove(Blocklist blocklist);
}
