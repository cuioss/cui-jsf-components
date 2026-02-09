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
package de.cuioss.jsf.jqplot.options;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import de.cuioss.tools.collect.CollectionBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for TickOptions class")
class TickOptionsTest implements ShouldHandleObjectContracts<TickOptions> {

    @Override
    public TickOptions getUnderTest() {
        return new TickOptions();
    }

    @Nested
    @DisplayName("Basic functionality tests")
    class BasicFunctionalityTests {

        @Test
        @DisplayName("Should return null when no properties are set")
        void shouldReturnNullWhenNoPropertiesAreSet() {
            // Arrange
            final var target = new TickOptions();

            // Act & Assert
            assertNull(target.asJavaScriptObjectNotation(), "Empty tick options should return null");
        }
    }

    @Nested
    @DisplayName("Font configuration tests")
    class FontConfigurationTests {

        @Test
        @DisplayName("Should configure font family property")
        void shouldConfigureFontFamily() {
            // Arrange
            final var target = new TickOptions();

            // Act
            target.setFontFamily("Arial");

            // Assert
            assertEquals("tickOptions: {fontFamily:\"Arial\"}", target.asJavaScriptObjectNotation(),
                    "Should properly format font family configuration");
        }

        @Test
        @DisplayName("Should configure font size property")
        void shouldConfigureFontSize() {
            // Arrange
            final var target = new TickOptions();

            // Act
            target.setFontSize("15px");

            // Assert
            assertEquals("tickOptions: {fontSize:\"15px\"}", target.asJavaScriptObjectNotation(),
                    "Should properly format font size configuration");
        }
    }

    @Nested
    @DisplayName("Angle configuration tests")
    class AngleConfigurationTests {

        @Test
        @DisplayName("Should configure angle property")
        void shouldConfigureAngle() {
            // Arrange
            final var target = new TickOptions();

            // Act
            target.setAngle(10);

            // Assert
            assertEquals("tickOptions: {angle:10}", target.asJavaScriptObjectNotation(),
                    "Should properly format angle configuration");
        }
    }

    @Nested
    @DisplayName("Combined configuration tests")
    class CombinedConfigurationTests {

        @Test
        @DisplayName("Should configure multiple label settings together")
        void shouldConfigureMultipleLabelSettings() {
            // Arrange
            final var target = new TickOptions();

            // Act
            target.setFontFamily("Arial")
                    .setFontSize("15px")
                    .setTextColor("red")
                    .setAngle(10);

            // Assert
            assertEquals("tickOptions: {fontFamily:\"Arial\",fontSize:\"15px\",angle:10,textColor:\"red\"}",
                    target.asJavaScriptObjectNotation(),
                    "Should properly format multiple label settings");
        }

        @Test
        @DisplayName("Should configure all available properties")
        void shouldConfigureAllAvailableProperties() {
            // Arrange
            final var target = new TickOptions()
                    .setShowMark(true)
                    .setShowGridline(true)
                    .setEscapeHTML(true)
                    .setFontFamily("Arial")
                    .setFontSize("15px")
                    .setAngle(10)
                    .setShowLabel(false);

            var expectedProperties = new CollectionBuilder<String>()
                    .add("escapeHTML:true")
                    .add("fontFamily:\"Arial\"")
                    .add("fontSize:\"15px\"")
                    .add("angle:10")
                    .add("showLabel:false")
                    .add("showMark:true")
                    .add("showGridline:true");

            // Act
            var actual = target.asJavaScriptObjectNotation();

            // Assert
            for (String expectedProperty : expectedProperties.toMutableList()) {
                assertTrue(actual.contains(expectedProperty),
                        "Output should contain property: " + expectedProperty);
            }
        }
    }
}
