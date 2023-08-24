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
