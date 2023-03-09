package com.icw.ehf.cui.core.api.components.util.modifier;

import static com.icw.ehf.cui.core.api.components.util.modifier.ComponentModifierAssert.assertContracts;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;

class ReflectionBasedModifierTest {

    @Test
    void shouldHandleUIComponentBase() {
        UIComponentBase component = new UIComponentBase() {

            @Override
            public String getFamily() {
                return null;
            }
        };
        var modifer = new ReflectionBasedModifier(component);
        assertContracts(modifer, component);
        assertFalse(modifer.isSupportsDisabled());
        assertFalse(modifer.isSupportsLabel());
        assertFalse(modifer.isSupportsRole());
        assertFalse(modifer.isSupportsStyle());
        assertFalse(modifer.isSupportsStyleClass());
        assertFalse(modifer.isSupportsTitle());
        assertFalse(modifer.isEditableValueHolder());
        assertFalse(modifer.isSupportsResetValue());
    }

    @Test
    void shouldHandleHtmlInput() {
        UIComponent component = new HtmlInputText();
        var modifer = new ReflectionBasedModifier(component);
        assertContracts(modifer, component);
        assertTrue(modifer.isSupportsDisabled());
        assertTrue(modifer.isSupportsLabel());
        assertTrue(modifer.isSupportsRole());
        assertTrue(modifer.isSupportsStyle());
        assertTrue(modifer.isSupportsStyleClass());
        assertTrue(modifer.isSupportsTitle());
        assertTrue(modifer.isEditableValueHolder());
        assertTrue(modifer.isSupportsResetValue());
    }
}
