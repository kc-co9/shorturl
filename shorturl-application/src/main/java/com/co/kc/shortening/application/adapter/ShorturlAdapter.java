package com.co.kc.shortening.application.adapter;

import com.co.kc.shortening.shorturl.domain.model.ShorturlStatus;

/**
 * @author kc
 */
public class ShorturlAdapter {

    private ShorturlAdapter() {
    }

    public static ShorturlStatus convertStatus(Integer status) {
        if (status == 1) {
            return ShorturlStatus.ONLINE;
        } else {
            return ShorturlStatus.OFFLINE;
        }
    }
}
