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
package de.cuioss.jsf.bootstrap.layout.input;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@DisplayName("Tests for ContainerFacets")
class ContainerFacetsTest {

    @Nested
    @DisplayName("Tests for parsing facet names")
    class ParsingTests {

        @Test
        @DisplayName("Should parse all valid enum values correctly")
        void shouldParseValidEnumValues() {
            // Arrange & Act & Assert
            for (ContainerFacets facet : ContainerFacets.values()) {
                // Arrange
                String facetName = facet.getName();

                // Act
                Optional<ContainerFacets> result = ContainerFacets.parse(facetName);

                // Assert
                assertTrue(result.isPresent(), "Should parse valid facet name: " + facetName);
                assertEquals(facet, result.get(), "Parsed facet should match original enum value");
            }
        }

        @Test
        @DisplayName("Should handle invalid values by returning empty Optional")
        void shouldHandleInvalidValues() {
            // Arrange & Act & Assert
            assertFalse(ContainerFacets.parse(null).isPresent(), "Should handle null value");
            assertFalse(ContainerFacets.parse("").isPresent(), "Should handle empty string");
            assertFalse(ContainerFacets.parse("null").isPresent(), "Should handle 'null' string");
        }
    }
}
