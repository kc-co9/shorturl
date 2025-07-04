package com.co.kc.shortening.infrastructure.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author kc
 */
@Data
@Configuration
public class BaseProperties {
    @Value("${snowflake.dataCenterId: 0}")
    private Integer snowflakeDataCenterId;
}
