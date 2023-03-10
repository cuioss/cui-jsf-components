package de.cuioss.jsf.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "align" })
class QuickControlGroupComponentTest extends AbstractUiComponentTest<QuickControlGroupComponent> {

    @Test
    void ensureDefaultAlignment() {
        var underTest = anyComponent();
        assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_RIGHT.getStyleClass(),
                underTest.resolveStyleClass().getStyleClass());
    }

    @Test
    void ensureSetAlignment() {
        var underTest = anyComponent();
        underTest.setAlign("fallback-to-default");
        assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_RIGHT.getStyleClass(),
                underTest.resolveStyleClass().getStyleClass());
        underTest.setAlign("left");
        assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_LEFT.getStyleClass(),
                underTest.resolveStyleClass().getStyleClass());
        underTest.setAlign("right");
        assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_RIGHT.getStyleClass(),
                underTest.resolveStyleClass().getStyleClass());
    }

    @Test
    void ensureStyleClassWithAlignment() {
        var underTest = anyComponent();
        underTest.setAlign("left");
        underTest.setStyleClass("foo");
        assertEquals(CssBootstrap.QUICK_CONTROL_GROUP_LEFT.getStyleClassBuilder().append("foo").getStyleClass(),
                underTest.resolveStyleClass().getStyleClass());
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.LAYOUT_RENDERER, anyComponent().getRendererType());
    }
}
