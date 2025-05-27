package com.co.kc.shortening.shared.domain.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author kc
 */
public class IdentificationTests {
    @Test
    public void testCreateNullIdentification() {
        try {
            new Identification().setId(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "id is null");
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateEqZeroIdentification() {
        try {
            new Identification().setId(0L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "id is less than or equal to 0");
            return;
        }
        Assert.fail();
    }

    @Test
    public void testCreateLessThanZeroIdentification() {
        try {
            new Identification().setId(-1L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "id is less than or equal to 0");
            return;
        }
        Assert.fail();
    }

    @Test
    public void testEqIdentificationWithSameProperties() {
        Identification identification1 = new Identification();
        identification1.setId(10L);
        Identification identification2 = new Identification();
        identification2.setId(10L);
        Assert.assertEquals(identification1, identification2);
    }

    @Test
    public void testNotEqIdentificationWithDifferentProperties() {
        Identification identification1 = new Identification();
        identification1.setId(10L);
        Identification identification2 = new Identification();
        identification2.setId(20L);
        Assert.assertNotEquals(identification1, identification2);
    }

}
