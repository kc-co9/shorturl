package com.co.kc.shortening.blocklist.service;

import com.co.kc.shortening.blocklist.domain.model.*;
import com.co.kc.shortening.shared.domain.model.Link;
import com.co.kc.shortening.common.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class BlocklistServiceTests {
    private BlocklistService blocklistService;

    @BeforeEach
    public void initRepository() {
        BlocklistRepository blocklistRepository = new BlocklistMemoryRepository();

        Blocklist activeBlocklist =
                new Blocklist(new BlockId(10L),
                        new Link("http://www.active.com"),
                        new BlockRemark("activeBlockLink"),
                        BlockStatus.ONLINE);
        blocklistRepository.save(activeBlocklist);

        Blocklist inactiveBlocklist =
                new Blocklist(
                        new BlockId(11L),
                        new Link("http://www.inactive.com"),
                        new BlockRemark("inactiveBlockLink"),
                        BlockStatus.OFFLINE);
        blocklistRepository.save(inactiveBlocklist);

        this.blocklistService = new BlocklistService(blocklistRepository);
    }

    @Test
    void testValidateThrowExWhenLinkIsBlocked() {
        Link link = new Link("http://www.active.com");
        BusinessException ex = Assertions.assertThrows(BusinessException.class, () -> blocklistService.validate(link));
        Assertions.assertEquals("短链已被禁用", ex.getMsg());
    }

    @Test
    void testValidatePassWhenLinkIsBlockedButInactive() {
        blocklistService.validate(new Link("http://www.inactive.com"));
    }

    @Test
    void testValidatePassWhenLinkIsNotBlocked() {
        blocklistService.validate(new Link("http://www.pass.com"));
    }
}
