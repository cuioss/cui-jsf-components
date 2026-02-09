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
package de.cuioss.jsf.jqplot.renderer.marker;

import static de.cuioss.jsf.jqplot.ChartTestSupport.assertThatNoPluginsAreUsed;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for MarkerRendererOptions class")
class MarkerRendererOptionsTest implements ShouldHandleObjectContracts<MarkerRendererOptions> {

    @Override
    public MarkerRendererOptions getUnderTest() {
        return new MarkerRendererOptions();
    }

    @Nested
    @DisplayName("Basic functionality tests")
    class BasicFunctionalityTests {

        @Test
        @DisplayName("Should return null when no properties are set")
        void shouldReturnNullWhenNoPropertiesAreSet() {
            // Arrange
            final var target = new MarkerRendererOptions();

            // Act & Assert
            assertNull(target.asJavaScriptObjectNotation(), "Empty marker options should return null");
            assertThatNoPluginsAreUsed(target);
        }
    }

    @Nested
    @DisplayName("Shadow configuration tests")
    class ShadowConfigurationTests {

        @Test
        @DisplayName("Should configure shadow properties")
        void shouldConfigureShadowProperties() {
            // Arrange
            final var target = new MarkerRendererOptions();

            // Act - set shadow to true
            target.setShadow(true);

            // Assert
            assertEquals("markerOptions: {shadow:true}", target.asJavaScriptObjectNotation(),
                    "Should enable shadow when true");

            // Act - set shadow to false
            target.setShadow(false);

            // Assert
            assertEquals("markerOptions: {shadow:false}", target.asJavaScriptObjectNotation(),
                    "Should disable shadow when false");

            // Act - set shadow to null
            target.setShadow(null);

            // Assert
            assertNull(target.asJavaScriptObjectNotation(),
                    "Should return null when shadow is null");

            // Act - set all shadow properties
            target.setShadow(true)
                    .setShadowAlpha("0.07")
                    .setShadowAngle(10.0)
                    .setShadowDepth(5)
                    .setShadowOffset(7);

            // Assert
            assertEquals(
                    "markerOptions: {shadow:true,shadowAlpha:\"0.07\",shadowAngle:10.000,shadowDepth:5,shadowOffset:7}",
                    target.asJavaScriptObjectNotation(),
                    "Should configure all shadow properties correctly");
            assertThatNoPluginsAreUsed(target);
        }
    }

    @Nested
    @DisplayName("Marker style tests")
    class MarkerStyleTests {

        @Test
        @DisplayName("Should configure different marker styles")
        void shouldConfigureDifferentMarkerStyles() {
            // Arrange
            final var target = new MarkerRendererOptions();

            // Act & Assert - test each style
            target.setStyle(PointStyle.CIRCLE);
            assertEquals("markerOptions: {style:\"circle\"}", target.asJavaScriptObjectNotation(),
                    "Should set circle style correctly");

            target.setStyle(PointStyle.FILLEDCIRCLE);
            assertEquals("markerOptions: {style:\"filledCircle\"}", target.asJavaScriptObjectNotation(),
                    "Should set filled circle style correctly");

            target.setStyle(PointStyle.DASH);
            assertEquals("markerOptions: {style:\"dash\"}", target.asJavaScriptObjectNotation(),
                    "Should set dash style correctly");

            target.setStyle(PointStyle.DIAMOND);
            assertEquals("markerOptions: {style:\"diamond\"}", target.asJavaScriptObjectNotation(),
                    "Should set diamond style correctly");

            target.setStyle(PointStyle.FILLEDDIAMOND);
            assertEquals("markerOptions: {style:\"filledDiamond\"}", target.asJavaScriptObjectNotation(),
                    "Should set filled diamond style correctly");

            target.setStyle(PointStyle.PLUS);
            assertEquals("markerOptions: {style:\"plus\"}", target.asJavaScriptObjectNotation(),
                    "Should set plus style correctly");

            target.setStyle(PointStyle.SQUARE);
            assertEquals("markerOptions: {style:\"square\"}", target.asJavaScriptObjectNotation(),
                    "Should set square style correctly");

            target.setStyle(PointStyle.FILLEDSQUARE);
            assertEquals("markerOptions: {style:\"filledSquare\"}", target.asJavaScriptObjectNotation(),
                    "Should set filled square style correctly");

            target.setStyle(PointStyle.X);
            assertEquals("markerOptions: {style:\"x\"}", target.asJavaScriptObjectNotation(),
                    "Should set X style correctly");

            assertThatNoPluginsAreUsed(target);
        }
    }

}
