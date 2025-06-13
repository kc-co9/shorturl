package com.co.kc.shortening.shared.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kc
 */
class IdentificationTests {
    @Test
    void testCreateNullIdentification() {
        Identification identification = new Identification();
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> identification.setId(null));
        Assertions.assertEquals("id is null", e.getMessage());
    }

    @Test
    void testCreateEqZeroIdentification() {
        Identification identification = new Identification();
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> identification.setId(0L));
        Assertions.assertEquals("id is less than or equal to 0", e.getMessage());
    }

    @Test
    void testCreateLessThanZeroIdentification() {
        Identification identification = new Identification();
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> identification.setId(-1L));
        Assertions.assertEquals("id is less than or equal to 0", e.getMessage());
    }

    @Test
    void testEqIdentificationWithSameProperties() {
        Identification identification1 = new Identification();
        identification1.setId(10L);
        Identification identification2 = new Identification();
        identification2.setId(10L);
        Assertions.assertEquals(identification1, identification2);
    }

    @Test
    void testNotEqIdentificationWithDifferentProperties() {
        Identification identification1 = new Identification();
        identification1.setId(10L);
        Identification identification2 = new Identification();
        identification2.setId(20L);
        Assertions.assertNotEquals(identification1, identification2);
    }

}
