package de.cuioss.jsf.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;

class FormGroupComponentTest extends AbstractUiComponentTest<FormGroupComponent> {

    @Test
    void shouldProvideCorrectStyleClass() {
        final var component = anyComponent();
        final var expected = CssBootstrap.FORM_GROUP.getStyleClass();
        assertEquals(expected, component.resolveStyleClass().getStyleClass());
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.LAYOUT_RENDERER, anyComponent().getRendererType());
    }
}
