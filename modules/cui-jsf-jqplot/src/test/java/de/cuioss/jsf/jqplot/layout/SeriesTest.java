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

import static de.cuioss.jsf.jqplot.ChartTestSupport.assertThatNoPluginsAreUsed;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.jqplot.axes.AxisType;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for Series class")
class SeriesTest {

    private Series target;

    @Nested
    @DisplayName("Serialization tests")
    class SerializationTests {

        @Test
        @DisplayName("Should be serializable")
        void shouldBeSerializable() {
            // Arrange & Act & Assert
            assertDoesNotThrow(() -> SerializableContractImpl.serializeAndDeserialize(Series.createAsListElement()));
        }
    }

    @Nested
    @DisplayName("Basic property tests")
    class BasicPropertyTests {

        @Test
        @DisplayName("Should not return object when no properties are set")
        void shouldNotReturnObjectOnEmptyProperties() {
            // Arrange
            target = Series.createAsSeriesDefaults();

            // Act & Assert
            assertNull(target.asJavaScriptObjectNotation());
            assertThatNoPluginsAreUsed(target);
        }
    }

    @Nested
    @DisplayName("Line visibility tests")
    class LineVisibilityTests {

        @Test
        @DisplayName("Should control line visibility with showLine property")
        void shouldProvideHideLine() {
            // Arrange
            target = Series.createAsSeriesDefaults();

            // Act - set to true
            target.setShowLine(TRUE);

            // Assert
            assertEquals("seriesDefaults: {showLine:true}", target.asJavaScriptObjectNotation(),
                    "Should show line when showLine is true");

            // Act - set to false
            target.setShowLine(FALSE);

            // Assert
            assertEquals("seriesDefaults: {showLine:false}", target.asJavaScriptObjectNotation(),
                    "Should hide line when showLine is false");
            assertThatNoPluginsAreUsed(target);
        }
    }

    @Nested
    @DisplayName("Line appearance tests")
    class LineAppearanceTests {

        @Test
        @DisplayName("Should set line color")
        void shouldProvideColorLine() {
            // Arrange
            target = Series.createAsListElement();

            // Act - set color
            target.setColor("#FF5500");

            // Assert
            assertEquals("{color:\"#FF5500\"}", target.asJavaScriptObjectNotation(),
                    "Should set specific color");

            // Act - set null color
            target.setColor(null);

            // Assert
            assertEquals("{color:\"transparent\"}", target.asJavaScriptObjectNotation(),
                    "Should set transparent color when null");
            assertThatNoPluginsAreUsed(target);
        }

        @Test
        @DisplayName("Should control shadow property")
        void shouldProvideShadow() {
            // Arrange
            target = Series.createAsListElement();

            // Act - set shadow to true
            target.setShadow(TRUE);

            // Assert
            assertEquals("{shadow:true}", target.asJavaScriptObjectNotation(),
                    "Should enable shadow when true");

            // Act - set shadow to false
            target.setShadow(FALSE);

            // Assert
            assertEquals("{shadow:false}", target.asJavaScriptObjectNotation(),
                    "Should disable shadow when false");
            assertThatNoPluginsAreUsed(target);
        }

        @Test
        @DisplayName("Should set line width")
        void shouldProvideLineWidth() {
            // Arrange
            target = Series.createAsListElement();

            // Act - set line width
            target.setLineWidth(10.1);

            // Assert
            assertEquals("{lineWidth:10.100}", target.asJavaScriptObjectNotation(),
                    "Should set line width with three decimal places");

            // Act - set null line width
            target.setLineWidth(null);

            // Assert
            assertNull(target.asJavaScriptObjectNotation(), "Should return null when line width is null");
            assertThatNoPluginsAreUsed(target);
        }
    }

    @Nested
    @DisplayName("Axis configuration tests")
    class AxisConfigurationTests {

        @Test
        @DisplayName("Should configure x and y axis types")
        void shouldProvideChangeSeriaAxis() {
            // Arrange
            target = Series.createAsSeriesDefaults();
            var yaxis = Generators.fixedValues(AxisType.Y_AXES).next();

            // Act & Assert - validate x-axis type checking
            assertThrows(IllegalArgumentException.class, () -> target.setXaxis(yaxis),
                    "Should reject Y axis type for X axis");

            // Act - set valid x-axis
            target.setXaxis(AxisType.X2AXIS);

            // Assert
            assertEquals("seriesDefaults: {xaxis:\"x2axis\"}", target.asJavaScriptObjectNotation(),
                    "Should set x2axis correctly");

            // Arrange for y-axis test
            var xaxis = Generators.fixedValues(AxisType.X_AXES).next();

            // Act & Assert - validate y-axis type checking
            assertThrows(IllegalArgumentException.class, () -> target.setYaxis(xaxis),
                    "Should reject X axis type for Y axis");

            // Act - set valid y-axis
            target.setYaxis(AxisType.Y2AXIS);

            // Assert
            assertEquals("seriesDefaults: {xaxis:\"x2axis\",yaxis:\"y2axis\"}", target.asJavaScriptObjectNotation(),
                    "Should set both axes correctly");
        }
    }
}
