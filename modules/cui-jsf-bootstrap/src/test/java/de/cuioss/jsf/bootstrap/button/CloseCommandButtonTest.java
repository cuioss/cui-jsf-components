package de.cuioss.jsf.bootstrap.button;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.CoreJsfTestConfiguration;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "titleKey", "titleValue" })
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class CloseCommandButtonTest extends AbstractComponentTest<CloseCommandButton> {

    @Test
    void shouldProvideDefaultStyleClass() {
        assertEquals(CssBootstrap.BUTTON_CLOSE.getStyleClass(), anyComponent().getStyleClass());
    }

    @Test
    void shouldProvidePassThroughAttributes() {
        var map = anyComponent().getPassThroughAttributes();
        assertTrue(map.containsKey(AttributeName.ARIA_LABEL.getContent()));
        assertEquals(AttributeValue.ARIA_CLOSE.getContent(), map.get(AttributeName.ARIA_LABEL.getContent()));
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.COMPONENT_FAMILY, anyComponent().getFamily());
        assertEquals(BootstrapFamily.CLOSE_COMMAND_BUTTON_RENDERER, anyComponent().getRendererType());
    }
}
