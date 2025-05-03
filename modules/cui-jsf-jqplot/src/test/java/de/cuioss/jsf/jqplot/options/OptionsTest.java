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
import org.junit.jupiter.api.Test;

class OptionsTest implements ShouldHandleObjectContracts<Options> {

    private Options target;

    @Override
    public Options getUnderTest() {
        return new Options();
    }

    @Test
    void shouldNotReturnObjectOnEmptyProperties() {
        target = new Options();
        assertNull(target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

    @Test
    void shouldProvidePossibilityToSetTitle() {
        target = new Options();
        target.setTitle(new Title("titleText"));
        assertEquals("{title: {text:\"titleText\",escapeHtml:true}}", target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

    @Test
    void shouldProvideLegendOptions() {
        target = new Options();
        final var legend = new Legend();
        legend.setShow(TRUE);
        target.setLegend(legend);
        assertEquals("{legend: {show:true,renderer:$.jqplot.EnhancedLegendRenderer}}",
                target.asJavaScriptObjectNotation());
        assertThatPluginsAreUsed(target, "jqplot.enhancedLegendRenderer.min.js");
    }

    @Test
    void shouldProvideHighlighterOptions() {
        target = new Options();
        final var highlighter = new Highlighter();
        highlighter.setShow(TRUE);
        target.setHighlighter(highlighter);
        assertEquals("{highlighter: {show:true}}", target.asJavaScriptObjectNotation());
        assertThatPluginsAreUsed(target, "jqplot.highlighter.min.js");
    }

    @Test
    void shouldProvidePossibilityToSetAxes() {
        target = new Options();
        final var axes = target.getAxes();
        assertNull(target.asJavaScriptObjectNotation());
        final var anyAxis = Axis.createXAxis();
        anyAxis.setLabel("Any axis");
        axes.addInNotNull(anyAxis);
        assertEquals("{axes: {xaxis: {label:\"Any axis\",showLabel:true}}}", target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

    @Test
    void shouldProvidePossibilityToSetSeriesDefaults() {
        target = new Options();
        final var seriesDefaults = Series.createAsSeriesDefaults();
        seriesDefaults.setXaxis(AxisType.XAXIS);
        target.setSeriesDefaults(seriesDefaults);
        assertEquals("{seriesDefaults: {xaxis:\"xaxis\"}}", target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

    @Test
    void shouldProvidePossibilityToSetSeries() {
        target = new Options();
        final var seriaX = Series.createAsListElement();
        seriaX.setXaxis(AxisType.XAXIS);
        final var seriaY = Series.createAsListElement();
        seriaY.setYaxis(AxisType.YAXIS);
        target.addSeriaOption(seriaX);
        target.addSeriaOption(seriaY);
        assertEquals("{series:[{xaxis:\"xaxis\"},{yaxis:\"yaxis\"}]}", target.asJavaScriptObjectNotation());
        assertThatNoPluginsAreUsed(target);
    }

    @Test
    void shouldSupportCursorPlugin() {
        final var cursor = new Cursor().setShow(TRUE);
        target = new Options().setCursor(cursor);
        assertEquals("{cursor: {show:true}}", target.asJavaScriptObjectNotation());
        assertThatPluginsAreUsed(target, "jqplot.cursor.min.js");
    }

    @Test
    void shouldProvidePossibilityToSetLegend() {
        var legend = new Legend();
        target = new Options();
        target.setLegend(legend);
        assertEquals("{legend: {renderer:$.jqplot.EnhancedLegendRenderer}}", target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldProvidePossibilityToSetGrid() {
        var grid = new Grid();
        grid.setBorderColor("red");
        target = new Options();
        target.setGrid(grid);
        assertEquals("{grid: {drawGridLines:true,gridLineColor:\"#cccccc\",borderColor:\"red\",borderWidth:2.000}}",
                target.asJavaScriptObjectNotation());
    }

}
