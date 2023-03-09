package com.icw.ehf.cui.core.api.components.util.modifier;

import javax.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;

class GenericUiInputComponentWrapperTest {

    @Test
    void shouldHandleComponentWrapperContract() {
        var component = new HtmlInputText();
        var wrapper = new GenericUiInputComponentWrapper(component);
        ComponentModifierAssert.assertContracts(wrapper, component);
    }
}
