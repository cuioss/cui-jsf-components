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
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.mocks.ReverseConverter;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"headerKey", "headerValue", "headerConverter", "headerEscape", "headerTag"})
class HeaderProviderImplTest extends AbstractPartialComponentTest {

    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new HeaderProvider(null));
    }

    @Test
    void shouldResolveNullForNoTitleSet() {
        assertNull(anyComponent().resolveHeader());
    }

    @Test
    void shouldResolveHeaderValue() {
        final var any = anyComponent();
        any.setHeaderValue(MESSAGE_VALUE);
        assertEquals(MESSAGE_VALUE, any.resolveHeader());
    }

    @Test
    void shouldResolveHeaderKey() {
        final var any = anyComponent();
        any.setHeaderKey(MESSAGE_KEY);
        assertEquals(MESSAGE_VALUE, any.resolveHeader());
    }

    @Test
    void shouldUseConverterAsId() {
        getComponentConfigDecorator().registerConverter(ReverseConverter.class);
        final var any = anyComponent();
        any.setHeaderConverter(ReverseConverter.CONVERTER_ID);
        any.setHeaderValue("test");
        assertEquals("tset", any.resolveHeader());
    }

    @Test
    void shouldUseConverterAsConverter() {
        final var any = anyComponent();
        any.setHeaderConverter(new ReverseConverter());
        any.setHeaderValue("test");
        assertEquals("tset", any.resolveHeader());
    }

    @Test
    void shouldDetectTitleAvailabilty() {
        final var any = anyComponent();
        assertFalse(any.hasHeaderTitleSet());
        final var strings = letterStrings(1, 10);
        any.setHeaderKey(strings.next());
        assertTrue(any.hasHeaderTitleSet());
        any.setHeaderValue(strings.next());
        assertTrue(any.hasHeaderTitleSet());
        any.setHeaderKey(null);
        assertTrue(any.hasHeaderTitleSet());
    }

    @Test
    void shouldUseHeaderTag() {
        final var any = anyComponent();
        assertEquals(HeaderProvider.HEADERTAG_DEFAULT, any.getHeaderTag());
        any.setHeaderTag("h2");
        assertEquals("h2", any.getHeaderTag());
    }

    @Test
    void shouldRenderHeaderFacet() {
        final var any = anyComponent();
        assertFalse(any.shouldRenderHeaderFacet());
        any.getFacets().put("header", anyComponent());
        assertTrue(any.shouldRenderHeaderFacet());
    }

    @Test
    void shouldRenderHeader() {
        final var any = anyComponent();
        // false, because no facet or key/value is present
        assertFalse(any.shouldRenderHeader());
        any.getFacets().put("header", anyComponent());
        assertTrue(any.shouldRenderHeader());
        any.getFacet("header").setRendered(false);
        // false because facet is not rendered
        assertFalse(any.shouldRenderHeader());
        any.getFacets().clear();
        any.setHeaderKey("Foo");
        assertTrue(any.shouldRenderHeader());
    }
}
