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

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.mocks.ReverseConverter;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"labelKey", "labelValue", "labelEscape", "labelConverter"})
class LabelProviderImplTest extends AbstractPartialComponentTest {

    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new LabelProvider(null));
    }

    @Test
    void shouldResolveNullForNoLabelSet() {
        assertNull(anyComponent().resolveLabel());
    }

    @Test
    void shouldResolveLabelValue() {
        var any = anyComponent();
        any.setLabelValue(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, any.resolveLabel());
    }

    @Test
    void shouldResolveLabelKey() {
        var any = anyComponent();
        any.setLabelKey(MESSAGE_KEY);
        assertEquals(MESSAGE_VALUE, any.resolveLabel());
    }

    @Test
    void shouldUseConverterAsId() {
        getComponentConfigDecorator().registerConverter(ReverseConverter.class);
        var any = anyComponent();
        any.setLabelConverter(ReverseConverter.CONVERTER_ID);
        any.setLabelValue("test");
        assertEquals("tset", any.resolveLabel());
    }

    @Test
    void shouldUseConverterAsConverter() {
        var any = anyComponent();
        any.setLabelConverter(new ReverseConverter());
        any.setLabelValue("test");
        assertEquals("tset", any.resolveLabel());
    }

    @Test
    void shouldDefaultToLabelEscape() {
        assertTrue(anyComponent().isLabelEscape());
    }

    @Test
    void shouldResolveLabelEscape() {
        var any = anyComponent();
        any.setLabelEscape(false);
        assertFalse(any.isLabelEscape());
    }
}
