package com.co.kc.shortening.application.model.cqrs.command.blocklist;

import lombok.Data;

/**
 * @author kc
 */
@Data
public class BlocklistUpdateCommand {
    private Long blockId;
    private String remark;
    private Integer status;
}
