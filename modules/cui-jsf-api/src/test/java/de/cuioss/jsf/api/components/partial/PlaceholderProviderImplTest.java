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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.mocks.ReverseConverter;

@VerifyComponentProperties(of = { "placeholderKey", "placeholderValue", "placeholderConverter" })
class PlaceholderProviderImplTest extends AbstractPartialComponentTest {

    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new PlaceholderProvider(null));
    }

    @Test
    void shouldResolveNullForNoTitleSet() {
        assertNull(anyComponent().resolveTitle());
    }

    @Test
    void shouldResolvePlaceholderValue() {
        var any = anyComponent();
        any.setPlaceholderValue(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolvePlaceholder());
    }

    @Test
    void shouldResolveTitleKey() {
        var any = anyComponent();
        any.setPlaceholderKey(MESSAGE_KEY);
        assertEquals(MESSAGE_VALUE, any.resolvePlaceholder());
    }

    @Test
    void shouldUseConverterAsId() {
        getComponentConfigDecorator().registerConverter(ReverseConverter.class);
        var any = anyComponent();
        any.setPlaceholderConverter(ReverseConverter.CONVERTER_ID);
        any.setPlaceholderValue("test");
        assertEquals("tset", any.resolvePlaceholder());
    }

    @Test
    void shouldUseConverterAsConverter() {
        var any = anyComponent();
        any.setPlaceholderConverter(new ReverseConverter());
        any.setPlaceholderValue("test");
        assertEquals("tset", any.resolvePlaceholder());
    }

    /**
     * Tests adding and removing of passthrough attributes emulating an ui:repeat
     * scenario.
     */
    @Test
    void shouldRemoveIfNull() {
        var pt = anyComponent();
        UIComponent other = JsfHtmlComponent.INPUT.component(getFacesContext());
        pt.setPlaceholderValue("test");
        pt.setPlaceholder(other, getFacesContext(), pt);
        assertEquals("test", other.getPassThroughAttributes().get(PlaceholderProvider.PT_PLACEHOLDER));
        pt.setPlaceholderValue(null);
        pt.setPlaceholder(other, getFacesContext(), pt);
        assertNull(other.getPassThroughAttributes().get(PlaceholderProvider.PT_PLACEHOLDER));
    }
}
