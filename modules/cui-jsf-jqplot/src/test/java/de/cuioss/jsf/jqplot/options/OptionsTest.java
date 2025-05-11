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
package de.cuioss.jsf.jqplot.options;

import static de.cuioss.jsf.jqplot.ChartTestSupport.assertThatNoPluginsAreUsed;
import static de.cuioss.jsf.jqplot.ChartTestSupport.assertThatPluginsAreUsed;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.cuioss.jsf.jqplot.axes.Axis;
import de.cuioss.jsf.jqplot.axes.AxisType;
import de.cuioss.jsf.jqplot.layout.Grid;
import de.cuioss.jsf.jqplot.layout.Series;
import de.cuioss.jsf.jqplot.layout.Title;
import de.cuioss.jsf.jqplot.options.legend.Legend;
import de.cuioss.jsf.jqplot.plugin.cursor.Cursor;
import de.cuioss.jsf.jqplot.plugin.highlighter.Highlighter;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for Options class")
class OptionsTest implements ShouldHandleObjectContracts<Options> {

    private Options target;

    @Override
    public Options getUnderTest() {
        return new Options();
    }

    @Nested
    @DisplayName("Basic functionality tests")
    class BasicFunctionalityTests {

        @Test
        @DisplayName("Should return null when no properties are set")
        void shouldReturnNullWhenNoPropertiesAreSet() {
            // Arrange
            target = new Options();

            // Act & Assert
            assertNull(target.asJavaScriptObjectNotation(), "Empty options should return null");
            assertThatNoPluginsAreUsed(target);
        }
    }

    @Nested
    @DisplayName("Title configuration tests")
    class TitleConfigurationTests {

        @Test
        @DisplayName("Should configure title property")
        void shouldConfigureTitle() {
            // Arrange
            target = new Options();

            // Act
            target.setTitle(new Title("titleText"));

            // Assert
            assertEquals("{title: {text:\"titleText\",escapeHtml:true}}", target.asJavaScriptObjectNotation(),
                    "Should properly format title configuration");
            assertThatNoPluginsAreUsed(target);
        }
    }

    @Nested
    @DisplayName("Legend configuration tests")
    class LegendConfigurationTests {

        @Test
        @DisplayName("Should configure legend with show property")
        void shouldConfigureLegendWithShowProperty() {
            // Arrange
            target = new Options();
            final var legend = new Legend();

            // Act
            legend.setShow(TRUE);
            target.setLegend(legend);

            // Assert
            assertEquals("{legend: {show:true,renderer:$.jqplot.EnhancedLegendRenderer}}",
                    target.asJavaScriptObjectNotation(),
                    "Should properly format legend with show property");
            assertThatPluginsAreUsed(target, "jqplot.enhancedLegendRenderer.min.js");
        }

        @Test
        @DisplayName("Should configure legend with default properties")
        void shouldConfigureLegendWithDefaultProperties() {
            // Arrange
            target = new Options();
            var legend = new Legend();

            // Act
            target.setLegend(legend);

            // Assert
            assertEquals("{legend: {renderer:$.jqplot.EnhancedLegendRenderer}}", target.asJavaScriptObjectNotation(),
                    "Should properly format legend with default properties");
        }
    }

    @Nested
    @DisplayName("Highlighter configuration tests")
    class HighlighterConfigurationTests {

        @Test
        @DisplayName("Should configure highlighter plugin")
        void shouldConfigureHighlighter() {
            // Arrange
            target = new Options();
            final var highlighter = new Highlighter();

            // Act
            highlighter.setShow(TRUE);
            target.setHighlighter(highlighter);

            // Assert
            assertEquals("{highlighter: {show:true}}", target.asJavaScriptObjectNotation(),
                    "Should properly format highlighter configuration");
            assertThatPluginsAreUsed(target, "jqplot.highlighter.min.js");
        }
    }

    @Nested
    @DisplayName("Axes configuration tests")
    class AxesConfigurationTests {

        @Test
        @DisplayName("Should configure axes with labels")
        void shouldConfigureAxesWithLabels() {
            // Arrange
            target = new Options();
            final var axes = target.getAxes();

            // Act - verify initial state
            assertNull(target.asJavaScriptObjectNotation(), "Options should be null before adding axes");

            // Act - add axis with label
            final var anyAxis = Axis.createXAxis();
            anyAxis.setLabel("Any axis");
            axes.addInNotNull(anyAxis);

            // Assert
            assertEquals("{axes: {xaxis: {label:\"Any axis\",showLabel:true}}}", target.asJavaScriptObjectNotation(),
                    "Should properly format axes configuration");
            assertThatNoPluginsAreUsed(target);
        }
    }

    @Nested
    @DisplayName("Series configuration tests")
    class SeriesConfigurationTests {

        @Test
        @DisplayName("Should configure series defaults")
        void shouldConfigureSeriesDefaults() {
            // Arrange
            target = new Options();
            final var seriesDefaults = Series.createAsSeriesDefaults();

            // Act
            seriesDefaults.setXaxis(AxisType.XAXIS);
            target.setSeriesDefaults(seriesDefaults);

            // Assert
            assertEquals("{seriesDefaults: {xaxis:\"xaxis\"}}", target.asJavaScriptObjectNotation(),
                    "Should properly format series defaults configuration");
            assertThatNoPluginsAreUsed(target);
        }

        @Test
        @DisplayName("Should configure multiple series options")
        void shouldConfigureMultipleSeries() {
            // Arrange
            target = new Options();
            final var seriaX = Series.createAsListElement();
            final var seriaY = Series.createAsListElement();

            // Act
            seriaX.setXaxis(AxisType.XAXIS);
            seriaY.setYaxis(AxisType.YAXIS);
            target.addSeriaOption(seriaX);
            target.addSeriaOption(seriaY);

            // Assert
            assertEquals("{series:[{xaxis:\"xaxis\"},{yaxis:\"yaxis\"}]}", target.asJavaScriptObjectNotation(),
                    "Should properly format multiple series configuration");
            assertThatNoPluginsAreUsed(target);
        }
    }

    @Nested
    @DisplayName("Plugin configuration tests")
    class PluginConfigurationTests {

        @Test
        @DisplayName("Should configure cursor plugin")
        void shouldConfigureCursorPlugin() {
            // Arrange
            final var cursor = new Cursor().setShow(TRUE);

            // Act
            target = new Options().setCursor(cursor);

            // Assert
            assertEquals("{cursor: {show:true}}", target.asJavaScriptObjectNotation(),
                    "Should properly format cursor plugin configuration");
            assertThatPluginsAreUsed(target, "jqplot.cursor.min.js");
        }
    }

    @Nested
    @DisplayName("Grid configuration tests")
    class GridConfigurationTests {

        @Test
        @DisplayName("Should configure grid properties")
        void shouldConfigureGridProperties() {
            // Arrange
            target = new Options();
            var grid = new Grid();

            // Act
            grid.setBorderColor("red");
            target.setGrid(grid);

            // Assert
            assertEquals("{grid: {drawGridLines:true,gridLineColor:\"#cccccc\",borderColor:\"red\",borderWidth:2.000}}",
                    target.asJavaScriptObjectNotation(),
                    "Should properly format grid configuration");
        }
    }
}
