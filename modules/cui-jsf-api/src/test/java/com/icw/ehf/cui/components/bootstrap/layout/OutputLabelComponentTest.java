package com.icw.ehf.cui.components.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "labelKey", "labelValue", "labelEscape", "titleKey", "titleValue", "for" })
class OutputLabelComponentTest extends AbstractUiComponentTest<OutputLabelComponent> {

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals("javax.faces.Output", anyComponent().getFamily());
    }
}
