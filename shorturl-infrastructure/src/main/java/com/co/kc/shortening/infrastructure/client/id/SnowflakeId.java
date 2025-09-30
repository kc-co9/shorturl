package com.co.kc.shortening.infrastructure.client.id;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 雪花ID由64位二进制数组成:
 * - 前41位是时间戳（从选定epoch算起的毫秒数）；
 * - 中间10位是机器ID，其中机器ID由5位数据中心+5位机器ID组成（在分布式环境下防止机器之间发生冲突）；
 * - 最后12位是每台机器中的序列号（同一个时间戳下可生成 2^(13)-1 个雪花ID）。
 *
 * @author kc
 */
public class SnowflakeId {
    /**
     * 起始的时间戳
     */
    private static final long EPOCH =
            LocalDateTime.of(2025, 1, 1, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

    /**
     * 每一部分占用的位数
     */
    private static final int TIMESTAMP_BIT = 41;
    private static final int DATACENTER_BIT = 5;
    private static final int MACHINE_BIT = 5;
    private static final int SEQUENCE_BIT = 12;

    /**
     * 每一部分的最大值
     */
    public static final long MAX_TIMESTAMP = ~(-1L << TIMESTAMP_BIT);
    public static final long MAX_DATACENTER = ~(-1L << DATACENTER_BIT);
    public static final long MAX_MACHINE = ~(-1L << MACHINE_BIT);
    public static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分的位便宜量
     */
    private static final int TIMESTAMP_BIT_SHIFT = DATACENTER_BIT + MACHINE_BIT + SEQUENCE_BIT;
    private static final int DATACENTER_BIT_SHIFT = MACHINE_BIT + SEQUENCE_BIT;
    private static final int MACHINE_BIT_SHIFT = SEQUENCE_BIT;
    private static final int SEQUENCE_BIT_SHIFT = 0;


    private final MachineIdAllocator machineIdAllocator;

    /**
     * 机器序列号
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private long lastTimestamp = -1L;

    public SnowflakeId(MachineIdAllocator machineIdAllocator) {
        this.machineIdAllocator = machineIdAllocator;
    }

    public synchronized Long next() {
        long currTimestamp = getTimestamp();
        if (currTimestamp < lastTimestamp) {
            throw new IllegalStateException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - currTimestamp));
        }

        if (currTimestamp == lastTimestamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currTimestamp = waitForNextTimestamp(currTimestamp);
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastTimestamp = currTimestamp;

        long milliseconds = currTimestamp - EPOCH;
        if (milliseconds > MAX_TIMESTAMP) {
            throw new IllegalStateException(String.format("The milliseconds is greater than maximum. Refusing to generate id for %d milliseconds", currTimestamp));
        }

        // 时间戳部分 | 数据中心部分 | 机器标识部分 | 序列号部分
        return milliseconds << TIMESTAMP_BIT_SHIFT | getDatacenterId() << DATACENTER_BIT_SHIFT | getMachineId() << MACHINE_BIT_SHIFT | sequence << SEQUENCE_BIT_SHIFT;
    }

    private long getTimestamp() {
        return System.currentTimeMillis();
    }


    private long getDatacenterId() {
        if (machineIdAllocator.getDatacenterId() > MAX_DATACENTER || machineIdAllocator.getDatacenterId() < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        return machineIdAllocator.getDatacenterId();
    }

    private long getMachineId() {
        if (machineIdAllocator.getMachineId() > MAX_MACHINE || machineIdAllocator.getMachineId() < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        return machineIdAllocator.getMachineId();
    }

    /**
     * running loop blocking until next millisecond
     *
     * @param currTimestamp current time stamp
     * @return current timestamp in millisecond
     */
    private long waitForNextTimestamp(long currTimestamp) {
        while (currTimestamp <= lastTimestamp) {
            currTimestamp = getTimestamp();
        }
        return currTimestamp;
    }
}
