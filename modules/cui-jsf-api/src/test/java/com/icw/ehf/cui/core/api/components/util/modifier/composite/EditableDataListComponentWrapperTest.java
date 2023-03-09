package com.icw.ehf.cui.core.api.components.util.modifier.composite;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.bootstrap.composite.EditableDataListComponent;
import com.icw.ehf.cui.core.api.components.util.modifier.ComponentModifierAssert;

class EditableDataListComponentWrapperTest {

    @Test
    void shouldHandleEditableDataList() {
        var wrapper = CuiCompositeWrapperFactory.wrap(new EditableDataListComponent());
        assertTrue(wrapper.isPresent());
        ComponentModifierAssert.assertContracts(wrapper.get(), wrapper.get().getComponent());
    }

    @Test
    void shouldIgnoreInvalidComponent() {
        assertFalse(CuiCompositeWrapperFactory.wrap(new HtmlInputText()).isPresent());
    }
}
