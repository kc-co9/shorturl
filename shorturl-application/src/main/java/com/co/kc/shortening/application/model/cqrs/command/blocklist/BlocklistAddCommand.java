package com.co.kc.shortening.application.model.cqrs.command.blocklist;

import com.co.kc.shortening.blocklist.domain.model.BlockStatus;
import lombok.Data;

/**
 * @author kc
 */
@Data
public class BlocklistAddCommand {
    private String blockLink;
    private String remark;
    private BlockStatus status;
}
