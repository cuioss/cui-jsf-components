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
package de.cuioss.jsf.jqplot.layout;

import static de.cuioss.jsf.jqplot.axes.AxisType.X_AXES;
import static de.cuioss.jsf.jqplot.axes.AxisType.Y_AXES;
import static de.cuioss.tools.base.Preconditions.checkArgument;

import de.cuioss.jsf.jqplot.axes.AxisType;
import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.support.JsValue;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.js.types.JsDouble;
import de.cuioss.jsf.jqplot.js.types.JsInteger;
import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.jsf.jqplot.layout.shadow.IShadowDecoration;
import de.cuioss.jsf.jqplot.layout.shadow.Shadow;
import de.cuioss.jsf.jqplot.options.color.Color;
import de.cuioss.jsf.jqplot.options.color.ColorProperty;
import de.cuioss.jsf.jqplot.plugin.IPluginConsumer;
import de.cuioss.jsf.jqplot.plugin.PluginSupport;
import de.cuioss.jsf.jqplot.renderer.RendererOptions;
import de.cuioss.jsf.jqplot.renderer.SeriaRenderer;
import de.cuioss.jsf.jqplot.renderer.marker.MarkerRenderer;
import de.cuioss.jsf.jqplot.renderer.marker.MarkerRendererOptions;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

import java.io.Serial;
import java.util.List;

