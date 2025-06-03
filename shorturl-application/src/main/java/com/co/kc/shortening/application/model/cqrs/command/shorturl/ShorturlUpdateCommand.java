package com.co.kc.shortening.application.model.cqrs.command.shorturl;

import com.co.kc.shortening.shorturl.domain.model.ShorturlStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author kc
 */
@Data
public class ShorturlUpdateCommand {
    private Long shortId;
    private ShorturlStatus status;
    private LocalDateTime validTimeStart;
    private LocalDateTime validTimeEnd;
}
