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
package de.cuioss.jsf.jqplot.plugin.highlighter;

import de.cuioss.jsf.jqplot.hook.PlotHookFunctionProvider;
import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.js.types.JsDouble;
import de.cuioss.jsf.jqplot.js.types.JsInteger;
import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.jsf.jqplot.layout.Location;
import de.cuioss.jsf.jqplot.plugin.IPluginConsumer;
import de.cuioss.jsf.jqplot.plugin.PluginSupport;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.List;

/**
 * Plugin which will highlight data points when they are moused over.
 *
 * @author Eugen Fischer
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/plugins/jqplot-highlighter-js.html">Highlighter
 *      </a>
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class Highlighter extends JsObject implements IPluginConsumer, PlotHookFunctionProvider {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = 341264181606528516L;

    private static final String HOOK_NAME = "highlighter_hook";

    private final PluginSupport pSupport = new PluginSupport();

    private JsBoolean show;

    private JsDouble lineWidthAdjust;

    private JsDouble sizeAdjust;

    private JsString tooltipLocation;

    private JsBoolean showTooltip;

    private JsBoolean fadeTooltip;

    private JsString tooltipFadeSpeed;

    private JsDouble tooltipOffset;

    private JsString tooltipAxes;

    private JsBoolean useAxesFormatters;

    private JsString tooltipFormatString;

    private JsString formatString;

    private JsInteger yvalues;

    private JsBoolean bringSeriesToFront;

    private TooltipContentEditor tooltipContentEditor;

    /**
     *
     */
    public Highlighter() {
        super("highlighter");
        pSupport.add("jqplot.highlighter.min.js");
    }

    /**
     * true to show the highlight.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Highlighter setShow(final Boolean value) {
        show = JsBoolean.create(value);
        return this;
    }

    private JsBoolean showMarker;

    /**
     * true to show the marker
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Highlighter setShowMarker(final Boolean value) {
        showMarker = JsBoolean.create(value);
        return this;
    }

    /**
     * Pixels to add to the lineWidth of the highlight.
     *
     * @param value {@link Double}
     * @return fluent api style
     */
    public Highlighter setLineWidthAdjust(final Double value) {
        lineWidthAdjust = new JsDouble(value);
        return this;
    }

    /**
     * Pixels to add to the overall size of the highlight.
     *
     * @param value {@link Double}
     * @return fluent api style
     */
    public Highlighter setSizeAdjust(final Double value) {
        sizeAdjust = new JsDouble(value);
        return this;
    }

    /**
     * Show a tooltip with data point values.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Highlighter setShowTooltip(final Boolean value) {
        showTooltip = JsBoolean.create(value);
        return this;
    }

    /**
     * Where to position tooltip, 'n', 'ne', 'e', 'se', 's', 'sw', 'w', 'nw'
     *
     * @param value {@link Location}
     * @return fluent api style
     */
    public Highlighter setTooltipLocation(final Location value) {
        if (null == value) {
            tooltipLocation = null;
        } else {
            tooltipLocation = new JsString(value.getConstant());
        }
        return this;
    }

    /**
     * true = fade in/out tooltip, flase = show/hide tooltip
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Highlighter setFadeTooltip(final Boolean value) {
        fadeTooltip = JsBoolean.create(value);
        return this;
    }

    /**
     * 'slow', 'def', 'fast', or number of milliseconds.
     *
     * @param value {@link FadeSpeed}
     * @return fluent api style
     */
    public Highlighter setTooltipFadeSpeed(final FadeSpeed value) {
        if (null == value) {
            tooltipFadeSpeed = null;
        } else {
            tooltipFadeSpeed = new JsString(value.getConstant());
        }
        return this;
    }

    /**
     * Pixel offset of tooltip from the highlight.
     *
     * @param value {@link Double}
     * @return fluent api style
     */
    public Highlighter setTooltipOffset(final Double value) {
        tooltipOffset = new JsDouble(value);
        return this;
    }

    /**
     * Which axes to display in tooltip, 'x', 'y' or 'both', 'xy' or 'yx' 'both' and
     * 'xy' are equivalent, 'yx' reverses order of labels.
     *
     * @param value {@link TooltipAxes}
     * @return fluent api style
     */
    public Highlighter setTooltipAxes(final TooltipAxes value) {
        if (null == value) {
            tooltipAxes = null;
        } else {
            tooltipAxes = new JsString(value.getConstant());
        }
        return this;
    }

    /**
     * Use the x and y axes formatters to format the text in the tooltip.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Highlighter setUseAxesFormatters(final Boolean value) {
        useAxesFormatters = JsBoolean.create(value);
        return this;
    }

    /**
     * sprintf format string for the tooltip.
     *
     * @param value {@link String}
     * @return fluent api style
     */
    public Highlighter setTooltipFormatString(final String value) {
        tooltipFormatString = new JsString(value);
        return this;
    }

    /**
     * alternative to tooltipFormatString will format the whole tooltip text,
     * populating with x, y values as indicated by tooltipAxes option
     *
     * @param value {@link String}
     * @return fluent api style
     */
    public Highlighter setFormatString(final String value) {
        formatString = new JsString(value);
        return this;
    }

    /**
     * Number of y values to expect in the data point array. Typically this is 1.
     * Certain plots, like OHLC, will have more y values in each data point array.
     *
     * @param value {@link String}
     * @return fluent api style
     */
    public Highlighter setYValues(final Integer value) {
        yvalues = new JsInteger(value);
        return this;
    }

    /**
     * This option requires jQuery 1.4+ True to bring the series of the highlighted
     * point to the front of other series.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Highlighter setBringSeriesToFront(final Boolean value) {
        bringSeriesToFront = JsBoolean.create(value);
        return this;
    }

    /**
     * Set or override {@link TooltipContentEditor}
     *
     * @param value {@link TooltipContentEditor}
     * @return fluent api style
     */
    public Highlighter setTooltipContentEditor(final TooltipContentEditor value) {
        tooltipContentEditor = value;
        return this;
    }

    /**
     * @return lazy initialized {@link TooltipContentEditor}
     */
    public TooltipContentEditor getTooltipContentEditor() {
        if (null == tooltipContentEditor) {
            tooltipContentEditor = new TooltipContentEditor();
        }
        return tooltipContentEditor;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("show", show);
        // this.addProperty("markerRenderer", markerRenderer);
        this.addProperty("showMarker", showMarker);
        this.addProperty("lineWidthAdjust", lineWidthAdjust);
        this.addProperty("sizeAdjust", sizeAdjust);
        this.addProperty("showTooltip", showTooltip);
        this.addProperty("tooltipLocation", tooltipLocation);
        this.addProperty("fadeTooltip", fadeTooltip);
        this.addProperty("tooltipFadeSpeed", tooltipFadeSpeed);
        this.addProperty("tooltipOffset", tooltipOffset);
        this.addProperty("tooltipAxes", tooltipAxes);
        this.addProperty("useAxesFormatters", useAxesFormatters);
        this.addProperty("tooltipFormatString", tooltipFormatString);
        this.addProperty("formatString", formatString);
        this.addProperty("yvalues", yvalues);
        this.addProperty("bringSeriesToFront", bringSeriesToFront);
        this.addProperty("tooltipContentEditor", tooltipContentEditor);
        return createAsJSON();
    }

    @Override
    public List<String> usedPlugins() {
        return pSupport.usedPlugins();
    }

    @Override
    public String getHookFunctionCode() {
        if (null != tooltipContentEditor) {
            return tooltipContentEditor.getHookFunctionCode();
        }
        return "";
    }

    @Override
    public String getIdentifier() {
        return HOOK_NAME;
    }
}
