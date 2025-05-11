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
package de.cuioss.jsf.components.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for DecodingStringConverter")
class DecodingStringConverterTest extends AbstractConverterTest<DecodingStringConverter, String> {

    @Override
    @DisplayName("Configure test items")
    public void populate(final TestItems<String> testItems) {
        // Add test cases for roundtrip conversion and specific encoding/decoding scenarios
        testItems.addRoundtripValues("someValue", "%25", "%40")
                .addValidObjectWithStringResult("%", "%25")
                .addValidObjectWithStringResult("@", "%40");
    }

    @Nested
    @DisplayName("Encoding tests")
    class EncodingTests {

        @Test
        @DisplayName("Should encode special characters")
        void shouldEncodeSpecialCharacters(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();

            // Act & Assert
            assertEquals("%3C", converter.getAsString(facesContext, getComponent(), "<"),
                    "Should encode less than sign");
            assertEquals("%3E", converter.getAsString(facesContext, getComponent(), ">"),
                    "Should encode greater than sign");
            assertEquals("%26", converter.getAsString(facesContext, getComponent(), "&"),
                    "Should encode ampersand");
            assertEquals("%3D", converter.getAsString(facesContext, getComponent(), "="),
                    "Should encode equals sign");
            assertEquals("%3F", converter.getAsString(facesContext, getComponent(), "?"),
                    "Should encode question mark");
        }

        @Test
        @DisplayName("Should encode spaces")
        void shouldEncodeSpaces(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();

            // Act & Assert
            assertEquals("Hello+World", converter.getAsString(facesContext, getComponent(), "Hello World"),
                    "Should encode space as plus sign");
            assertEquals("Multiple++Spaces", converter.getAsString(facesContext, getComponent(), "Multiple  Spaces"),
                    "Should encode multiple spaces");
        }

        @Test
        @DisplayName("Should encode non-ASCII characters")
        void shouldEncodeNonAsciiCharacters(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();

            // Act & Assert
            assertEquals("%C3%BC", converter.getAsString(facesContext, getComponent(), "ü"),
                    "Should encode German umlaut");
            assertEquals("%E2%82%AC", converter.getAsString(facesContext, getComponent(), "€"),
                    "Should encode Euro sign");
        }
    }

    @Nested
    @DisplayName("Decoding tests")
    class DecodingTests {

        @Test
        @DisplayName("Should decode encoded special characters")
        void shouldDecodeSpecialCharacters(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();

            // Act & Assert
            assertEquals("<", converter.getAsObject(facesContext, getComponent(), "%3C"),
                    "Should decode less than sign");
            assertEquals(">", converter.getAsObject(facesContext, getComponent(), "%3E"),
                    "Should decode greater than sign");
            assertEquals("&", converter.getAsObject(facesContext, getComponent(), "%26"),
                    "Should decode ampersand");
        }

        @Test
        @DisplayName("Should decode plus signs as spaces")
        void shouldDecodePlusSigns(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();

            // Act & Assert
            assertEquals("Hello World", converter.getAsObject(facesContext, getComponent(), "Hello+World"),
                    "Should decode plus sign as space");
            assertEquals("Multiple  Spaces", converter.getAsObject(facesContext, getComponent(), "Multiple++Spaces"),
                    "Should decode multiple plus signs as multiple spaces");
        }

        @Test
        @DisplayName("Should decode encoded non-ASCII characters")
        void shouldDecodeNonAsciiCharacters(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();

            // Act & Assert
            assertEquals("ü", converter.getAsObject(facesContext, getComponent(), "%C3%BC"),
                    "Should decode German umlaut");
            assertEquals("€", converter.getAsObject(facesContext, getComponent(), "%E2%82%AC"),
                    "Should decode Euro sign");
        }

        @Test
        @DisplayName("Should throw ConverterException for invalid encoded strings")
        void shouldThrowExceptionForInvalidEncodedStrings(FacesContext facesContext) {
            // Arrange
            var converter = getConverter();

            // Act & Assert
            UIComponent component = getComponent();
            assertThrows(ConverterException.class,
                    () -> converter.getAsObject(facesContext, component, "%ZZ"),
                    "Should throw ConverterException for invalid percent encoding");

            assertThrows(ConverterException.class,
                    () -> converter.getAsObject(facesContext, component, "%"),
                    "Should throw ConverterException for incomplete percent encoding");
        }
    }
}
