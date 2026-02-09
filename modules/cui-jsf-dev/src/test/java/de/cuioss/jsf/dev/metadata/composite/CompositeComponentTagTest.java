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
package de.cuioss.jsf.dev.metadata.composite;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CompositeComponentTag")
class CompositeComponentTagTest extends AbstractComponentTest<CompositeComponentTag> {

    @Nested
    @DisplayName("Component Attribute Tests")
    class ComponentAttributeTests {

        @Test
        @DisplayName("Should return library attribute")
        void shouldReturnLibrary() {
            // Arrange
            var component = anyComponent();
            var expectedLibrary = "testLibrary";
            component.getAttributes().put("compositeLibrary", expectedLibrary);

            // Act
            var library = component.getLibrary();

            // Assert
            assertEquals(expectedLibrary, library);
        }

        @Test
        @DisplayName("Should handle null library attribute")
        void shouldHandleNullLibrary() {
            // Arrange
            var component = anyComponent();
            // Don't set the attribute at all, which will make getAttributes().get() return null

            // Act
            var library = component.getLibrary();

            // Assert
            assertEquals("null", library);
        }
    }

    @Nested
    @DisplayName("Facet Availability Tests")
    class FacetAvailabilityTests {

        @Test
        @DisplayName("Should return false for unavailable sample facet")
        void shouldReturnFalseForUnavailableSampleFacet() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertFalse(component.isSampleFacetAvailable());
        }

        @Test
        @DisplayName("Should return false for unavailable sample source facet")
        void shouldReturnFalseForUnavailableSampleSourceFacet() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertFalse(component.isSampleSourceFacetAvailable());
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should throw exception for empty composite name")
        void shouldThrowExceptionForEmptyCompositeName() {
            // Arrange
            var component = anyComponent();
            component.getAttributes().put("compositeName", "");

            // Act & Assert
            // This method is private, but we can access it indirectly through getPropertyDescriptors
            assertThrows(IllegalArgumentException.class, component::getPropertyDescriptors);
        }
    }
}
