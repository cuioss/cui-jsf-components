/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.components.partial;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.converter.FallbackSanitizingConverter;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ApplicationConfigDecorator;
import jakarta.faces.application.ProjectStage;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"contentKey", "contentValue", "contentEscape", "contentConverter"})
@ExplicitParamInjection
@DisplayName("Tests for ContentProvider implementation")
class ContentProviderImplTest extends AbstractPartialComponentTest {

    @Test
    @DisplayName("Should throw NullPointerException when constructed with null")
    void shouldFailWithNullConstructor() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new ContentProvider(null),
                "Constructor should reject null component");
    }

    @Nested
    @DisplayName("Tests for content resolution")
    class ContentResolutionTests {

        @Test
        @DisplayName("Should resolve null when no content is set")
        void shouldResolveNullForNoContentSet() {
            // Act & Assert
            assertNull(anyComponent().resolveContent(),
                    "Content should be null when no content is set");
        }

        @Test
        @DisplayName("Should resolve content value when set directly")
        void shouldResolveContentValue() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setContentValue(MESSAGE_VALUE);

            // Assert
            assertEquals(MESSAGE_VALUE, any.resolveContent(),
                    "Should return the directly set content value");
        }

        @Test
        @DisplayName("Should resolve content from resource bundle when key is set")
        void shouldResolveContentKey() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setContentKey(MESSAGE_KEY);

            // Assert
            assertEquals(MESSAGE_VALUE, any.resolveContent(),
                    "Should resolve content from resource bundle using the key");
        }
    }

    @Nested
    @DisplayName("Tests for content sanitization")
    class ContentSanitizationTests {

        @Test
        @DisplayName("Should sanitize content when escape is false and sanitizer is set")
        void shouldSanitizeWithFallback(ApplicationConfigDecorator applicationConfig) {
            // Arrange
            applicationConfig.setProjectStage(ProjectStage.Development);
            var any = anyComponent();

            // Act
            any.setContentValue("<script>");
            any.setContentEscape(false);
            any.setContentConverter(new FallbackSanitizingConverter());

            // Assert
            assertEquals("", any.resolveContent(),
                    "Dangerous script tag should be sanitized to empty string");
        }

        @Test
        @DisplayName("Should not sanitize content when escape is true even with sanitizer set")
        void shouldNotSanitizeWithFallbackWhenEscaped() {
            // Arrange
            var any = anyComponent();

            // Act
            any.setContentValue("<script>");
            any.setContentEscape(true);
            any.setContentConverter(new FallbackSanitizingConverter());

            // Assert
            assertEquals("<script>", any.resolveContent(),
                    "Content should not be sanitized when escape is true");
        }
    }
}
