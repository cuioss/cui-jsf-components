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

@VerifyComponentProperties(of = {"footerKey", "footerValue", "footerConverter", "footerEscape"})
@ExplicitParamInjection
@DisplayName("Tests for FooterProvider implementation")
class FooterProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new FooterProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for footer resolution")
    class FooterResolutionTests {

        @Test
        @DisplayName("Should resolve null when no footer is set")
        void shouldResolveNullForNoFooterSet() {
            // Act & Assert
            assertNull(anyComponent().resolveFooter(),
                    "Footer should be null when no footer is set");
        }

        @Test
        @DisplayName("Should resolve footer value when set directly")
        void shouldResolveFooterValue() {
            // Arrange
            final var any = anyComponent();

            // Act
            any.setFooterValue(MESSAGE_VALUE);

            // Assert
            assertEquals(MESSAGE_VALUE, any.resolveFooter(),
                    "Should return the directly set footer value");
        }

        @Test
        @DisplayName("Should resolve footer from resource bundle when key is set")
        void shouldResolveFooterKey() {
            // Arrange
            final var any = anyComponent();

            // Act
            any.setFooterKey(MESSAGE_KEY);

            // Assert
            assertEquals(MESSAGE_VALUE, any.resolveFooter(),
                    "Should resolve footer from resource bundle using the key");
        }
    }

    @Nested
    @DisplayName("Tests for footer conversion")
    class FooterConversionTests {

        @Test
        @DisplayName("Should use converter by ID when registered in application")
        void shouldUseConverterAsId(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerConverter(ReverseConverter.class);
            final var any = anyComponent();

            // Act
            any.setFooterConverter(ReverseConverter.CONVERTER_ID);
            any.setFooterValue("test");

            // Assert
            assertEquals("tset", any.resolveFooter(),
                    "Footer should be converted using the registered converter");
        }

        @Test
        @DisplayName("Should use converter instance when set directly")
        void shouldUseConverterAsConverter() {
            // Arrange
            final var any = anyComponent();

            // Act
            any.setFooterConverter(new ReverseConverter());
            any.setFooterValue("test");

            // Assert
            assertEquals("tset", any.resolveFooter(),
                    "Footer should be converted using the direct converter instance");
        }
    }

    @Nested
    @DisplayName("Tests for footer availability detection")
    class FooterAvailabilityTests {

        @Test
        @DisplayName("Should correctly detect whether footer is set")
        void shouldDetectFooterAvailability() {
            // Arrange
            final var any = anyComponent();
            final var strings = letterStrings(1, 10);

            // Assert - initial state
            assertFalse(any.hasFooterTitleSet(),
                    "Footer should not be set initially");

            // Act - set footer key
            any.setFooterKey(strings.next());

            // Assert - after setting key
            assertTrue(any.hasFooterTitleSet(),
                    "Footer should be detected as set after setting key");

            // Act - set footer value
            any.setFooterValue(strings.next());

            // Assert - after setting value
            assertTrue(any.hasFooterTitleSet(),
                    "Footer should be detected as set after setting value");

            // Act - clear key but keep value
            any.setFooterKey(null);

            // Assert - after clearing key
            assertTrue(any.hasFooterTitleSet(),
                    "Footer should still be detected as set when value remains");
        }
    }

    @Nested
    @DisplayName("Tests for footer facet handling")
    class FooterFacetTests {

        @Test
        @DisplayName("Should detect and handle footer facet correctly")
        void shouldHandleFooterFacet() {
            // Arrange
            final var any = anyComponent();

            // Assert - initial state
            assertFalse(any.shouldRenderFooterFacet(),
                    "Should not render footer facet when none exists");

            // Act - add footer facet
            any.getFacets().put("footer", anyComponent());

            // Assert - after adding facet
            assertTrue(any.shouldRenderFooterFacet(),
                    "Should render footer facet when it exists and is rendered");
        }

        @Test
        @DisplayName("Should determine footer rendering based on content and facets")
        void shouldDetermineFooterRendering() {
            // Arrange
            final var any = anyComponent();

            // Assert - initial state
            assertFalse(any.shouldRenderFooter(),
                    "Should not render footer when no facet or key/value is present");

            // Act - add footer facet
            any.getFacets().put("footer", anyComponent());

            // Assert - after adding facet
            assertTrue(any.shouldRenderFooter(),
                    "Should render footer when facet is present and rendered");

            // Act - set facet to not rendered
            any.getFacet("footer").setRendered(false);

            // Assert - after setting facet to not rendered
            assertFalse(any.shouldRenderFooter(),
                    "Should not render footer when facet is not rendered");

            // Act - clear facets and set footer key
            any.getFacets().clear();
            any.setFooterKey("Foo");

            // Assert - after setting footer key
            assertTrue(any.shouldRenderFooter(),
                    "Should render footer when footer key is set");
        }
    }
}
