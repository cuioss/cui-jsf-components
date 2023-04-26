package de.cuioss.jsf.bootstrap.badge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "titleKey", "titleValue" })
class BadgeComponentTest extends AbstractComponentTest<BadgeComponent> {

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(JsfHtmlComponent.SPAN.getRendererType(), anyComponent().getRendererType());
        assertEquals(JsfHtmlComponent.SPAN.getFamily(), anyComponent().getFamily());
    }
}
