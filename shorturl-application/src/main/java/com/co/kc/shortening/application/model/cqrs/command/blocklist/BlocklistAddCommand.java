package com.co.kc.shortening.application.model.cqrs.command.blocklist;

import lombok.Data;

/**
 * @author kc
 */
@Data
public class BlocklistAddCommand {
    private String blockLink;
    private String remark;
    private Integer status;
}