/**
 * Class: Series An individual data series object. Cannot be instantiated
 * directly, but created by the Plot object. Series properties can be set or
 * overridden by the options passed in from the user. Properties will be
 * assigned from a series array at the top level of the options.
 *
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/jqplot-core-js.html#Series.Properties">Series.
 *      Properties</a>
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class Series extends JsObject implements IPluginConsumer, JsValue, IShadowDecoration<Series> {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = -7480172463037356837L;

    private boolean hasObjectName;

    private final PluginSupport plugins = new PluginSupport();

    @Delegate
    private final Shadow<Series> shadowDecorator;

    private Series(final boolean isUsedStandAlone) {
        super("seriesDefaults");
        hasObjectName = isUsedStandAlone;
        shadowDecorator = new Shadow<>(this);
    }

    /**
     * Factory Method for {@link Series} object using for seriesDefaults as stand
     * alone object
     *
     * @return {@link Series}
     */
    public static Series createAsSeriesDefaults() {
        return new Series(true);
    }

    /**
     * Factory Method for {@link Series} object using as object inside a list
     *
     * @return {@link Series}
     */
    public static Series createAsListElement() {
        return new Series(false);
    }

    /**
     * wether or not to draw the series.
     */
    private JsBoolean show;

    private JsString xaxis;

    /**
     * change which x axis to use with this series
     *
     * @param type AxisType expected is {@code null} or any member from
     *             {@linkplain AxisType#X_AXES}
     * @return {@link Series} in fluent api style
     * @throws IllegalArgumentException if type is not a member from
     *                                  {@linkplain AxisType#X_AXES}
     */
    public Series setXaxis(final AxisType type) {
        if (null == type) {
            xaxis = null;
        } else {
            checkArgument(X_AXES.contains(type), "X-Axis [%s] is not supported.".formatted(type));
            xaxis = new JsString(type.getAxisName());
        }
        return this;
    }

    private JsString yaxis;

    /**
     * change which y axis to use with this series
     *
     * @param type AxisType expected is {@code null} or any member from
     *             {@linkplain AxisType#Y_AXES}
     * @return {@link Series} in fluent api style
     * @throws IllegalArgumentException if type is not a member from
     *                                  {@linkplain AxisType#Y_AXES}
     */
    public Series setYaxis(final AxisType type) {
        if (null == type) {
            yaxis = null;
        } else {
            checkArgument(Y_AXES.contains(type), "Y-Axis [%s] is not supported.".formatted(type));
            yaxis = new JsString(type.getAxisName());
        }
        return this;
    }

    private SeriaRenderer<?> renderer;

    /**
     * A class of a renderer which will draw the series.
     *
     * @param seriaRenderer {@link SeriaRenderer}.
     * @return fluent api style
     */
    public Series setRenderer(final SeriaRenderer<?> seriaRenderer) {
        renderer = seriaRenderer;
        return this;
    }

    /**
     * Options to pass on to the renderer.
     */
    private RendererOptions rendererOptions;

    /**
     * @param rendererOptions
     * @return {@link Series}
     */
    public Series setRendererOptions(final RendererOptions rendererOptions) {
        this.rendererOptions = rendererOptions;
        return this;
    }

    /**
     * Line label to use in the legend.
     */
    private JsString label;

    /**
     * @param value
     * @return {@link Series}
     */
    public Series setLabel(final String value) {
        label = new JsString(value);
        return this;
    }

    /**
     * true to show label for this series in the legend.
     */
    private JsBoolean showLabel;

    /**
     * css color spec for the series
     */
    private final ColorProperty color = new ColorProperty("color");

    /**
     * css color spec for the series
     *
     * @see Color#createFrom(String)
     * @param colorValue hex color value
     * @return fluent api style
     */
    public Series setColor(final String colorValue) {
        color.setColorValue(colorValue);
        return this;
    }

    /**
     * width of the line in pixels.
     */
    private JsDouble lineWidth;

    /**
     * width of the line in pixels.
     *
     * @param value Double value
     * @return fluent api style
     */
    public Series setLineWidth(final Double value) {
        lineWidth = new JsDouble(value);
        return this;
    }

    /**
     * Canvas lineJoin style between segments of series. this.lineJoin = 'round'
     */
    private JsString lineJoin;

    /**
     * Canvas lineCap style at ends of line. this.lineCap = 'round'
     */
    private JsString lineCap;

    /**
     * Wether line segments should be be broken at null value. False will join point
     * on either side of line.
     */
    private JsBoolean breakOnNull;

    /**
     * A class of a renderer which will draw marker (e.g.
     */
    private MarkerRenderer markerRenderer;

    private MarkerRendererOptions markerOptions;

    /**
     * renderer specific options to pass to the markerRenderer, see
     * $.jqplot.MarkerRenderer.
     *
     * @param options
     * @return {@link Series}
     */
    public Series setMarkerOptions(final MarkerRendererOptions options) {
        markerOptions = options;
        return this;
    }

    /**
     * wether to actually draw the line or not.
     */
    private JsBoolean showLine;

    /**
     * wether to actually draw the line or not.
     *
     * @param value Boolean
     * @return fluent api style
     */
    public Series setShowLine(final Boolean value) {
        showLine = JsBoolean.create(value);
        return this;
    }

    /**
     * wether or not to show the markers at the data points.
     */
    private JsBoolean showMarker;

    /**
     * 0 based index of this series in the plot series array.
     */
    private JsInteger index;

    /**
     * true or false, wether to fill under lines or in bars.
     */
    private JsBoolean fill;

    /**
     * CSS color spec to use for fill under line.
     */
    private Color fillColor;

    /**
     * Alpha transparency to apply to the fill under the line. Use this to adjust
     * alpha separate from fill color.
     */
    private JsDouble fillAlpha;

    /**
     * If true will stroke the line (with color this.color) as well as fill under
     * it. Applies only when fill is true.
     */
    private JsBoolean fillAndStroke;

    /**
     * true to not stack this series with other series in the plot. To render
     * properly, non-stacked series must come after any stacked series in the plot’s
     * data series array. So, the plot’s data series array would look like:
     * [stackedSeries1, stackedSeries2, ..., nonStackedSeries1, nonStackedSeries2,
     * ...] disableStack will put a gap in the stacking order of series, and
     * subsequent stacked series will not fill down through the non-stacked series
     * and will most likely not stack properly on top of the non-stacked series.
     */
    private JsBoolean disableStack;

    /**
     * how close or far (in pixels) the cursor must be from a point marker to detect
     * the point.
     */
    private JsInteger neighborThreshold;

    /**
     * true will force bar and filled series to fill toward zero on the fill Axis.
     */
    private JsBoolean fillToZero;

    /**
     * fill a filled series to this value on the fill axis. Works in conjunction
     * with fillToZero, so that must be true.
     */
    private JsInteger fillToValue;

    /**
     * Either ‘x’ or ‘y’. Which axis to fill the line toward if fillToZero is true.
     * ‘y’ means fill up/down to 0 on the y axis for this series
     */
    private JsString fillAxis;

    /**
     * true to color negative values differently in filled and bar charts.
     */
    private JsBoolean useNegativeColors;

    @Override
    public String asJavaScriptObjectNotation() {
        // add properties
        this.addProperty("breakOnNull", breakOnNull);
        this.addProperty(color);
        this.addProperty("disableStack", disableStack);
        this.addProperty("fill", fill);
        this.addProperty("fillAlpha", fillAlpha);
        this.addProperty("fillAndStroke", fillAndStroke);
        this.addProperty("fillAxis", fillAxis);
        this.addProperty("fillColor", fillColor);
        this.addProperty("fillToValue", fillToValue);
        this.addProperty("fillToZero", fillToZero);
        this.addProperty("index", index);
        this.addProperty("label", label);
        this.addProperty("lineCap", lineCap);
        this.addProperty("lineJoin", lineJoin);
        this.addProperty("lineWidth", lineWidth);
        this.addProperty("neighborThreshold", neighborThreshold);
        this.addProperty("renderer", renderer);
        this.addProperty(rendererOptions);
        this.addProperty("markerRenderer", markerRenderer);
        this.addProperty(markerOptions);
        addProperties(shadowDecorator);
        this.addProperty("showLabel", showLabel);
        this.addProperty("showMarker", showMarker);
        this.addProperty("useNegativeColors", useNegativeColors);
        this.addProperty("show", show);
        this.addProperty("xaxis", xaxis);
        this.addProperty("yaxis", yaxis);
        this.addProperty("showLine", showLine);

        if (hasObjectName) {
            return createAsJSON();
        }
        return createAsJSONObjectWithoutName();
    }

    @Override
    public List<String> usedPlugins() {
        plugins.add(renderer);
        plugins.add(markerRenderer);
        return plugins.usedPlugins();
    }

    @Override
    public String getValueAsString() {
        hasObjectName = false;
        return asJavaScriptObjectNotation();
    }

}
