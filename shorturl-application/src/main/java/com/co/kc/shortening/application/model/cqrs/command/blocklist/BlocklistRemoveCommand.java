package com.co.kc.shortening.application.model.cqrs.command.blocklist;

import lombok.Data;

/**
 * @author kc
 */
@Data
public class BlocklistRemoveCommand {
    private Long blockId;
}
