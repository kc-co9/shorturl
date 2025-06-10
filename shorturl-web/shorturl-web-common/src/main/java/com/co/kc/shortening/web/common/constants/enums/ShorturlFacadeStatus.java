package com.co.kc.shortening.web.common.constants.enums;

import com.co.kc.shortening.shorturl.domain.model.ShorturlStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

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

    public static Optional<ShorturlFacadeStatus> convert(ShorturlStatus status) {
        if (status == null) {
            return Optional.empty();
        }
        switch (status) {
            case ONLINE:
                return Optional.of(ShorturlFacadeStatus.ONLINE);
            case OFFLINE:
                return Optional.of(ShorturlFacadeStatus.OFFLINE);
            default:
                return Optional.empty();
        }
    }

    public static Optional<ShorturlStatus> convert(ShorturlFacadeStatus status) {
        if (status == null) {
            return Optional.empty();
        }
        switch (status) {
            case ONLINE:
                return Optional.of(ShorturlStatus.ONLINE);
            case OFFLINE:
                return Optional.of(ShorturlStatus.OFFLINE);
            default:
                return Optional.empty();
        }
    }
}
