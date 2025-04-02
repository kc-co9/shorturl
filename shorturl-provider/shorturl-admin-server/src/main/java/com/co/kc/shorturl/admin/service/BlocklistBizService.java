package com.co.kc.shorturl.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.co.kc.shorturl.admin.model.dto.response.BlocklistDTO;
import com.co.kc.shorturl.common.model.io.Paging;
import com.co.kc.shorturl.common.model.io.PagingResult;
import com.co.kc.shorturl.common.utils.FunctionUtils;
import com.co.kc.shorturl.common.utils.HashUtils;
import com.co.kc.shorturl.repository.dao.UrlBlocklistRepository;
import com.co.kc.shorturl.repository.po.entity.UrlBlocklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kc
 */
@Service
public class BlocklistBizService {

    @Autowired
    private UrlBlocklistRepository urlBlocklistRepository;

    public PagingResult<BlocklistDTO> getBlocklistList(Paging paging) {
        IPage<UrlBlocklist> blocklistPage =
                urlBlocklistRepository.page(new Page<>(paging.getPageNo(), paging.getPageSize()),
                        urlBlocklistRepository.getQueryWrapper().orderByDesc(UrlBlocklist::getId));
        List<BlocklistDTO> blocklistList = FunctionUtils.mappingList(blocklistPage.getRecords(), blocklist -> {
            BlocklistDTO shorturlDTO = new BlocklistDTO();
            shorturlDTO.setId(blocklist.getId());
            shorturlDTO.setUrl(blocklist.getUrl());
            shorturlDTO.setRemark(blocklist.getRemark());
            shorturlDTO.setCreateTime(blocklist.getCreateTime());
            return shorturlDTO;
        });
        return PagingResult.<BlocklistDTO>newBuilder()
                .paging(paging)
                .records(blocklistList)
                .totalCount(blocklistPage.getTotal())
                .totalPages(blocklistPage.getPages())
                .build();
    }

    public void addBlocklist(String url, String remark) {
        String hash = HashUtils.murmurHash32(url);
        UrlBlocklist blocklist = urlBlocklistRepository.findByHash(hash, url).orElse(null);
        if (blocklist != null) {
            return;
        }
        blocklist = new UrlBlocklist();
        blocklist.setUrl(url);
        blocklist.setHash(hash);
        blocklist.setRemark(remark);
        urlBlocklistRepository.save(blocklist);
    }

    public void updateBlocklist(Long id, String remark) {
        urlBlocklistRepository.update(urlBlocklistRepository.getUpdateWrapper()
                .set(UrlBlocklist::getRemark, remark)
                .eq(UrlBlocklist::getId, id));
    }

    public void removeBlocklist(Long id) {
        urlBlocklistRepository.removeById(id);
    }


}
