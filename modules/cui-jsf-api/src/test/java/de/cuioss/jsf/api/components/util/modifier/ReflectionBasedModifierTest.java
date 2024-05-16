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

import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.component.html.HtmlInputText;
import org.junit.jupiter.api.Test;

import static de.cuioss.jsf.api.components.util.modifier.ComponentModifierAssert.assertContracts;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnableJsfEnvironment
class ReflectionBasedModifierTest {

    @Test
    void shouldHandleUIComponentBase() {
        UIComponentBase component = new UIComponentBase() {

            @Override
            public String getFamily() {
                return null;
            }
        };
        var modifier = new ReflectionBasedModifier(component);
        assertContracts(modifier, component);
        assertFalse(modifier.isSupportsDisabled());
        assertFalse(modifier.isSupportsLabel());
        assertFalse(modifier.isSupportsRole());
        assertFalse(modifier.isSupportsStyle());
        assertFalse(modifier.isSupportsStyleClass());
        assertFalse(modifier.isSupportsTitle());
        assertFalse(modifier.isEditableValueHolder());
        assertFalse(modifier.isSupportsResetValue());
    }

    @Test
    void shouldHandleHtmlInput() {
        UIComponent component = new HtmlInputText();
        var modifier = new ReflectionBasedModifier(component);
        assertContracts(modifier, component);
        assertTrue(modifier.isSupportsDisabled());
        assertTrue(modifier.isSupportsLabel());
        assertTrue(modifier.isSupportsRole());
        assertTrue(modifier.isSupportsStyle());
        assertTrue(modifier.isSupportsStyleClass());
        assertTrue(modifier.isSupportsTitle());
        assertTrue(modifier.isEditableValueHolder());
        assertTrue(modifier.isSupportsResetValue());
    }
}
