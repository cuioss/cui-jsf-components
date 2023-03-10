package de.cuioss.jsf.api.components.util.styleclass;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.composite.EditableDataListComponent;

class StyleClassModifierFactoryTest {

    @Test
    void shouldHandleInputText() {
        assertNotNull(StyleClassModifierFactory.findFittingWrapper(new HtmlInputText()));
    }

    @Test
    void shouldHandleEditableDataList() {
        assertNotNull(StyleClassModifierFactory.findFittingWrapper(new EditableDataListComponent()));
    }
}
