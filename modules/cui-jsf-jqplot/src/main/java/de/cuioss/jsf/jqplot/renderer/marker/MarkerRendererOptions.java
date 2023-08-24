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
package de.cuioss.jsf.jqplot.renderer.marker;

import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.js.types.JsDouble;
import de.cuioss.jsf.jqplot.layout.shadow.IShadowDecoration;
import de.cuioss.jsf.jqplot.layout.shadow.Shadow;
import de.cuioss.jsf.jqplot.options.color.Color;
import de.cuioss.jsf.jqplot.renderer.RendererOptions;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * The default jqPlot marker renderer, rendering the points on the line.
 *
 * @author Eugen Fischer
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/jqplot-markerRenderer-js.html#$.jqplot.MarkerRenderer">MarkerRenderer</a>
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class MarkerRendererOptions extends RendererOptions implements IShadowDecoration<MarkerRendererOptions> {

    /** serial Version UID */
    private static final long serialVersionUID = 2131890465908857384L;

    @Delegate
    private final Shadow<MarkerRendererOptions> shadowDecorator;

    /**
     *
     */
    public MarkerRendererOptions() {
        super("markerOptions");
        shadowDecorator = new Shadow<>(this);
    }

    private PointStyle style;

    /**
     * One of diamond, circle, square, x, plus, dash, filledDiamond, filledCircle,
     * filledSquare
     *
     * @param value {@link PointStyle}
     * @return fluent api style
     */
    public MarkerRendererOptions setStyle(final PointStyle value) {
        style = value;
        return this;
    }

    private JsDouble lineWidth;

    /**
     * size of the line for non-filled markers.
     *
     * @param value {@linkplain Double}
     * @return fluent api style
     */
    public MarkerRendererOptions setLineWidth(final Double value) {
        lineWidth = new JsDouble(value);
        return this;
    }

    private JsDouble size;

    /**
     * Size of the marker (diameter or circle, length of edge of square, etc.)
     *
     * @param value {@linkplain Double}
     * @return fluent api style
     */
    public MarkerRendererOptions setSize(final Double value) {
        size = new JsDouble(value);
        return this;
    }

    private Color color;

    /**
     * css color spec for the series
     *
     * @param colorValue hex color value
     * @return fluent api style
     */
    public MarkerRendererOptions setColor(final String colorValue) {
        color = Color.createFrom(colorValue);
        return this;
    }

    private JsBoolean shadow;

    @Override
    protected void addPropertiesForJsonObject() {
        this.addProperty("style", style);
        this.addProperty("lineWidth", lineWidth);
        this.addProperty("size", size);
        this.addProperty("color", color);
        addProperties(shadowDecorator);
    }

}
