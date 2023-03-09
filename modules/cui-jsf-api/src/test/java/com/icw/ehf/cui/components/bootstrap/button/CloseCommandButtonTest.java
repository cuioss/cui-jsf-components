package com.icw.ehf.cui.components.bootstrap.button;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.core.api.CoreJsfTestConfiguration;
import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.AttributeValue;

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
