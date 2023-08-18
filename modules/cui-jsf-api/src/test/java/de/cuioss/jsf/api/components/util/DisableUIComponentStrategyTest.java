/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.components.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;

import org.junit.jupiter.api.Test;

class DisableUIComponentStrategyTest {

    @Test
    void shouldFailOnMissingComponent() {
        assertThrows(NullPointerException.class, () -> DisableUIComponentStrategy.disableComponent(null));
    }

    @Test
    void shouldFailOnMissingComponentStrategy() {
        final var component = new HtmlForm();
        assertThrows(IllegalArgumentException.class, () -> DisableUIComponentStrategy.disableComponent(component));
    }

    @Test
    void shouldSupportToDisableHtmlInput() {
        final var component = new HtmlInputText();
        assertFalse(component.isDisabled(), "wrong state of initialized component. ");
        DisableUIComponentStrategy.disableComponent(component);
        assertTrue(component.isDisabled(), "disable state is wrong. ");
    }

    @Test
    void shouldSupportToDisableHtmlSelectOneMenu() {
        final var component = new HtmlSelectOneMenu();
        assertFalse(component.isDisabled(), "wrong state of initialized component. ");
        DisableUIComponentStrategy.disableComponent(component);
        assertTrue(component.isDisabled(), "disable state is wrong. ");
    }
}
