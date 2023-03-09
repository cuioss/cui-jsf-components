package com.icw.ehf.cui.components.chart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.chart.layout.Title;
import com.icw.ehf.cui.components.chart.model.SeriaTupelItem;
import com.icw.ehf.cui.components.chart.model.SeriesData;
import com.icw.ehf.cui.components.chart.options.Options;
import com.icw.ehf.cui.components.js.support.JsArray;
import com.icw.ehf.cui.components.js.support.JsValue;
import com.icw.ehf.cui.components.js.types.JsDate;
import com.icw.ehf.cui.components.js.types.JsDouble;
import com.icw.ehf.cui.components.js.types.JsInteger;

import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;

@SuppressWarnings({ "unchecked" })
class JqPlotTest implements ShouldHandleObjectContracts<JqPlot> {

    @Override
    public JqPlot getUnderTest() {
        return new JqPlot("ccId", anyNotEmptySeria());
    }

    @Test
    void createMinimalGraph() {
        final var targetId = "chartA";
        final var jqPlot = new JqPlot(targetId, anyNotEmptySeria());
        assertEquals("$.jqplot(\"" + targetId + "\", [[0]], null);", jqPlot.asJavaScriptObjectNotation());
        assertEquals(jqPlot.getChartId(), targetId);
    }

    @Test
    void createGraphWithTitle() {
        final var targetId = "chartA";
        final var title = new Title("Incident date");
        final var options = new Options();
        options.setTitle(title);
        final var jqPlot = new JqPlot(targetId, anyNotEmptySeria(), options);
        assertEquals("$.jqplot(\"chartA\", [[0]], {title: {text:\"Incident date\",escapeHtml:true}});",
                jqPlot.asJavaScriptObjectNotation());
    }

    @Test
    void createGraphWithOneTimeLine() {
        final var targetId = "chartA";
        final var title = new Title("Incident date");
        final var options = new Options();
        options.setTitle(title);
        final JsArray<? super JsValue> line = new JsArray<>();
        for (var i = 0; i < 5; i++) {
            line.addValueIfNotNull(createItem(i));
        }
        final var jqPlot = new JqPlot(targetId, createSeries(line), options);
        assertNotNull(jqPlot.asJavaScriptObjectNotation());
    }

    @Test
    void shouldProvideHookExtension() {
        final var targetId = "chartA";
        final var options = new Options();
        options.getHighlighter().getTooltipContentEditor();
        final var jqPlot = new JqPlot(targetId, anyNotEmptySeria(), options);
        assertFalse(jqPlot.isNothingToDisplay());
        assertTrue(jqPlot.getPlugins().contains("jqplot.highlighter.min.js"));
        assertEquals(
                "$.jqplot(\"chartA\", [[0]], {highlighter: {tooltipContentEditor:tooltipContentEditor}});function tooltipContentEditor(str,seriesIndex,pointIndex,plot){return \"\";};",
                jqPlot.asJavaScriptObjectNotation());
    }

    @Test
    void shouldProvidePossibilityToIgnoreAvailableData() {
        final var targetId = "chartA";
        final var jqPlot = new JqPlot(targetId, anyNotEmptySeria());
        assertFalse(jqPlot.isNothingToDisplay());
        assertEquals("$.jqplot(\"chartA\", [[0]], null);", jqPlot.asJavaScriptObjectNotation());
        jqPlot.setNothingToDisplay(true);
        assertTrue(jqPlot.isNothingToDisplay());
        assertEquals("'';", jqPlot.asJavaScriptObjectNotation());
        assertEquals(0, jqPlot.getPlugins().size());
    }

    private static SeriesData anyNotEmptySeria() {
        final var seriesData = new SeriesData();
        final var jsArray = new JsArray<>();
        jsArray.addValueIfNotNull(new JsInteger(0));
        seriesData.addSeriaDataIfNotNull(jsArray);
        return seriesData;
    }

    private static SeriesData createSeries(final JsArray<? super JsValue>... lines) {
        final var seriesData = new SeriesData();
        for (final JsArray<? super JsValue> jsArray : lines) {
            seriesData.addSeriaDataIfNotNull(jsArray);
        }
        return seriesData;
    }

    private static SeriaTupelItem<JsDate, JsDouble> createItem(final int someValue) {
        final var cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1950);
        cal.add(Calendar.YEAR, someValue);
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        final var x = new JsDate(cal.getTime());
        final var y = new JsDouble(0.5 * someValue);
        return new SeriaTupelItem<>(x, y);
    }

}
