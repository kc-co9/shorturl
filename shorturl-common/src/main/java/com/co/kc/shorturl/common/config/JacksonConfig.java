package com.co.kc.shorturl.common.config;

import com.co.kc.shorturl.common.model.enums.BaseEnum;
import com.co.kc.shorturl.common.processor.serializer.BaseEnumSerializer;
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
