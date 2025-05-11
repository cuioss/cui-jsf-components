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
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.ReverseConverter;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"headerKey", "headerValue", "headerConverter", "headerEscape", "headerTag"})
@ExplicitParamInjection
@DisplayName("Tests for HeaderProvider implementation")
class HeaderProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new HeaderProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for header resolution")
    class HeaderResolutionTests {

        @Test
        @DisplayName("Should resolve null when no header is set")
        void shouldResolveNullForNoHeaderSet() {
            // Act & Assert
            assertNull(anyComponent().resolveHeader(),
                    "Header should be null when no header is set");
        }

        @Test
        @DisplayName("Should resolve header value when set directly")
        void shouldResolveHeaderValue() {
            // Arrange
            final var any = anyComponent();

            // Act
            any.setHeaderValue(MESSAGE_VALUE);

            // Assert
            assertEquals(MESSAGE_VALUE, any.resolveHeader(),
                    "Should return the directly set header value");
        }

        @Test
        @DisplayName("Should resolve header from resource bundle when key is set")
        void shouldResolveHeaderKey() {
            // Arrange
            final var any = anyComponent();

            // Act
            any.setHeaderKey(MESSAGE_KEY);

            // Assert
            assertEquals(MESSAGE_VALUE, any.resolveHeader(),
                    "Should resolve header from resource bundle using the key");
        }
    }

    @Nested
    @DisplayName("Tests for header conversion")
    class HeaderConversionTests {

        @Test
        @DisplayName("Should use converter by ID when registered in application")
        void shouldUseConverterAsId(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerConverter(ReverseConverter.class);
            final var any = anyComponent();

            // Act
            any.setHeaderConverter(ReverseConverter.CONVERTER_ID);
            any.setHeaderValue("test");

            // Assert
            assertEquals("tset", any.resolveHeader(),
                    "Header should be converted using the registered converter");
        }

        @Test
        @DisplayName("Should use converter instance when set directly")
        void shouldUseConverterAsConverter() {
            // Arrange
            final var any = anyComponent();

            // Act
            any.setHeaderConverter(new ReverseConverter());
            any.setHeaderValue("test");

            // Assert
            assertEquals("tset", any.resolveHeader(),
                    "Header should be converted using the direct converter instance");
        }
    }

    @Nested
    @DisplayName("Tests for header availability detection")
    class HeaderAvailabilityTests {

        @Test
        @DisplayName("Should correctly detect whether header is set")
        void shouldDetectHeaderAvailability() {
            // Arrange
            final var any = anyComponent();
            final var strings = letterStrings(1, 10);

            // Assert - initial state
            assertFalse(any.hasHeaderTitleSet(),
                    "Header should not be set initially");

            // Act - set header key
            any.setHeaderKey(strings.next());

            // Assert - after setting key
            assertTrue(any.hasHeaderTitleSet(),
                    "Header should be detected as set after setting key");

            // Act - set header value
            any.setHeaderValue(strings.next());

            // Assert - after setting value
            assertTrue(any.hasHeaderTitleSet(),
                    "Header should be detected as set after setting value");

            // Act - clear key but keep value
            any.setHeaderKey(null);

            // Assert - after clearing key
            assertTrue(any.hasHeaderTitleSet(),
                    "Header should still be detected as set when value remains");
        }
    }

    @Nested
    @DisplayName("Tests for header tag handling")
    class HeaderTagTests {

        @Test
        @DisplayName("Should use default header tag and allow overriding")
        void shouldHandleHeaderTag() {
            // Arrange
            final var any = anyComponent();

            // Assert - initial state
            assertEquals(HeaderProvider.HEADERTAG_DEFAULT, any.getHeaderTag(),
                    "Should use default header tag initially");

            // Act - set custom header tag
            any.setHeaderTag("h2");

            // Assert - after setting custom tag
            assertEquals("h2", any.getHeaderTag(),
                    "Should use the custom header tag when set");
        }
    }

    @Nested
    @DisplayName("Tests for header facet handling")
    class HeaderFacetTests {

        @Test
        @DisplayName("Should detect and handle header facet correctly")
        void shouldHandleHeaderFacet() {
            // Arrange
            final var any = anyComponent();

            // Assert - initial state
            assertFalse(any.shouldRenderHeaderFacet(),
                    "Should not render header facet when none exists");

            // Act - add header facet
            any.getFacets().put("header", anyComponent());

            // Assert - after adding facet
            assertTrue(any.shouldRenderHeaderFacet(),
                    "Should render header facet when it exists and is rendered");
        }

        @Test
        @DisplayName("Should determine header rendering based on content and facets")
        void shouldDetermineHeaderRendering() {
            // Arrange
            final var any = anyComponent();

            // Assert - initial state
            assertFalse(any.shouldRenderHeader(),
                    "Should not render header when no facet or key/value is present");

            // Act - add header facet
            any.getFacets().put("header", anyComponent());

            // Assert - after adding facet
            assertTrue(any.shouldRenderHeader(),
                    "Should render header when facet is present and rendered");

            // Act - set facet to not rendered
            any.getFacet("header").setRendered(false);

            // Assert - after setting facet to not rendered
            assertFalse(any.shouldRenderHeader(),
                    "Should not render header when facet is not rendered");

            // Act - clear facets and set header key
            any.getFacets().clear();
            any.setHeaderKey("Foo");

            // Assert - after setting header key
            assertTrue(any.shouldRenderHeader(),
                    "Should render header when header key is set");
        }
    }
}
