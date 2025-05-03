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
package de.cuioss.jsf.jqplot;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.jqplot.js.support.JsArray;
import de.cuioss.jsf.jqplot.js.support.JsValue;
import de.cuioss.jsf.jqplot.js.types.JsDate;
import de.cuioss.jsf.jqplot.js.types.JsDouble;
import de.cuioss.jsf.jqplot.js.types.JsInteger;
import de.cuioss.jsf.jqplot.layout.Title;
import de.cuioss.jsf.jqplot.model.SeriaTupelItem;
import de.cuioss.jsf.jqplot.model.SeriesData;
import de.cuioss.jsf.jqplot.options.Options;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

@SuppressWarnings({"unchecked"})
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
