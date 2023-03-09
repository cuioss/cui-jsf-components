package com.icw.ehf.cui.core.api.components.base;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "titleKey", "titleValue" })
class BaseCuiOutputTextTest extends AbstractComponentTest<MockBaseCuiOutputText> {

    @Test
    void shouldProvideDefaultStyleClass() {
        assertEquals("mock", anyComponent().getStyleClass());
    }

    @Test
    void shouldExtendStyleClass() {
        var component = anyComponent();
        component.setStyleClass("foo");
        assertEquals("mock foo", component.getStyleClass());
    }
}
