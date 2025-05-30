package com.co.kc.shortening.blocklist.service;

import com.co.kc.shortening.blocklist.domain.model.*;
import com.co.kc.shortening.shared.domain.model.Link;
import com.co.kc.shortening.common.exception.BusinessException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kc
 */
public class BlocklistServiceTests {
    private BlocklistService blocklistService;

    @Before
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
    public void testValidateThrowExWhenLinkIsBlocked() {
        try {
            blocklistService.validate(new Link("http://www.active.com"));
        } catch (BusinessException ex) {
            Assert.assertEquals("短链已被禁用", ex.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testValidatePassWhenLinkIsBlockedButInactive() {
        try {
            blocklistService.validate(new Link("http://www.inactive.com"));
        } catch (BusinessException ex) {
            Assert.fail();
        }
    }

    @Test
    public void testValidatePassWhenLinkIsNotBlocked() {
        try {
            blocklistService.validate(new Link("http://www.pass.com"));
        } catch (BusinessException ex) {
            Assert.fail();
        }
    }
}
