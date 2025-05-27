package com.co.kc.shortening.application.adapter;

import com.co.kc.shortening.shorturl.domain.model.ShorturlStatus;
import org.junit.Assert;
import org.junit.Test;

public class ShorturlAdapterTests {
    @Test
    public void testConvertOnlineStatus() {
        ShorturlStatus shorturlStatus = ShorturlAdapter.convertStatus(1);
        Assert.assertEquals(ShorturlStatus.ONLINE, shorturlStatus);
    }

    @Test
    public void testConvertOfflineStatus() {
        ShorturlStatus shorturlStatus = ShorturlAdapter.convertStatus(0);
        Assert.assertEquals(ShorturlStatus.OFFLINE, shorturlStatus);
    }
}
