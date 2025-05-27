package com.co.kc.shortening.shorturl.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 时间范围-值对象
 *
 * @author kc
 */
@Getter
@ToString
@EqualsAndHashCode
public class ValidTimeInterval {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public ValidTimeInterval(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("startTime is null");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("endTime is null");
        }
        if (startTime.compareTo(endTime) > 0) {
            throw new IllegalArgumentException("endTime is less than startTime");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean contain(LocalDateTime datetime) {
        if (datetime == null) {
            throw new IllegalArgumentException("datetime is null");
        }
        return datetime.compareTo(startTime) >= 0 && datetime.compareTo(endTime) <= 0;
    }
}
