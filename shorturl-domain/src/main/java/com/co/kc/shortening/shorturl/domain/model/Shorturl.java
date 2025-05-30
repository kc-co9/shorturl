package com.co.kc.shortening.shorturl.domain.model;

import com.co.kc.shortening.shared.domain.model.Identification;
import com.co.kc.shortening.common.exception.BusinessException;
import com.co.kc.shortening.shared.domain.model.Link;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 短链-聚合根
 *
 * @author kc
 */
@EqualsAndHashCode(callSuper = false)
public class Shorturl extends Identification {
    /**
     * 聚合根-唯一标识
     */
    @Getter
    private final ShortId shortId;
    @Getter
    private final ShortCode shortCode;
    @Getter
    private final Link rawLink;
    @Getter
    private ShorturlStatus status;
    @Getter
    private ValidTimeInterval validTime;

    public Shorturl(ShortId shortId, ShortCode shortCode, Link rawLink, ShorturlStatus status, ValidTimeInterval validTime) {
        this.shortId = shortId;
        this.shortCode = shortCode;
        this.rawLink = rawLink;
        this.status = status;
        this.validTime = validTime;
    }

    public Link resolveToRawLink() {
        if (!isActive()) {
            throw new BusinessException("短链已下线");
        }
        if (!isInValidTime()) {
            throw new BusinessException("短链已过期");
        }
        return this.rawLink;
    }

    public Link getLink(Link domain) {
        return domain.appendPath(this.shortCode.getCode());
    }

    public boolean isActive() {
        return ShorturlStatus.ONLINE.equals(this.status);
    }

    public void activate() {
        if (!isInValidTime()) {
            throw new BusinessException("短链已过期，激活失败");
        }
        this.status = ShorturlStatus.ONLINE;
    }

    public void inactivate() {
        this.status = ShorturlStatus.OFFLINE;
    }

    public boolean isInValidTime() {
        return this.validTime.contain(LocalDateTime.now());
    }

    public void changeStatus(ShorturlStatus status) {
        if (this.status.equals(status)) {
            return;
        }
        if (isActive()) {
            inactivate();
        } else {
            activate();
        }
    }

    public void changeValidTime(ValidTimeInterval validTime) {
        this.validTime = validTime;
        if (!isInValidTime()) {
            this.inactivate();
        }
    }

}


