package de.cuioss.jsf.api.components.base;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.base.BaseCuiPanel.PanelType;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "styleClass", "rendered", "style" })
class BaseCuiPanelTest extends AbstractComponentTest<BaseCuiPanel> {

    @Test
    void shouldCreateAccordingGivenType() {
        assertEquals("block", new BaseCuiPanel(PanelType.DIV).getLayout());
        assertEquals("span", new BaseCuiPanel(PanelType.SPAN).getLayout());
        assertEquals("block", new BaseCuiPanel().getLayout());
    }
}
