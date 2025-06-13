package com.co.kc.shortening.application.service.app;

import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlUpdateCommand;
import com.co.kc.shortening.application.model.cqrs.dto.ShorturlAddDTO;
import com.co.kc.shortening.shorturl.domain.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShorturlAppServiceTests {
    private ShorturlRepository shorturlRepository;
    private ShorturlAppService shorturlAppService;

    @BeforeEach
    public void initShorturlAppService() {
        shorturlRepository = new ShorturlMemoryRepository();
        shorturlAppService = ShorturlAppServiceFactory.createShorturlAppService(shorturlRepository);
    }

    @Test
    void testAddShorturl() {
        ShorturlAddCommand command = new ShorturlAddCommand();
        command.setRawLink(ShorturlFactory.testRawLink);
        command.setStatus(ShorturlFactory.testStatus);
        command.setValidTimeStart(ShorturlFactory.testValidTime.getStartTime());
        command.setValidTimeEnd(ShorturlFactory.testValidTime.getEndTime());
        ShorturlAddDTO shorturlAddDTO = shorturlAppService.add(command);

        Shorturl shorturl = shorturlRepository.find(new ShortId(shorturlAddDTO.getShortId()));
        Assertions.assertNotNull(shorturl);
        Assertions.assertEquals(ShorturlFactory.getTestRawLink(), shorturl.getRawLink());
        Assertions.assertEquals(ShorturlFactory.testStatus, shorturl.getStatus());
        Assertions.assertEquals(ShorturlFactory.testValidTime, shorturl.getValidTime());
    }

    @Test
    void testUpdateShorturl() {
        ShorturlAddCommand command = new ShorturlAddCommand();
        command.setRawLink(ShorturlFactory.testRawLink);
        command.setStatus(ShorturlFactory.testStatus);
        command.setValidTimeStart(ShorturlFactory.testValidTime.getStartTime());
        command.setValidTimeEnd(ShorturlFactory.testValidTime.getEndTime());
        ShorturlAddDTO shorturlAddDTO = shorturlAppService.add(command);

        ShorturlUpdateCommand updateCommand = new ShorturlUpdateCommand();
        updateCommand.setShortId(shorturlAddDTO.getShortId());
        updateCommand.setStatus(ShorturlFactory.testChangedStatus);
        updateCommand.setValidTimeStart(ShorturlFactory.testChangedValidTime.getStartTime());
        updateCommand.setValidTimeEnd(ShorturlFactory.testChangedValidTime.getEndTime());
        shorturlAppService.update(updateCommand);

        Shorturl updateShorturl = shorturlRepository.find(new ShortId(shorturlAddDTO.getShortId()));
        Assertions.assertNotNull(updateShorturl);
        Assertions.assertEquals(ShorturlFactory.getTestRawLink(), updateShorturl.getRawLink());
        Assertions.assertEquals(ShorturlFactory.testChangedStatus, updateShorturl.getStatus());
        Assertions.assertEquals(ShorturlFactory.testChangedValidTime, updateShorturl.getValidTime());
    }

    @Test
    void testRedirectToAddedShorturl() {
        ShorturlAddCommand command = new ShorturlAddCommand();
        command.setRawLink(ShorturlFactory.testRawLink);
        command.setStatus(ShorturlFactory.testStatus);
        command.setValidTimeStart(ShorturlFactory.testValidTime.getStartTime());
        command.setValidTimeEnd(ShorturlFactory.testValidTime.getEndTime());
        ShorturlAddDTO shorturlAddDTO = shorturlAppService.add(command);

        Shorturl shorturl = shorturlRepository.find(new ShortId(shorturlAddDTO.getShortId()));
        String redirectLink = shorturlAppService.redirect(shorturl.getShortCode().getCode());
        Assertions.assertEquals(ShorturlFactory.testRawLink, redirectLink);
    }
}
