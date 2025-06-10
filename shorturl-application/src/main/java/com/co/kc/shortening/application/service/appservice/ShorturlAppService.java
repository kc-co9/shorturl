package com.co.kc.shortening.application.service.appservice;

import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlAddCommand;
import com.co.kc.shortening.application.model.cqrs.command.shorturl.ShorturlUpdateCommand;
import com.co.kc.shortening.application.model.cqrs.dto.ShorturlDTO;
import com.co.kc.shortening.application.provider.ShortDomainProvider;
import com.co.kc.shortening.application.client.IdClient;
import com.co.kc.shortening.blocklist.service.BlocklistService;
import com.co.kc.shortening.common.exception.NotFoundException;
import com.co.kc.shortening.shorturl.domain.model.ShortCode;
import com.co.kc.shortening.shared.domain.model.Link;
import com.co.kc.shortening.shorturl.domain.model.*;

/**
 * 短链-应用服务
 *
 * @author kc
 */
public class ShorturlAppService {
    private final IdClient<Long> shortIdClient;
    private final IdClient<String> shortCodeClient;
    private final ShortDomainProvider shortDomainProvider;

    private final BlocklistService blocklistService;

    private final ShorturlRepository shorturlRepository;

    public ShorturlAppService(IdClient<Long> shortIdClient,
                              IdClient<String> shortCodeClient,
                              ShortDomainProvider shortDomainProvider,
                              BlocklistService blocklistService,
                              ShorturlRepository shorturlRepository) {
        this.shortIdClient = shortIdClient;
        this.shortCodeClient = shortCodeClient;
        this.shortDomainProvider = shortDomainProvider;
        this.blocklistService = blocklistService;
        this.shorturlRepository = shorturlRepository;
    }

    public ShorturlDTO add(ShorturlAddCommand command) {
        Link rawLink = new Link(command.getRawLink());
        ShorturlStatus status = command.getStatus();
        ValidTimeInterval validTime = new ValidTimeInterval(command.getValidTimeStart(), command.getValidTimeEnd());
        blocklistService.validate(rawLink);

        ShortId shortId = new ShortId(shortIdClient.next());
        ShortCode shortCode = new ShortCode(shortCodeClient.next());
        Shorturl shorturl = new Shorturl(shortId, shortCode, rawLink, status, validTime);
        shorturlRepository.save(shorturl);

        Link shortDomain = new Link(shortDomainProvider.getDomain());
        String shortLink = shorturl.getLink(shortDomain).getUrl();
        return new ShorturlDTO(shorturl.getShortId().getId(), shortLink);
    }

    public void update(ShorturlUpdateCommand command) {
        ShortId shortId = new ShortId(command.getShortId());
        ShorturlStatus status = command.getStatus();
        ValidTimeInterval validTime = new ValidTimeInterval(command.getValidTimeStart(), command.getValidTimeEnd());

        Shorturl shorturl = shorturlRepository.find(shortId);
        if (shorturl == null) {
            throw new NotFoundException("短链不存在");
        }

        shorturl.changeStatus(status);
        shorturl.changeValidTime(validTime);
        shorturlRepository.save(shorturl);
    }

    public String redirect(String shortCode) {
        Shorturl shorturl = shorturlRepository.find(new ShortCode(shortCode));
        if (shorturl == null) {
            throw new NotFoundException("短链不存在");
        }

        Link rawLink = shorturl.resolveToRawLink();

        blocklistService.validate(rawLink);

        return rawLink.getUrl();
    }
}
