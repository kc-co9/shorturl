package com.co.kc.shortening.application.service.appservice;

import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlUpdateCommand;
import com.co.kc.shortening.application.model.cqrs.dto.ShorturlDTO;
import com.co.kc.shortening.shorturl.domain.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShorturlAppServiceTests {
    private ShorturlRepository shorturlRepository;
    private ShorturlAppService shorturlAppService;

    @Before
    public void initShorturlAppService() {
        shorturlRepository = new ShorturlMemoryRepository();
        shorturlAppService = ShorturlAppServiceFactory.createShorturlAppService(shorturlRepository);
    }

    @Test
    public void testAddShorturl() {
        ShorturlAddCommand command = new ShorturlAddCommand();
        command.setRawLink(ShorturlFactory.testRawLink);
        command.setStatus(ShorturlFactory.testStatus);
        command.setValidTimeStart(ShorturlFactory.testValidTime.getStartTime());
        command.setValidTimeEnd(ShorturlFactory.testValidTime.getEndTime());
        ShorturlDTO shorturlDTO = shorturlAppService.add(command);

        Shorturl shorturl = shorturlRepository.find(new ShortId(shorturlDTO.getShortId()));
        Assert.assertNotNull(shorturl);
        Assert.assertEquals(ShorturlFactory.getTestRawLink(), shorturl.getRawLink());
        Assert.assertEquals(ShorturlFactory.testStatus, shorturl.getStatus());
        Assert.assertEquals(ShorturlFactory.testValidTime, shorturl.getValidTime());
    }

    @Test
    public void testUpdateShorturl() {
        ShorturlAddCommand command = new ShorturlAddCommand();
        command.setRawLink(ShorturlFactory.testRawLink);
        command.setStatus(ShorturlFactory.testStatus);
        command.setValidTimeStart(ShorturlFactory.testValidTime.getStartTime());
        command.setValidTimeEnd(ShorturlFactory.testValidTime.getEndTime());
        ShorturlDTO shorturlDTO = shorturlAppService.add(command);

        ShorturlUpdateCommand updateCommand = new ShorturlUpdateCommand();
        updateCommand.setShortId(shorturlDTO.getShortId());
        updateCommand.setStatus(ShorturlFactory.testChangedStatus);
        updateCommand.setValidTimeStart(ShorturlFactory.testChangedValidTime.getStartTime());
        updateCommand.setValidTimeEnd(ShorturlFactory.testChangedValidTime.getEndTime());
        shorturlAppService.update(updateCommand);

        Shorturl updateShorturl = shorturlRepository.find(new ShortId(shorturlDTO.getShortId()));
        Assert.assertNotNull(updateShorturl);
        Assert.assertEquals(ShorturlFactory.getTestRawLink(), updateShorturl.getRawLink());
        Assert.assertEquals(ShorturlFactory.testChangedStatus, updateShorturl.getStatus());
        Assert.assertEquals(ShorturlFactory.testChangedValidTime, updateShorturl.getValidTime());
    }

    @Test
    public void testRedirectToAddedShorturl() {
        ShorturlAddCommand command = new ShorturlAddCommand();
        command.setRawLink(ShorturlFactory.testRawLink);
        command.setStatus(ShorturlFactory.testStatus);
        command.setValidTimeStart(ShorturlFactory.testValidTime.getStartTime());
        command.setValidTimeEnd(ShorturlFactory.testValidTime.getEndTime());
        ShorturlDTO shorturlDTO = shorturlAppService.add(command);

        Shorturl shorturl = shorturlRepository.find(new ShortId(shorturlDTO.getShortId()));
        String redirectLink = shorturlAppService.redirect(shorturl.getShortCode().getCode());
        Assert.assertEquals(ShorturlFactory.testRawLink, redirectLink);
    }
}
