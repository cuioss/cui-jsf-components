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
package de.cuioss.jsf.components.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for TextSplittingConverter")
class TextSplittingConverterTest extends AbstractConverterTest<TextSplittingConverter, String> {

    private static final String LONG_TEXT = "123456789abcdef";
    private static final String VERY_LONG_TEXT = "This is a very long text that should be split into multiple lines and eventually abridged if it exceeds the configured length limit. The text contains more than 15 characters per line and more than 100 characters in total.";

    @Override
    @DisplayName("Configure test items for text splitting conversion")
    public void populate(final TestItems<String> testItems) {
        // Test with standard text
        testItems.addValidObjectWithStringResult(LONG_TEXT, LONG_TEXT);

        // Test with null value
        testItems.addValidObjectWithStringResult(null, "");

        // Test with empty string
        testItems.addValidObjectWithStringResult("", "");
    }

    @Test
    @DisplayName("Should abridge text when it exceeds the abridged length count")
    void shouldAbridgeText(FacesContext facesContext) {
        // Arrange
        var converter = new TextSplittingConverter();
        var component = getComponent();
        converter.setAbridgedLengthCount(10);

        // Act
        var converted = converter.getAsString(facesContext, component, LONG_TEXT);

        // Assert
        assertEquals("123456 ...", converted,
                "Text should be abridged when it exceeds the abridged length count");
    }

    @Test
    @DisplayName("Should enforce line breaks when text exceeds the force length break count")
    void shouldEnforceLineBreaks(FacesContext facesContext) {
        // Arrange
        var converter = new TextSplittingConverter();
        var component = getComponent();
        converter.setForceLengthBreakCount(5);
        converter.setAbridgedLengthCount(100); // Set high to avoid abridging

        // Act
        var converted = converter.getAsString(facesContext, component, LONG_TEXT);

        // Assert
        // The TextSplitter inserts line breaks, but not necessarily spaces
        // Check that the string length is greater than the original
        // due to inserted line break characters
        assertTrue(converted.length() > LONG_TEXT.length(),
                "Text should have line breaks inserted");

        // Verify the text content is preserved (ignoring line breaks)
        var contentWithoutBreaks = converted.replaceAll("[\\s\\u200B]", "");
        assertEquals(LONG_TEXT, contentWithoutBreaks,
                "Text content should be preserved");
    }

    @Test
    @DisplayName("Should use default values when not explicitly set")
    void shouldUseDefaultValues(FacesContext facesContext) {
        // Arrange
        var converter = new TextSplittingConverter();
        var component = getComponent();

        // Act
        var converted = converter.getAsString(facesContext, component, LONG_TEXT);

        // Assert
        // With default values (forceLengthBreakCount=15, abridgedLengthCount=4096),
        // the text should not be modified as it's exactly 15 characters
        assertEquals(LONG_TEXT, converted,
                "Text should not be modified with default values");
    }

    @Test
    @DisplayName("Should both enforce line breaks and abridge text when configured")
    void shouldEnforceLineBreaksAndAbridgeText(FacesContext facesContext) {
        // Arrange
        var converter = new TextSplittingConverter();
        var component = getComponent();
        converter.setForceLengthBreakCount(10);
        converter.setAbridgedLengthCount(30);

        // Act
        var converted = converter.getAsString(facesContext, component, VERY_LONG_TEXT);

        // Assert
        // The text should be split at 10 characters and abridged at 30 characters
        assertTrue(converted.length() <= 30,
                "Text should be abridged to 30 characters or less");
        assertTrue(converted.endsWith("..."),
                "Abridged text should end with '...'");
    }

    @Test
    @DisplayName("Should handle null value")
    void shouldHandleNullValue(FacesContext facesContext) {
        // Arrange
        var converter = new TextSplittingConverter();
        var component = getComponent();

        // Act
        var converted = converter.getAsString(facesContext, component, null);

        // Assert
        assertEquals("", converted, "Should return empty string for null value");
    }

    @Test
    @DisplayName("Should handle empty string")
    void shouldHandleEmptyString(FacesContext facesContext) {
        // Arrange
        var converter = new TextSplittingConverter();
        var component = getComponent();

        // Act
        var converted = converter.getAsString(facesContext, component, "");

        // Assert
        assertEquals("", converted, "Should return empty string for empty input");
    }

    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
