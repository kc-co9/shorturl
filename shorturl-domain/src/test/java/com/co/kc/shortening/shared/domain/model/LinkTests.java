package com.co.kc.shortening.shared.domain.model;

import com.co.kc.shortening.common.utils.HashUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class LinkTests {

    @Test
    void testCreateNullLink() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new Link(null));
        Assertions.assertEquals("url is null", e.getMessage());
    }

    @Test
    void testCreateEmptyLink() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> new Link(""));
        Assertions.assertEquals("url is null", e.getMessage());
    }

    @Test
    void testCreateSimpleLink() {
        Link link = new Link("http://www.test.com");
        Assertions.assertEquals("http://www.test.com", link.getUrl());
        Assertions.assertEquals(HashUtils.murmurHash32("http://www.test.com"), link.getHash());
        Assertions.assertEquals("http", link.getProtocol());
        Assertions.assertEquals("www.test.com", link.getHost());
        Assertions.assertEquals("", link.getPath());
        Assertions.assertNull(link.getQuery());
        Assertions.assertEquals(-1, link.getPort());
    }

    @Test
    void testCreateLinkWithPath() {
        Link link = new Link("http://www.test.com/abc");
        Assertions.assertEquals("http://www.test.com/abc", link.getUrl());
        Assertions.assertEquals(HashUtils.murmurHash32("http://www.test.com/abc"), link.getHash());
        Assertions.assertEquals("http", link.getProtocol());
        Assertions.assertEquals("www.test.com", link.getHost());
        Assertions.assertEquals("/abc", link.getPath());
        Assertions.assertNull(link.getQuery());
        Assertions.assertEquals(-1, link.getPort());
    }

    @Test
    void testCreateLinkWithQuery() {
        Link link = new Link("http://www.test.com?q=abc");
        Assertions.assertEquals("http://www.test.com?q=abc", link.getUrl());
        Assertions.assertEquals(HashUtils.murmurHash32("http://www.test.com?q=abc"), link.getHash());
        Assertions.assertEquals("http", link.getProtocol());
        Assertions.assertEquals("www.test.com", link.getHost());
        Assertions.assertEquals("", link.getPath());
        Assertions.assertEquals("q=abc", link.getQuery());
        Assertions.assertEquals(-1, link.getPort());
    }

    @Test
    void testCreateLinkWithPathAndQuery() {
        Link link = new Link("http://www.test.com/abc?q=efg");
        Assertions.assertEquals("http://www.test.com/abc?q=efg", link.getUrl());
        Assertions.assertEquals(HashUtils.murmurHash32("http://www.test.com/abc?q=efg"), link.getHash());
        Assertions.assertEquals("http", link.getProtocol());
        Assertions.assertEquals("www.test.com", link.getHost());
        Assertions.assertEquals("/abc", link.getPath());
        Assertions.assertEquals("q=efg", link.getQuery());
        Assertions.assertEquals(-1, link.getPort());
    }

    @Test
    void testEqLinkWithSameProperties() {
        Link link1 = new Link("http://www.test.com");
        Link link2 = new Link("http://www.test.com");
        Assertions.assertEquals(link1, link2);
    }

    @Test
    void testNotEqLinkWithDifferentProperties() {
        Link link1 = new Link("http://www.test1.com");
        Link link2 = new Link("http://www.test2.com");
        Assertions.assertNotEquals(link1, link2);
    }

}
