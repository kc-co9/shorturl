package com.co.kc.shortening.shared.domain.event;

/**
 * @author kc
 */
public interface DomainEventHandler<T extends DomainEvent> {
    /**
     * 事件类型
     *
     * @return 事件Class类型
     */
    Class<T> type();

    /**
     * 事件处理
     *
     * @param event 领域事件
     */
    void handle(T event);
}
