package de.cuioss.jsf.components.inlineconfirm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.jsf.components.CuiFamily;
import de.cuioss.test.jsf.component.AbstractComponentTest;

class InlineConfirmComponentTest extends AbstractComponentTest<InlineConfirmComponent> {

    @Test
    void shouldProvideMetadata() {
        assertEquals(CuiFamily.COMPONENT_FAMILY, anyComponent().getFamily());
        assertEquals(CuiFamily.INLINE_CONFIRM_RENDERER, anyComponent().getRendererType());
    }

    @Test
    void shouldFailWithMissingFacet() {
        var component = anyComponent();
        assertThrows(IllegalArgumentException.class, () -> component.getInitialFacet());
    }

    @Test
    void shouldFailWithMissingChild() {
        var component = anyComponent();
        assertThrows(IllegalArgumentException.class, () -> component.getChildAsModifier());
    }

    @Test
    void shouldHandleChildAndFacet() {
        var underTest = anyComponent();
        underTest.getFacets().put(InlineConfirmComponent.INITIAL_FACET_NAME, new DummyComponent());
        underTest.getChildren().add(new DummyComponent());
        assertNotNull(underTest.getInitialFacet());
        assertNotNull(underTest.getChildAsModifier());
    }
}
