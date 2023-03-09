package com.icw.ehf.cui.core.api.components.util.modifier;

import static com.icw.ehf.cui.core.api.components.util.modifier.ComponentModifierAssert.assertContracts;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.components.util.modifier.support.StyleClassProvider;
import com.icw.ehf.cui.core.api.components.util.modifier.support.StyleProvider;
import com.icw.ehf.cui.core.api.components.util.modifier.support.TitleProviderImpl;

class CuiInterfaceBasedModifierTest {

    @Test
    void shouldHandleTitleProvider() {
        UIComponent component = new TitleProviderImpl();
        var modifer = CuiInterfaceBasedModifier.wrap(component).get();
        assertContracts(modifer, component);
        assertFalse(modifer.isSupportsDisabled());
        assertFalse(modifer.isSupportsLabel());
        assertFalse(modifer.isSupportsRole());
        assertFalse(modifer.isSupportsStyle());
        assertFalse(modifer.isSupportsStyleClass());
        assertTrue(modifer.isSupportsTitle());
    }

    @Test
    void shouldHandleStyleProvider() {
        UIComponent component = new StyleProvider();
        var modifer = CuiInterfaceBasedModifier.wrap(component).get();
        assertContracts(modifer, component);
        assertFalse(modifer.isSupportsDisabled());
        assertFalse(modifer.isSupportsLabel());
        assertFalse(modifer.isSupportsRole());
        assertTrue(modifer.isSupportsStyle());
        assertFalse(modifer.isSupportsStyleClass());
        assertFalse(modifer.isSupportsTitle());
    }

    @Test
    void shouldHandleStyleClassProvider() {
        UIComponent component = new StyleClassProvider();
        var modifer = CuiInterfaceBasedModifier.wrap(component).get();
        assertContracts(modifer, component);
        assertFalse(modifer.isSupportsDisabled());
        assertFalse(modifer.isSupportsLabel());
        assertFalse(modifer.isSupportsRole());
        assertFalse(modifer.isSupportsStyle());
        assertTrue(modifer.isSupportsStyleClass());
        assertFalse(modifer.isSupportsTitle());
    }

    @Test
    void shouldIgnoreNonCuiComponent() {
        assertFalse(CuiInterfaceBasedModifier.wrap(new HtmlInputText()).isPresent());
    }
}
