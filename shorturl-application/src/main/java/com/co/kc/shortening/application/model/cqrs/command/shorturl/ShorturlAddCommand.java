package com.co.kc.shortening.application.model.cqrs.command.shorturl;

import com.co.kc.shortening.shorturl.domain.model.ShorturlStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author kc
 */
@Data
public class ShorturlAddCommand {
    private String rawLink;
    private ShorturlStatus status;
    private LocalDateTime validTimeStart;
    private LocalDateTime validTimeEnd;
}
