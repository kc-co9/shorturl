package com.co.kc.shortening.infrastructure.config;

import com.co.kc.shortening.web.common.BaseEnum;
import com.co.kc.shortening.web.common.serializer.BaseEnumSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kc
 */
@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        BaseEnumSerializer baseEnumSerializer = new BaseEnumSerializer();
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .serializerByType(BaseEnum.class, baseEnumSerializer);
    }
}
