package de.cuioss.jsf.components.chart.options;

import static de.cuioss.jsf.components.chart.ChartTestSupport.assertThatNoPluginsAreUsed;
import static de.cuioss.jsf.components.chart.ChartTestSupport.assertThatPluginsAreUsed;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.components.chart.axes.Axis;
import de.cuioss.jsf.components.chart.axes.AxisType;
import de.cuioss.jsf.components.chart.layout.Grid;
import de.cuioss.jsf.components.chart.layout.Series;
import de.cuioss.jsf.components.chart.layout.Title;
import de.cuioss.jsf.components.chart.options.legend.Legend;
import de.cuioss.jsf.components.chart.plugin.cursor.Cursor;
import de.cuioss.jsf.components.chart.plugin.highlighter.Highlighter;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;

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
        assertEquals(target.asJavaScriptObjectNotation(), "{highlighter: {show:true}}");
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
