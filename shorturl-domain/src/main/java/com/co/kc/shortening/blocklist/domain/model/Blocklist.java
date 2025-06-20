package com.co.kc.shortening.blocklist.domain.model;

import com.co.kc.shortening.shared.domain.model.Identification;
import com.co.kc.shortening.shared.domain.model.Link;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.IOException;

/**
 * 黑名单-聚合根
 *
 * @author kc
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonSerialize(using = Blocklist.BlocklistSerializer.class)
@JsonDeserialize(using = Blocklist.BlocklistDeserializer.class)
public class Blocklist extends Identification {
    /**
     * 黑名单聚合根-唯一标识
     */
    private final BlockId blockId;
    private final Link link;
    private BlockRemark remark;
    private BlockStatus status;

    @JsonCreator
    public Blocklist(BlockId blockId, Link link, BlockRemark remark, BlockStatus status) {
        this.blockId = blockId;
        this.link = link;
        this.remark = remark;
        this.status = status;
    }

    public boolean isActive() {
        return BlockStatus.ONLINE.equals(this.status);
    }

    public void activate() {
        this.status = BlockStatus.ONLINE;
    }

    public void inactivate() {
        this.status = BlockStatus.OFFLINE;
    }

    public void changeRemark(BlockRemark remark) {
        this.remark = remark;
    }

    public void changeStatus(BlockStatus status) {
        if (this.status.equals(status)) {
            return;
        }
        if (isActive()) {
            inactivate();
        } else {
            activate();
        }
    }

    static class BlocklistSerializer extends JsonSerializer<Blocklist> {
        @Override
        public void serialize(Blocklist blocklist, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeNumberField("id", blocklist.getId());
            gen.writeNumberField("blockId", blocklist.getBlockId().getId());
            gen.writeStringField("link", blocklist.getLink().getUrl());
            gen.writeStringField("remark", blocklist.getRemark().getRemark());
            gen.writeStringField("status", blocklist.getStatus().name());
            gen.writeEndObject();
        }
    }

    static class BlocklistDeserializer extends JsonDeserializer<Blocklist> {
        @Override
        public Blocklist deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);

            Long id = node.get("id").asLong();
            BlockId blockId = new BlockId(node.get("blockId").asLong());
            Link link = new Link(node.get("link").asText());
            BlockRemark remark = new BlockRemark(node.get("remark").asText());
            BlockStatus status = BlockStatus.valueOf(node.get("status").asText());

            Blocklist blocklist = new Blocklist(blockId, link, remark, status);
            blocklist.setId(id);
            return blocklist;
        }
    }
}
