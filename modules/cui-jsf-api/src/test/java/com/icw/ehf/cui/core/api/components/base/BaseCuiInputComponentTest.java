package com.icw.ehf.cui.core.api.components.base;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "styleClass", "rendered", "style" })
class BaseCuiInputComponentTest extends AbstractComponentTest<BaseCuiInputComponent> {

    @Test
    void shouldBridgeFacesContext() {
        assertNotNull(anyComponent().facesContext());
    }
}
