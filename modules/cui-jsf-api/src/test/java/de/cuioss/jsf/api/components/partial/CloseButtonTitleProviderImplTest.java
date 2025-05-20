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

@VerifyComponentProperties(of = {"closeButtonTitleKey", "closeButtonTitleValue", "closeButtonTitleConverter"})
@ExplicitParamInjection
@DisplayName("Tests for CloseButtonTitleProvider implementation")
class CloseButtonTitleProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new CloseButtonTitleProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for title resolution")
    class TitleResolutionTests {

        @Test
        @DisplayName("Should resolve null when no title is set")
        void shouldResolveNullForNoTitleSet() {
            // Act & Assert
            assertNull(anyComponent().resolveCloseButtonTitle(),
                    "Title should be null when no title is set");
        }

        @Test
        @DisplayName("Should resolve title value when set directly")
        void shouldResolveCloseButtonTitleValue() {
            // Arrange
            final var any = anyComponent();

            // Act
            any.setCloseButtonTitleValue(MESSAGE_KEY);

            // Assert
            assertEquals(MESSAGE_KEY, any.resolveCloseButtonTitle(),
                    "Should return the directly set title value");
        }

        @Test
        @DisplayName("Should resolve title from resource bundle when key is set")
        void shouldResolveCloseButtonTitleKey() {
            // Arrange
            final var any = anyComponent();

            // Act
            any.setCloseButtonTitleKey(MESSAGE_KEY);

            // Assert
            assertEquals(MESSAGE_VALUE, any.resolveCloseButtonTitle(),
                    "Should resolve title from resource bundle using the key");
        }
    }

    @Nested
    @DisplayName("Tests for title conversion")
    class TitleConversionTests {

        @Test
        @DisplayName("Should use converter by ID when registered in application")
        void shouldUseConverterAsId(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerConverter(ReverseConverter.class);
            final var any = anyComponent();

            // Act
            any.setCloseButtonTitleConverter(ReverseConverter.CONVERTER_ID);
            any.setCloseButtonTitleValue("test");

            // Assert
            assertEquals("tset", any.resolveCloseButtonTitle(),
                    "Title should be converted using the registered converter");
        }

        @Test
        @DisplayName("Should use converter instance when set directly")
        void shouldUseConverterAsConverter() {
            // Arrange
            final var any = anyComponent();

            // Act
            any.setCloseButtonTitleConverter(new ReverseConverter());
            any.setCloseButtonTitleValue("test");

            // Assert
            assertEquals("tset", any.resolveCloseButtonTitle(),
                    "Title should be converted using the direct converter instance");
        }
    }

    @Nested
    @DisplayName("Tests for title availability detection")
    class TitleAvailabilityTests {

        @Test
        @DisplayName("Should correctly detect whether title is set")
        void shouldDetectTitleAvailability() {
            // Arrange
            final var any = anyComponent();
            final var strings = letterStrings(1, 10);

            // Assert - initial state
            assertFalse(any.isCloseButtonTitleSet(),
                    "Title should not be set initially");

            // Act - set title key
            any.setCloseButtonTitleKey(strings.next());

            // Assert - after setting key
            assertTrue(any.isCloseButtonTitleSet(),
                    "Title should be detected as set after setting key");

            // Act - set title value
            any.setCloseButtonTitleValue(strings.next());

            // Assert - after setting value
            assertTrue(any.isCloseButtonTitleSet(),
                    "Title should be detected as set after setting value");

            // Act - clear key but keep value
            any.setCloseButtonTitleKey(null);

            // Assert - after clearing key
            assertTrue(any.isCloseButtonTitleSet(),
                    "Title should still be detected as set when value remains");
        }
    }
}
