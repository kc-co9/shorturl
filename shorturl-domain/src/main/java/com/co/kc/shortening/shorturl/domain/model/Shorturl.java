package com.co.kc.shortening.shorturl.domain.model;

import com.co.kc.shortening.common.utils.ReflectUtils;
import com.co.kc.shortening.shared.domain.model.Identification;
import com.co.kc.shortening.common.exception.BusinessException;
import com.co.kc.shortening.shared.domain.model.Link;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 短链-聚合根
 *
 * @author kc
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonSerialize(using = Shorturl.ShorturlSerializer.class)
@JsonDeserialize(using = Shorturl.ShorturlDeserializer.class)
public class Shorturl extends Identification {
    /**
     * 聚合根-唯一标识
     */

    private final ShortId shortId;
    private final ShortCode shortCode;
    private final Link rawLink;
    private ShorturlStatus status;
    private ValidTimeInterval validTime;

    public Shorturl(ShortId shortId, ShortCode shortCode, Link rawLink, ShorturlStatus status, ValidTimeInterval validTime) {
        this.shortId = shortId;
        this.shortCode = shortCode;
        this.rawLink = rawLink;
        this.status = status;
        this.validTime = validTime;
    }

    public Link resolveToRawLink() {
        if (!isActive()) {
            throw new BusinessException("短链已下线");
        }
        if (!isInValidTime()) {
            throw new BusinessException("短链已过期");
        }
        return this.rawLink;
    }

    public Link getLink(Link domain) {
        return domain.appendPath(this.shortCode.getCode());
    }

    public boolean isActive() {
        return ShorturlStatus.ONLINE.equals(this.status);
    }

    public void activate() {
        if (!isInValidTime()) {
            throw new BusinessException("短链已过期，激活失败");
        }
        this.status = ShorturlStatus.ONLINE;
    }

    public void inactivate() {
        this.status = ShorturlStatus.OFFLINE;
    }

    public boolean isInValidTime() {
        return this.validTime.contain(LocalDateTime.now());
    }

    public void changeStatus(ShorturlStatus status) {
        if (this.status.equals(status)) {
            return;
        }
        if (isActive()) {
            inactivate();
        } else {
            activate();
        }
    }

    public void changeValidTime(ValidTimeInterval validTime) {
        this.validTime = validTime;
        if (!isInValidTime()) {
            this.inactivate();
        }
    }

    static class ShorturlSerializer extends JsonSerializer<Shorturl> {
        @Override
        public void serialize(Shorturl shorturl, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeNumberField(ReflectUtils.getPropertyName(Shorturl::getId), shorturl.getId());
            gen.writeNumberField(ReflectUtils.getPropertyName(Shorturl::getShortId), shorturl.getShortId().getId());
            gen.writeStringField(ReflectUtils.getPropertyName(Shorturl::getShortCode), shorturl.getShortCode().getCode());
            gen.writeStringField(ReflectUtils.getPropertyName(Shorturl::getRawLink), shorturl.getRawLink().getUrl());
            gen.writeStringField(ReflectUtils.getPropertyName(Shorturl::getStatus), shorturl.getStatus().name());
            gen.writeObjectField(ReflectUtils.getPropertyName(Shorturl::getValidTime), shorturl.getValidTime());
            gen.writeEndObject();
        }
    }

    static class ShorturlDeserializer extends JsonDeserializer<Shorturl> {
        @Override
        public Shorturl deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            ObjectCodec objectCodec = p.getCodec();
            JsonNode node = objectCodec.readTree(p);

            Long id = node.get(ReflectUtils.getPropertyName(Shorturl::getId)).asLong();
            Long shortId = node.get(ReflectUtils.getPropertyName(Shorturl::getShortId)).asLong();
            String shortCode = node.get(ReflectUtils.getPropertyName(Shorturl::getShortCode)).asText();
            String rawLink = node.get(ReflectUtils.getPropertyName(Shorturl::getRawLink)).asText();
            String statusName = node.get(ReflectUtils.getPropertyName(Shorturl::getStatus)).asText();
            JsonNode validTime = node.get(ReflectUtils.getPropertyName(Shorturl::getValidTime));

            Shorturl shorturl =
                    new Shorturl(
                            new ShortId(shortId),
                            new ShortCode(shortCode),
                            new Link(rawLink),
                            ShorturlStatus.valueOf(statusName),
                            objectCodec.treeToValue(validTime, ValidTimeInterval.class));
            shorturl.setId(id);
            return shorturl;
        }
    }

}


