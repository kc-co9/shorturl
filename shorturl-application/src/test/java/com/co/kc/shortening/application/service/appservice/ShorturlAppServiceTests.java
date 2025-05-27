package com.co.kc.shortening.application.service.appservice;

import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.application.client.RandomCodeClient;
import com.co.kc.shortening.application.client.RandomIdClient;
import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlUpdateCommand;
import com.co.kc.shortening.application.model.cqrs.dto.ShorturlDTO;
import com.co.kc.shortening.application.provider.ShortDomainProvider;
import com.co.kc.shortening.application.provider.StaticShortDomainProvider;
import com.co.kc.shortening.blocklist.domain.model.BlocklistMemoryRepository;
import com.co.kc.shortening.blocklist.domain.model.BlocklistRepository;
import com.co.kc.shortening.blocklist.service.BlocklistService;
import com.co.kc.shortening.shorturl.domain.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ShorturlAppServiceTests {

    private ShorturlRepository shorturlRepository;
    private ShorturlAppService shorturlAppService;

    @Before
    public void initShorturlAppService() {
        IdClient<Long> shortIdClient = new RandomIdClient();
        IdClient<String> shortCodeClient = new RandomCodeClient();
        ShortDomainProvider shortDomainProvider = new StaticShortDomainProvider();
        BlocklistRepository blocklistRepository = new BlocklistMemoryRepository();
        BlocklistService blocklistService = new BlocklistService(blocklistRepository);
        shorturlRepository = new ShorturlMemoryRepository();
        shorturlAppService = new ShorturlAppService(shortIdClient, shortCodeClient, shortDomainProvider, blocklistService, shorturlRepository);
    }

    @Test
    public void testAddShorturl() {
        LocalDateTime validTimeStart = LocalDateTime.now().minus(10, ChronoUnit.MINUTES);
        LocalDateTime validTimeEnd = LocalDateTime.now().plus(10, ChronoUnit.MINUTES);

        ShorturlAddCommand command = new ShorturlAddCommand();
        command.setRawLink("https://rawlink.com");
        command.setStatus(1);
        command.setValidTimeStart(validTimeStart);
        command.setValidTimeEnd(validTimeEnd);
        ShorturlDTO shorturlDTO = shorturlAppService.add(command);

        Shorturl shorturl = shorturlRepository.find(new ShortId(shorturlDTO.getShortId()));
        Assert.assertNotNull(shorturl);
        Assert.assertEquals("https://rawlink.com", shorturl.getRawLink().getUrl());
        Assert.assertEquals(ShorturlStatus.ONLINE, shorturl.getStatus());
        Assert.assertEquals(validTimeStart, shorturl.getValidTime().getStartTime());
        Assert.assertEquals(validTimeEnd, shorturl.getValidTime().getEndTime());
    }

    @Test
    public void testUpdateShorturl() {
        ShorturlAddCommand addCommand = new ShorturlAddCommand();
        addCommand.setRawLink("https://rawlink.com");
        addCommand.setStatus(1);
        addCommand.setValidTimeStart(LocalDateTime.now().minus(10, ChronoUnit.MINUTES));
        addCommand.setValidTimeEnd(LocalDateTime.now().plus(10, ChronoUnit.MINUTES));
        ShorturlDTO shorturlDTO = shorturlAppService.add(addCommand);

        Shorturl addShorturl = shorturlRepository.find(new ShortId(shorturlDTO.getShortId()));
        Assert.assertNotNull(addShorturl);
        Assert.assertEquals("https://rawlink.com", addShorturl.getRawLink().getUrl());
        Assert.assertEquals(ShorturlStatus.ONLINE, addShorturl.getStatus());
        Assert.assertEquals(addCommand.getValidTimeStart(), addShorturl.getValidTime().getStartTime());
        Assert.assertEquals(addCommand.getValidTimeEnd(), addShorturl.getValidTime().getEndTime());


        ShorturlUpdateCommand updateCommand = new ShorturlUpdateCommand();
        updateCommand.setShortId(addShorturl.getShortId().getId());
        updateCommand.setStatus(0);
        updateCommand.setValidTimeStart(LocalDateTime.now().minus(20, ChronoUnit.MINUTES));
        updateCommand.setValidTimeEnd(LocalDateTime.now().plus(20, ChronoUnit.MINUTES));
        shorturlAppService.update(updateCommand);

        Shorturl updateShorturl = shorturlRepository.find(new ShortId(shorturlDTO.getShortId()));
        Assert.assertNotNull(updateShorturl);
        Assert.assertEquals("https://rawlink.com", updateShorturl.getRawLink().getUrl());
        Assert.assertEquals(ShorturlStatus.OFFLINE, updateShorturl.getStatus());
        Assert.assertEquals(updateCommand.getValidTimeStart(), updateShorturl.getValidTime().getStartTime());
        Assert.assertEquals(updateCommand.getValidTimeEnd(), updateShorturl.getValidTime().getEndTime());
    }

    @Test
    public void testRedirectToAddedShorturl() {
        ShorturlAddCommand addCommand = new ShorturlAddCommand();
        addCommand.setRawLink("https://rawlink.com");
        addCommand.setStatus(1);
        addCommand.setValidTimeStart(LocalDateTime.now().minus(10, ChronoUnit.MINUTES));
        addCommand.setValidTimeEnd(LocalDateTime.now().plus(10, ChronoUnit.MINUTES));
        ShorturlDTO shorturlDTO = shorturlAppService.add(addCommand);
        Shorturl shorturl = shorturlRepository.find(new ShortId(shorturlDTO.getShortId()));

        String redirectLink = shorturlAppService.redirect(shorturl.getShortCode().getCode());
        Assert.assertEquals("https://rawlink.com", redirectLink);
    }
}
