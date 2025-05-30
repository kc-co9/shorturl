package com.co.kc.shortening.shared.domain.model;

import com.co.kc.shortening.common.utils.HashUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;

/**
 * 链接值对象
 *
 * @author kc
 */
@Getter
@EqualsAndHashCode
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
}
