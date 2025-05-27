package com.co.kc.shortening.application.model.cqrs.command.shorturl;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author kc
 */
@Data
public class ShorturlAddCommand {
    private String rawLink;
    private Integer status;
    private LocalDateTime validTimeStart;
    private LocalDateTime validTimeEnd;
}
