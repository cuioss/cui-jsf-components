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

import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.mocks.ReverseConverter;

@VerifyComponentProperties(of = { "closeButtonTitleKey", "closeButtonTitleValue", "closeButtonTitleConverter" })
class CloseButtonTitleProviderImplTest extends AbstractPartialComponentTest {

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new CloseButtonTitleProvider(null));
    }

    @Test
    void shouldResolveNullForNoTitleSet() {
        assertNull(anyComponent().resolveCloseButtonTitle());
    }

    @Test
    void shouldresolveCloseButtonTitleValue() {
        final var any = anyComponent();
        any.setCloseButtonTitleValue(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveCloseButtonTitle());
    }

    @Test
    void shouldresolveCloseButtonTitleKey() {
        final var any = anyComponent();
        any.setCloseButtonTitleKey(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveCloseButtonTitle());
    }

    @Test
    void shouldUseConverterAsId() {
        getComponentConfigDecorator().registerConverter(ReverseConverter.class);
        final var any = anyComponent();
        any.setCloseButtonTitleConverter(ReverseConverter.CONVERTER_ID);
        any.setCloseButtonTitleValue("test");
        assertEquals("tset", any.resolveCloseButtonTitle());
    }

    @Test
    void shouldUseConverterAsConverter() {
        final var any = anyComponent();
        any.setCloseButtonTitleConverter(new ReverseConverter());
        any.setCloseButtonTitleValue("test");
        assertEquals("tset", any.resolveCloseButtonTitle());
    }

    @Test
    void shouldDetectTitleAvailabilty() {
        final var any = anyComponent();
        assertFalse(any.isCloseButtonTitleSet());
        final var strings = letterStrings(1, 10);
        any.setCloseButtonTitleKey(strings.next());
        assertTrue(any.isCloseButtonTitleSet());
        any.setCloseButtonTitleValue(strings.next());
        assertTrue(any.isCloseButtonTitleSet());
        any.setCloseButtonTitleKey(null);
        assertTrue(any.isCloseButtonTitleSet());
    }
}
