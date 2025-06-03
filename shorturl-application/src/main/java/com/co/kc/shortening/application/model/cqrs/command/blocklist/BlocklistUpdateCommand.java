package com.co.kc.shortening.application.model.cqrs.command.blocklist;

import com.co.kc.shortening.blocklist.domain.model.BlockStatus;
import lombok.Data;

/**
 * @author kc
 */
@Data
public class BlocklistUpdateCommand {
    private Long blockId;
    private String remark;
    private BlockStatus status;
}
