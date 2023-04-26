package de.cuioss.jsf.bootstrap.layout.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class ContainerFacetsTest {

    @Test
    void shouldHandleValues() {
        for (ContainerFacets facet : ContainerFacets.values()) {
            assertEquals(facet, ContainerFacets.parse(facet.getName()).get());
        }
    }

    @Test
    void shouldHandleInvalidValues() {
        assertFalse(ContainerFacets.parse(null).isPresent());
        assertFalse(ContainerFacets.parse("").isPresent());
        assertFalse(ContainerFacets.parse("null").isPresent());
    }
}
