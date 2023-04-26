package de.cuioss.jsf.bootstrap.icon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "icon", "size", "state", "titleKey", "titleValue" })
class IconComponentTest extends AbstractUiComponentTest<IconComponent> {

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.ICON_COMPONENT_RENDERER, anyComponent().getRendererType());
    }
}
