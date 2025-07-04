package com.co.kc.shortening.infrastructure.constant;

/**
 * @author kc
 */
public class ZooKeeperConstants {

    private static final String SNOWFLAKE_PATH = "snowflake";
    private static final String SNOWFLAKE_DATA_CENTER_PATH = "data_center:%d";

    private ZooKeeperConstants() {
    }

    public static String getSnowflakePath() {
        return SNOWFLAKE_PATH;
    }

    public static String getDataCenterPath(long dataCenterId) {
        return String.format(SNOWFLAKE_DATA_CENTER_PATH, dataCenterId);
    }
}
