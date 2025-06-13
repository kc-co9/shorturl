package com.co.kc.shortening.infrastructure.service.app;

import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlUpdateCommand;
import com.co.kc.shortening.application.model.cqrs.dto.ShorturlAddDTO;
import com.co.kc.shortening.application.service.app.ShorturlAppService;
import com.co.kc.shortening.common.exception.BusinessException;
import com.co.kc.shortening.infrastructure.extension.InfrastructureExtension;
import com.co.kc.shortening.infrastructure.starter.ShortUrlInfrastructureTestApplication;
import com.co.kc.shortening.shorturl.domain.model.ShorturlFactory;
import com.co.kc.shortening.shorturl.domain.model.ShorturlStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Rollback
@Transactional
@ExtendWith(InfrastructureExtension.class)
@SpringBootTest(classes = ShortUrlInfrastructureTestApplication.class)
class ShorturlAppServiceTests {
    @Autowired
    private ShorturlAppService shorturlAppService;

    @Test
    void testSucceedToRedirectAddedShorturl() {
        ShorturlAddDTO shorturlAddDTO = addValidShortLink();
        Assertions.assertNotNull(shorturlAddDTO);
        Assertions.assertNotNull(shorturlAddDTO.getShortId());
        Assertions.assertNotNull(shorturlAddDTO.getShortCode());
        Assertions.assertNotNull(shorturlAddDTO.getShorturl());

        String rawLink = shorturlAppService.redirect(shorturlAddDTO.getShortCode());
        Assertions.assertEquals(ShorturlFactory.testRawLink, rawLink);
    }

    @Test
    void testFailedToRedirectBecauseInvalidAfterUpdating() {
        ShorturlAddDTO shorturlAddDTO = addValidShortLink();

        ShorturlUpdateCommand updateCommand = new ShorturlUpdateCommand();
        updateCommand.setShortId(shorturlAddDTO.getShortId());
        updateCommand.setStatus(ShorturlStatus.OFFLINE);
        updateCommand.setValidTimeStart(LocalDateTime.now().minusDays(10));
        updateCommand.setValidTimeEnd(LocalDateTime.now().plusDays(10));
        shorturlAppService.update(updateCommand);

        String shortCode = shorturlAddDTO.getShortCode();
        Assertions.assertThrows(BusinessException.class, () -> shorturlAppService.redirect(shortCode));
    }

    @Test
    void testFailedToRedirectBecauseExpiredAfterUpdating() {
        ShorturlAddDTO shorturlAddDTO = addValidShortLink();

        ShorturlUpdateCommand updateCommand = new ShorturlUpdateCommand();
        updateCommand.setShortId(shorturlAddDTO.getShortId());
        updateCommand.setStatus(ShorturlStatus.ONLINE);
        updateCommand.setValidTimeStart(LocalDateTime.now().minusDays(10));
        updateCommand.setValidTimeEnd(LocalDateTime.now().minusDays(8));
        shorturlAppService.update(updateCommand);

        String shortCode = shorturlAddDTO.getShortCode();
        Assertions.assertThrows(BusinessException.class, () -> shorturlAppService.redirect(shortCode));
    }

    private ShorturlAddDTO addValidShortLink() {
        ShorturlAddCommand addCommand = new ShorturlAddCommand();
        addCommand.setRawLink(ShorturlFactory.testRawLink);
        addCommand.setStatus(ShorturlStatus.ONLINE);
        addCommand.setValidTimeStart(LocalDateTime.now().minusDays(10));
        addCommand.setValidTimeEnd(LocalDateTime.now().plusDays(10));
        return shorturlAppService.add(addCommand);
    }
}
