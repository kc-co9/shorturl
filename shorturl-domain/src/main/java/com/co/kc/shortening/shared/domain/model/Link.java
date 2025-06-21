package com.co.kc.shortening.shared.domain.model;

import com.co.kc.shortening.common.utils.HashUtils;
import com.co.kc.shortening.common.utils.ReflectUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;

/**
 * 链接值对象
 *
 * @author kc
 */
@Getter
@EqualsAndHashCode
@JsonSerialize(using = Link.LinkSerializer.class)
@JsonDeserialize(using = Link.LinkDeserializer.class)
public class Link {
    /**
     * The original URL
     */
    private final String url;
    /**
     * The hash of original URL
     */
    private final String hash;
    /**
     * The protocol to use (ftp, http, nntp, ... etc.) .
     */
    private final String protocol;
    /**
     * The host name to connect to.
     */
    private final String host;
    /**
     * The protocol port to connect to.
     */
    private final int port;
    /**
     * The path part of this URL.
     */
    private final String path;
    /**
     * The query part of this URL.
     */
    private final String query;

    public Link(String url) {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("url is null");
        }
        URI uri = URI.create(url);
        this.url = uri.toString();
        this.hash = HashUtils.murmurHash32(uri.toString());
        this.protocol = uri.getScheme();
        this.host = uri.getHost();
        this.port = uri.getPort();
        this.path = uri.getPath();
        this.query = uri.getQuery();
    }

    public Link appendPath(String path) {
        return new Link(url + "/" + path);
    }

    static class LinkSerializer extends JsonSerializer<Link> {
        @Override
        public void serialize(Link link, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField(ReflectUtils.getPropertyName(Link::getUrl), link.getUrl());
            gen.writeEndObject();
        }
    }

    static class LinkDeserializer extends JsonDeserializer<Link> {
        @Override
        public Link deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            String url = node.get(ReflectUtils.getPropertyName(Link::getUrl)).asText();
            return new Link(url);
        }
    }
}
