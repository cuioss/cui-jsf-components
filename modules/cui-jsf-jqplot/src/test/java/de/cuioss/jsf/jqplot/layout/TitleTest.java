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
package de.cuioss.jsf.jqplot.layout;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for Title class")
class TitleTest {

    @Nested
    @DisplayName("Serialization tests")
    class SerializationTests {

        @Test
        @DisplayName("Should be serializable")
        void shouldBeSerializable() {
            // Arrange & Act & Assert
            assertDoesNotThrow(() -> SerializableContractImpl.serializeAndDeserialize(new Title()));
        }
    }

    @Nested
    @DisplayName("Title text tests")
    class TitleTextTests {

        @Test
        @DisplayName("Should provide title text in JavaScript notation")
        void shouldProvideTitleText() {
            // Arrange
            final var target = new Title("any text");

            // Act
            final var json = target.asJavaScriptObjectNotation();

            // Assert
            assertEquals("title: {text:\"any text\",escapeHtml:true}", json,
                    "Should format title text with escapeHtml set to true");
            assertEquals(json, target.asJavaScriptObjectNotation(),
                    "Should return consistent results on multiple calls");
        }
    }

    @Nested
    @DisplayName("Empty property tests")
    class EmptyPropertyTests {

        @Test
        @DisplayName("Should return null when no properties are set")
        void shouldNotReturnObjectOnEmptyProperties() {
            // Arrange
            final var target = new Title();

            // Act & Assert
            assertNull(target.asJavaScriptObjectNotation(),
                    "Should return null for empty title");
        }
    }
}
