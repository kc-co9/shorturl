package com.co.kc.shortening.shared.domain.model;

import com.co.kc.shortening.common.utils.HashUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class LinkTests {

    @Test
    public void testCreateNullLink() {
        try {
            new Link(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("url is null", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEmptyLink() {
        try {
            new Link("");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("url is null", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateSimpleLink() {
        Link link = new Link("http://www.test.com");
        Assert.assertEquals("http://www.test.com", link.getUrl());
        Assert.assertEquals(HashUtils.murmurHash32("http://www.test.com"), link.getHash());
        Assert.assertEquals("http", link.getProtocol());
        Assert.assertEquals("www.test.com", link.getHost());
        Assert.assertEquals("", link.getPath());
        Assert.assertNull(link.getQuery());
        Assert.assertEquals(-1, link.getPort());
    }

    @Test
    public void testCreateLinkWithPath() {
        Link link = new Link("http://www.test.com/abc");
        Assert.assertEquals("http://www.test.com/abc", link.getUrl());
        Assert.assertEquals(HashUtils.murmurHash32("http://www.test.com/abc"), link.getHash());
        Assert.assertEquals("http", link.getProtocol());
        Assert.assertEquals("www.test.com", link.getHost());
        Assert.assertEquals("/abc", link.getPath());
        Assert.assertNull(link.getQuery());
        Assert.assertEquals(-1, link.getPort());
    }

    @Test
    public void testCreateLinkWithQuery() {
        Link link = new Link("http://www.test.com?q=abc");
        Assert.assertEquals("http://www.test.com?q=abc", link.getUrl());
        Assert.assertEquals(HashUtils.murmurHash32("http://www.test.com?q=abc"), link.getHash());
        Assert.assertEquals("http", link.getProtocol());
        Assert.assertEquals("www.test.com", link.getHost());
        Assert.assertEquals("", link.getPath());
        Assert.assertEquals("q=abc", link.getQuery());
        Assert.assertEquals(-1, link.getPort());
    }

    @Test
    public void testCreateLinkWithPathAndQuery() {
        Link link = new Link("http://www.test.com/abc?q=efg");
        Assert.assertEquals("http://www.test.com/abc?q=efg", link.getUrl());
        Assert.assertEquals(HashUtils.murmurHash32("http://www.test.com/abc?q=efg"), link.getHash());
        Assert.assertEquals("http", link.getProtocol());
        Assert.assertEquals("www.test.com", link.getHost());
        Assert.assertEquals("/abc", link.getPath());
        Assert.assertEquals("q=efg", link.getQuery());
        Assert.assertEquals(-1, link.getPort());
    }

    @Test
    public void testEqLinkWithSameProperties() {
        Link link1 = new Link("http://www.test.com");
        Link link2 = new Link("http://www.test.com");
        Assert.assertEquals(link1, link2);
    }

    @Test
    public void testNotEqLinkWithDifferentProperties() {
        Link link1 = new Link("http://www.test1.com");
        Link link2 = new Link("http://www.test2.com");
        Assert.assertNotEquals(link1, link2);
    }

}
