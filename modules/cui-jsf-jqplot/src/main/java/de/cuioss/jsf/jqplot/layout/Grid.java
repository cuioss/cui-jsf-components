package de.cuioss.jsf.jqplot.layout;

import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.js.types.JsDouble;
import de.cuioss.jsf.jqplot.layout.shadow.IShadowDecoration;
import de.cuioss.jsf.jqplot.layout.shadow.Shadow;
import de.cuioss.jsf.jqplot.options.color.Color;
import de.cuioss.jsf.jqplot.options.color.ColorProperty;
import de.cuioss.jsf.jqplot.renderer.CanvasGridRenderer;
import de.cuioss.jsf.jqplot.renderer.RendererOptions;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * <pre>
 * grid: {
        drawGridLines: true,        // whether to draw lines across the grid or not.
        gridLineColor: '#cccccc'    // Color of the grid lines.
        background: '#fffdf6',      // CSS color spec for background color of grid.
        borderColor: '#999999',     // CSS color spec for border around grid.
        borderWidth: 2.0,           // pixel width of border around grid.
        shadow: true,               // draw a shadow for grid.
        shadowAngle: 45,            // angle of the shadow.  Clockwise from x axis.
        shadowOffset: 1.5,          // offset from the line of the shadow.
        shadowWidth: 3,             // width of the stroke for the shadow.
        shadowDepth: 3,             // Number of strokes to make when drawing shadow.
                                    // Each stroke offset by shadowOffset from the last.
        shadowAlpha: 0.07           // Opacity of the shadow
        renderer: $.jqplot.CanvasGridRenderer,  // renderer to use to draw the grid.
        rendererOptions: {}         // options to pass to the renderer.  Note, the default
                                    // CanvasGridRenderer takes no additional options.
    }
 * </pre>
 *
 * @author Oliver Wolff
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/jqplot-core-js.html#Grid">jqplot core
 *      grid</a>
 */
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class Grid extends JsObject implements IShadowDecoration<Grid> {

    private static final long serialVersionUID = 6537562133677782480L;

    private static final Color DEFAULT_GRID_LINE_COLOR = Color.createFrom("#cccccc");

    private static final Double DEFAULT_SHADOW_WIDTH = 3.0;

    private static final Double DEFAULT_BORDER_WIDTH = 2.0;

    @Delegate
    private final Shadow<Grid> shadowDecorator;

    /**
     * Default Constructor
     */
    public Grid() {
        super("grid");
        shadowDecorator = new Shadow<>(this);
    }

    /**
     * wether to draw lines across the grid or not.
     */
    private JsBoolean drawGridLines = JsBoolean.TRUE;

    /**
     * wether to draw lines across the grid or not.
     *
     * @param value
     * @return {@linkplain Grid} in fluent api style
     */
    public Grid setDrawGridLines(final boolean value) {
        if (value) {
            drawGridLines = JsBoolean.TRUE;
        } else {
            drawGridLines = JsBoolean.FALSE;
        }
        return this;
    }

    /**
     * "#cccccc" *Color of the grid lines.
     */
    private final ColorProperty gridLineColor = new ColorProperty("gridLineColor", DEFAULT_GRID_LINE_COLOR);

    /**
     * Change color of the grid lines.<br>
     * If parameter value is {@code null} or empty the color will be set to
     * 'transparent'.<br>
     * Otherwise it should match the html color definition.<br>
     * <b>Default</b> value is <i>#cccccc</i>
     *
     * @see Color
     * @param colorValue string value
     * @return {@linkplain Grid} in fluent api style
     */
    public Grid setGridLineColor(final String colorValue) {
        gridLineColor.setColorValue(colorValue);
        return this;
    }

    /**
     * '#fffdf6', CSS color spec for background color of grid.
     */
    private final ColorProperty background = new ColorProperty("background");

    /**
     * Change color of the grid background.<br>
     * If parameter value is {@code null} or empty the color will be set to
     * 'transparent'.<br>
     * Otherwise it should match the html color definition.<br>
     * <b>Default</b> value is <i>#cccccc</i>
     *
     * @see Color
     * @param colorValue string value
     * @return {@linkplain Grid} in fluent api style
     */
    public Grid setBackground(final String colorValue) {
        background.setColorValue(colorValue);
        return this;
    }

    /**
     * '#999999', CSS color spec for border around grid.
     */
    private final ColorProperty borderColor = new ColorProperty("borderColor");

    /**
     * Change color of the grid border.<br>
     * If parameter value is {@code null} or empty the color will be set to
     * 'transparent'.<br>
     * Otherwise it should match the html color definition.<br>
     * <b>Default</b> value is <i>#999999</i>
     *
     * @see Color
     * @param colorValue string value
     * @return {@linkplain Grid} in fluent api style
     */
    public Grid setBorderColor(final String colorValue) {
        borderColor.setColorValue(colorValue);
        return this;
    }

    /**
     * pixel width of border around grid.
     */
    private JsDouble borderWidth = new JsDouble(DEFAULT_BORDER_WIDTH);

    public Grid setBorderWidth(final double value) {
        borderWidth = new JsDouble(value);
        return this;
    }

    /**
     * width of the stroke for the shadow.
     */
    private final Double shadowWidth = DEFAULT_SHADOW_WIDTH;

    /**
     * $.jqplot.CanvasGridRenderer, renderer to use to draw the grid.
     */
    private final CanvasGridRenderer renderer = null;

    /**
     * options to pass to the renderer. Note, the default CanvasGridRenderer takes
     * no additional options.
     */
    private final RendererOptions rendererOptions = null;

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("drawGridLines", drawGridLines);
        this.addProperty(gridLineColor);
        this.addProperty(background);
        this.addProperty(borderColor);
        this.addProperty("borderWidth", borderWidth);
        addProperties(shadowDecorator);
        return createAsJSON();
    }

}
