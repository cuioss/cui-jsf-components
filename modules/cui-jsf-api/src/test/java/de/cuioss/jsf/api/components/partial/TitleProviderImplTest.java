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

@VerifyComponentProperties(of = { "titleKey", "titleValue", "titleConverter" })
class TitleProviderImplTest extends AbstractPartialComponentTest {

    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new TitleProviderImpl(null));
    }

    @Test
    void shouldResolveNullForNoTitleSet() {
        assertNull(anyComponent().resolveTitle());
    }

    @Test
    void shouldResolveTitleValue() {
        final var any = anyComponent();
        any.setTitleValue(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveTitle());
    }

    @Test
    void shouldResolveTitleKey() {
        final var any = anyComponent();
        any.setTitleKey(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveTitle());
    }

    @Test
    void shouldUseConverterAsId() {
        getComponentConfigDecorator().registerConverter(ReverseConverter.class);
        final var any = anyComponent();
        any.setTitleConverter(ReverseConverter.CONVERTER_ID);
        any.setTitleValue("test");
        assertEquals("tset", any.resolveTitle());
    }

    @Test
    void shouldUseConverterAsConverter() {
        final var any = anyComponent();
        any.setTitleConverter(new ReverseConverter());
        any.setTitleValue("test");
        assertEquals("tset", any.resolveTitle());
    }

    @Test
    void shouldDetectTitleAvailabilty() {
        final var any = anyComponent();
        assertFalse(any.isTitleSet());
        final var strings = letterStrings(1, 10);
        any.setTitleKey(strings.next());
        assertTrue(any.isTitleSet());
        any.setTitleValue(strings.next());
        assertTrue(any.isTitleSet());
        any.setTitleKey(null);
        assertTrue(any.isTitleSet());
    }
}
