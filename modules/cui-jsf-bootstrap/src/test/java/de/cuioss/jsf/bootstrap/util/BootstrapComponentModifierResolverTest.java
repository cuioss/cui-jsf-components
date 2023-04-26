package de.cuioss.jsf.bootstrap.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import javax.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.util.modifier.ComponentModifierFactory;
import de.cuioss.jsf.bootstrap.composite.EditableDataListComponent;

class BootstrapComponentModifierResolverTest {

    @Test
    void shouldHandleEditableDataList() {
        var wrapper = ComponentModifierFactory.findFittingWrapper(new EditableDataListComponent());
        assertInstanceOf(EditableDataListComponentWrapper.class, wrapper);
        ComponentModifierAssert.assertContracts(wrapper, wrapper.getComponent());
    }

    @Test
    void shouldIgnoreInvalidComponent() {
        assertFalse(new BootstrapComponentModifierResolver().wrap(new HtmlInputText()).isPresent());
    }

}
