package com.icw.ehf.cui.core.api.components.util.styleclass;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.bootstrap.composite.EditableDataListComponent;

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
