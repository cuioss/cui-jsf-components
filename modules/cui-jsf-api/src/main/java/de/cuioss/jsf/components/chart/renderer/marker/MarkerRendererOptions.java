package de.cuioss.jsf.components.chart.renderer.marker;

import de.cuioss.jsf.components.chart.layout.shadow.IShadowDecoration;
import de.cuioss.jsf.components.chart.layout.shadow.Shadow;
import de.cuioss.jsf.components.chart.options.color.Color;
import de.cuioss.jsf.components.chart.renderer.RendererOptions;
import de.cuioss.jsf.components.js.types.JsBoolean;
import de.cuioss.jsf.components.js.types.JsDouble;
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
public class MarkerRendererOptions extends RendererOptions implements
        IShadowDecoration<MarkerRendererOptions> {

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
     * One of diamond, circle, square, x, plus, dash, filledDiamond, filledCircle, filledSquare
     *
     * @param value {@link PointStyle}
     * @return fluent api style
     */
    public MarkerRendererOptions setStyle(final PointStyle value) {
        this.style = value;
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
        this.lineWidth = new JsDouble(value);
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
        this.size = new JsDouble(value);
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
        this.color = Color.createFrom(colorValue);
        return this;
    }

    private JsBoolean shadow;

    @Override
    protected void addPropertiesForJsonObject() {
        this.addProperty("style", style);
        this.addProperty("lineWidth", lineWidth);
        this.addProperty("size", size);
        this.addProperty("color", color);
        this.addProperties(shadowDecorator);
    }

}
