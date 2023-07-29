package de.cuioss.jsf.bootstrap.icon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "labelKey", "labelValue", "labelEscape", "iconAlign", "icon", "titleKey",
        "titleValue" })
class LabeledIconComponentTest extends AbstractUiComponentTest<LabeledIconComponent> {

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.LABELED_ICON_COMPONENT_RENDERER, anyComponent().getRendererType());
    }
}
