package com.icw.ehf.cui.core.api.components.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;

import org.junit.jupiter.api.Test;

import lombok.val;

class DisableUIComponentStrategyTest {

    @Test
    void shouldFailOnMissingComponent() {
        assertThrows(NullPointerException.class, () -> {
            DisableUIComponentStrategy.disableComponent(null);
        });
    }

    @Test
    void shouldFailOnMissingComponentStrategy() {
        val component = new HtmlForm();
        assertThrows(IllegalArgumentException.class, () -> {
            DisableUIComponentStrategy.disableComponent(component);
        });
    }

    @Test
    void shouldSupportToDisableHtmlInput() {
        val component = new HtmlInputText();
        assertFalse(component.isDisabled(), "wrong state of initialized component. ");
        DisableUIComponentStrategy.disableComponent(component);
        assertTrue(component.isDisabled(), "disable state is wrong. ");
    }

    @Test
    void shouldSupportToDisableHtmlSelectOneMenu() {
        val component = new HtmlSelectOneMenu();
        assertFalse(component.isDisabled(), "wrong state of initialized component. ");
        DisableUIComponentStrategy.disableComponent(component);
        assertTrue(component.isDisabled(), "disable state is wrong. ");
    }
}
