package com.co.kc.shortening.web.common.constants.enums;

import com.co.kc.shortening.application.model.enums.BaseEnum;
import com.co.kc.shortening.shorturl.domain.model.ShorturlStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author kc
 */
@Getter
@AllArgsConstructor
public enum ShorturlFacadeStatus implements BaseEnum {
    /**
     * 上线
     */
    ONLINE(1),
    /**
     * 下线
     */
    OFFLINE(0);

    private final Integer code;

    public static ShorturlFacadeStatus convert(ShorturlStatus status) {
        if (ShorturlStatus.ONLINE.equals(status)) {
            return ShorturlFacadeStatus.ONLINE;
        } else {
            return ShorturlFacadeStatus.OFFLINE;
        }
    }

    public static ShorturlStatus convert(ShorturlFacadeStatus status) {
        if (ShorturlFacadeStatus.ONLINE.equals(status)) {
            return ShorturlStatus.ONLINE;
        } else {
            return ShorturlStatus.OFFLINE;
        }
    }
}
