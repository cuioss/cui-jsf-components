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
package de.cuioss.jsf.api.components.partial;

import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@VerifyComponentProperties(of = "keyBinding")
class KeyBindingProviderTest extends AbstractPartialComponentTest {

    public static final String BINDING_VALUE = "binding-value";

    @Test
    void shouldWriteThroughPassThrough() {
        var any = anyComponent();
        assertFalse(any.getPassThroughAttributes(true).containsKey(AttributeValue.CUI_CLICK_BINDING.getContent()));
        any.setKeyBinding(BINDING_VALUE);
        any.writeBindingToPassThroughAttributes(any);
        assertTrue(any.getPassThroughAttributes(true).containsKey(AttributeValue.CUI_CLICK_BINDING.getContent()));
        assertEquals(BINDING_VALUE, any.getPassThroughAttributes().get(AttributeValue.CUI_CLICK_BINDING.getContent()));
    }

    @Test
    void shouldHandleMissingThroughPassThrough() {
        var any = anyComponent();
        assertFalse(any.getPassThroughAttributes(true).containsKey(AttributeValue.CUI_CLICK_BINDING.getContent()));
        any.writeBindingToPassThroughAttributes(any);
        assertFalse(any.getPassThroughAttributes(true).containsKey(AttributeValue.CUI_CLICK_BINDING.getContent()));
    }
}
