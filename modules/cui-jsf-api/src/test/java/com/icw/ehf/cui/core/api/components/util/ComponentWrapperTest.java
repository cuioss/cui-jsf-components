package com.icw.ehf.cui.core.api.components.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class ComponentWrapperTest extends JsfEnabledTestEnvironment implements ComponentConfigurator {

    private static final String ID = "id";

    private static final String EXTENSION = "extension";

    private static final String EXTENSION_SUFFIX = "_" + EXTENSION;

    @Test
    void shouldHandleHtmlInputText() {
        var wrapper = new ComponentWrapper<>(new HtmlInputText());
        assertTrue(wrapper.isClientBehaviorHolder());
        // Should be cached now.
        assertTrue(wrapper.isClientBehaviorHolder());
        assertTrue(wrapper.getClientBehaviors().isEmpty());
        // Should be cached now.
        wrapper.getWrapped().addClientBehavior("click", new AjaxBehavior());
        assertTrue(wrapper.getClientBehaviors().isEmpty());
        // No Id set and no ClientBehaviors but is of type UIInput, therefore
        // clientId needs to be rendered
        assertFalse(wrapper.shouldRenderClientId());
    }

    @Test
    void shouldHandleHtmlOutputText() {
        var wrapper = new ComponentWrapper<>(new HtmlOutputText());
        assertFalse(wrapper.isClientBehaviorHolder());
        // No Id set and not of type ClientBehaviorHolder, therefore no
        // clientId needs to be rendered
        assertFalse(wrapper.shouldRenderClientId());
        wrapper.getWrapped().setId(UIViewRoot.UNIQUE_ID_PREFIX);
        assertFalse(wrapper.shouldRenderClientId());
        wrapper.getWrapped().setId(ID);
        assertTrue(wrapper.shouldRenderClientId());
    }

    @Test
    void shouldSuffixClientId() {
        var wrapper = new ComponentWrapper<>(new HtmlOutputText());
        assertFalse(wrapper.isClientBehaviorHolder());
        wrapper.getWrapped().setId(ID);
        assertTrue(wrapper.shouldRenderClientId());
        assertEquals(ID + EXTENSION_SUFFIX, wrapper.getSuffixedClientId(EXTENSION));
        assertEquals(ID, wrapper.getSuffixedClientId(null));
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerMockRendererForHtmlOutputText();
    }
}
