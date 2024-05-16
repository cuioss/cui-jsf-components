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
package de.cuioss.jsf.api.components.util.modifier;

import static de.cuioss.jsf.api.components.util.modifier.ComponentModifierAssert.assertContracts;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.util.modifier.support.StyleClassProvider;
import de.cuioss.jsf.api.components.util.modifier.support.StyleProvider;
import de.cuioss.jsf.api.components.util.modifier.support.TitleProviderImpl;

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
