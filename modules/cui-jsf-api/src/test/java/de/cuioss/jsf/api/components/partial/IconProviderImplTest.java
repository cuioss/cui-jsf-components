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
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = "icon")
@ExplicitParamInjection
@DisplayName("Tests for IconProvider implementation")
class IconProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new IconProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for icon resolution")
    class IconResolutionTests {

        @Test
        @DisplayName("Should use fallback icon when no icon is set")
        void shouldFallbackOnNoIconSet() {
            // Act & Assert
            assertEquals(IconProvider.FALLBACK_ICON_STRING, anyComponent().resolveIconCss(),
                    "Should return fallback icon when no icon is set");
        }

        @Test
        @DisplayName("Should use fallback icon when invalid icon is set")
        void shouldFallbackOnInvalidIconSet() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setIcon(MESSAGE_KEY);

            // Assert
            assertEquals(IconProvider.FALLBACK_ICON_STRING, any.resolveIconCss(),
                    "Should return fallback icon when invalid icon is set");
        }

        @Test
        @DisplayName("Should resolve valid icon with proper CSS classes")
        void shouldResolveValidIcon() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setIcon("cui-icon-alarm");

            // Assert
            assertEquals("cui-icon cui-icon-alarm", any.resolveIconCss(),
                    "Should return properly formatted icon CSS classes");
        }
    }

    @Nested
    @DisplayName("Tests for icon state detection")
    class IconStateTests {

        @Test
        @DisplayName("Should correctly detect whether icon is set")
        void shouldDetectIconSetState() {
            // Arrange
            var any = anyComponent();

            // Assert - initial state
            assertFalse(any.isIconSet(), "Icon should not be set initially");

            // Act - set icon
            any.setIcon("cui-icon-alarm");

            // Assert - after setting
            assertTrue(any.isIconSet(), "Icon should be detected as set after setting");
        }
    }
}
