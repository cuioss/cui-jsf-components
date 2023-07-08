package de.cuioss.jsf.api.components.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.component.AbstractComponentTest;

class CuiComponentBaseTest extends AbstractComponentTest<CuiComponentBase> {

    @Test
    void shouldActAsBridge() {
        assertNotNull(anyComponent().facesContext());
        assertNull(anyComponent().facet("notThere"));
        assertNotNull(anyComponent().stateHelper());
        assertEquals(CuiComponentBase.COMPONENT_FAMILY, anyComponent().getFamily());
    }

}
