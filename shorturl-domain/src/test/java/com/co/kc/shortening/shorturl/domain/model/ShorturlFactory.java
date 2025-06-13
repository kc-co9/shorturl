package com.co.kc.shortening.shorturl.domain.model;

import com.co.kc.shortening.shared.domain.model.Link;

import java.time.LocalDateTime;

public class ShorturlFactory {

    public static final Long testShortId = 10L;
    public static final String testShortCode = "testCode";
    public static final String testRawLink = "https://www.test.com";
    public static final String testShortDomain = "https://www.short.com";
    public static final String testShortLink = "https://www.short.com/testCode";
    public static final ShorturlStatus testStatus = ShorturlStatus.ONLINE;
    public static final ShorturlStatus testChangedStatus = ShorturlStatus.OFFLINE;
    public static final ValidTimeInterval testValidTime = new ValidTimeInterval(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
    public static final ValidTimeInterval testChangedValidTime = new ValidTimeInterval(LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(10));
    public static final ValidTimeInterval testExpiredValidTime = new ValidTimeInterval(LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1));

    public static Shorturl createTestShorturl() {
        return new Shorturl(getTestShortId(), getTestShortCode(), getTestRawLink(), testStatus, testValidTime);
    }

    public static ShortId getTestShortId() {
        return new ShortId(testShortId);
    }

    public static ShortCode getTestShortCode() {
        return new ShortCode(testShortCode);
    }

    public static Link getTestRawLink() {
        return new Link(testRawLink);
    }

    public static Link getTestShortDomain() {
        return new Link(testShortDomain);
    }

    public static Link getTestShortLink() {
        return new Link(testShortLink);
    }

}
