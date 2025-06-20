package com.co.kc.shortening.shared.domain.event;

import java.time.LocalDateTime;

/**
 * 领域事件-基类
 *
 * @author kc
 */
public interface DomainEvent {
    /**
     * 发生时间
     *
     * @return 发生时间
     */
    LocalDateTime occurredOn();
}
