package com.co.kc.shortening.web.common.serializer;

import com.co.kc.shortening.web.common.constants.enums.BaseEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author kc
 */
public class BaseEnumSerializer extends JsonSerializer<BaseEnum> {
    @Override
    public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getCode());
    }
}
