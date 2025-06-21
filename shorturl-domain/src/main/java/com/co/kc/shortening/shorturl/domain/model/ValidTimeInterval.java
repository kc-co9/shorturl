package com.co.kc.shortening.shorturl.domain.model;

import com.co.kc.shortening.common.utils.DateUtils;
import com.co.kc.shortening.common.utils.ReflectUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 时间范围-值对象
 *
 * @author kc
 */
@Getter
@ToString
@JsonSerialize(using = ValidTimeInterval.ValidTimeIntervalSerializer.class)
@JsonDeserialize(using = ValidTimeInterval.ValidTimeIntervalDeserializer.class)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValidTimeInterval that = (ValidTimeInterval) o;

        String thisFormatStartTime = DateUtils.commonFormat(startTime);
        String thatFormatStartTime = DateUtils.commonFormat(that.startTime);
        if (!Objects.equals(thisFormatStartTime, thatFormatStartTime)) {
            return false;
        }

        String thisFormatEndTime = DateUtils.commonFormat(endTime);
        String thatFormatEndTime = DateUtils.commonFormat(that.endTime);
        return Objects.equals(thisFormatEndTime, thatFormatEndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(DateUtils.commonFormat(startTime), DateUtils.commonFormat(endTime));
    }

    static class ValidTimeIntervalSerializer extends JsonSerializer<ValidTimeInterval> {
        @Override
        public void serialize(ValidTimeInterval validTimeInterval, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField(ReflectUtils.getPropertyName(ValidTimeInterval::getStartTime), DateUtils.commonFormat(validTimeInterval.getStartTime()));
            gen.writeStringField(ReflectUtils.getPropertyName(ValidTimeInterval::getEndTime), DateUtils.commonFormat(validTimeInterval.getEndTime()));
            gen.writeEndObject();
        }
    }

    static class ValidTimeIntervalDeserializer extends JsonDeserializer<ValidTimeInterval> {
        @Override
        public ValidTimeInterval deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            String startTime = node.get(ReflectUtils.getPropertyName(ValidTimeInterval::getStartTime)).asText();
            String endTime = node.get(ReflectUtils.getPropertyName(ValidTimeInterval::getEndTime)).asText();
            return new ValidTimeInterval(DateUtils.commonParse(startTime), DateUtils.commonParse(endTime));
        }
    }
}
