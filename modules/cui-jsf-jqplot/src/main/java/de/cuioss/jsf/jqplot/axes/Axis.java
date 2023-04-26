package de.cuioss.jsf.jqplot.axes;

import static de.cuioss.tools.string.MoreStrings.emptyToNull;

import java.awt.Color;
import java.util.List;

import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.support.JsValue;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.jsf.jqplot.model.Ticks;
import de.cuioss.jsf.jqplot.options.LabelOptions;
import de.cuioss.jsf.jqplot.options.TickOptions;
import de.cuioss.jsf.jqplot.plugin.IPluginConsumer;
import de.cuioss.jsf.jqplot.plugin.PluginSupport;
import de.cuioss.jsf.jqplot.renderer.AxisTickRenderer;
import de.cuioss.jsf.jqplot.renderer.LabelRenderer;
import de.cuioss.jsf.jqplot.renderer.Renderer;
import de.cuioss.jsf.jqplot.renderer.RendererOptions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * An individual axis object. Cannot be instantiated directly, but created by the Plot object. Axis
 * properties can be set or overriden by the options passed in from the user.
 *
 * @see <a href="http://www.jqplot.com/docs/files/jqplot-core-js.html#Axis.labelOptions"></a>
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class Axis extends JsObject implements IPluginConsumer {

    /** serial Version UID */
    private static final long serialVersionUID = 4870320461004802288L;

    private final PluginSupport plugins = new PluginSupport();

    /** Wether to display the axis on the graph. */
    private final boolean show = true;

    /**
     * A class of a rendering engine for creating the ticks labels displayed on the plot, See
     * $.jqplot.AxisTickRenderer.
     */
    @Setter
    private AxisTickRenderer tickRenderer;

    /** Options that will be passed to the tickRenderer, see $.jqplot.AxisTickRenderer options. */
    @Setter
    private TickOptions tickOptions;

    /** A class of a rendering engine for creating an axis label. */
    private LabelRenderer labelRenderer;

    /** Options passed to the label renderer. */
    @Setter
    private LabelOptions labelOptions;

    /** Label for the axis */
    private JsString label;

    /** true to show the axis label */
    private JsBoolean showLabel = null;

    private JsValue min = null;

    private JsValue max = null;

    /** Autoscale the axis min and max values to provide sensible tick spacing. */
    private final boolean autoscale = false;

    /**
     * Padding to extend the range above and below the data bounds. The data range is multiplied by
     * this factor to determine minimum and maximum axis bounds. A value of 0 will be interpreted to
     * mean no padding, and pad will be set to 1.0.
     */
    private final Double pad = null;

    /** Padding to extend the range above data bounds. */
    private final Double padMax = null;

    /** Padding to extend the range below data bounds. */
    private final Double padMin = null;

    /** 1D [val, val, ...] or 2D [[val, label], [val, label], ...] array of ticks for the axis. */
    private Ticks ticks;

    /** Desired number of ticks. Default is to compute automatically. */
    private Integer numberTicks = null;

    /** number of units between ticks. Mutually exclusive with numberTicks. */
    private String tickInterval = null;

    /**
     * A class of a rendering engine that handles tick generation, scaling input data to pixel grid
     * units and drawing the axis element.
     */
    @Setter
    private Renderer renderer = null;

    /** renderer specific options. See $.jqplot.LinearAxisRenderer for options. */
    private final RendererOptions rendererOptions = null;

    /**
     * Wether to show the ticks (both marks and labels) or not. Will not override showMark and
     * showLabel options if specified on the ticks themselves.
     */
    private final boolean showTicks = true;

    /**
     * Wether to show the tick marks (line crossing grid) or not. Overridden by showTicks and
     * showMark option of tick itself.
     */
    private boolean showTickMarks;

    /**
     * Wether or not to show minor ticks. This is renderer dependent. The default
     * $.jqplot.LinearAxisRenderer does not have minor ticks.
     */
    private boolean showMinorTicks;

    /**
     * Use the color of the first series associated with this axis for the tick marks and line
     * bordering this axis.
     */
    private boolean useSeriesColor;

    /**
     * width of line stroked at the border of the axis. Defaults to the width of the grid boarder.
     */
    private final Double borderWidth = null;

    /** color of the border adjacent to the axis. */
    private final Color borderColor = null;

    /**
     * true to try and synchronize tick spacing across multiple axes so that ticks and grid lines
     * line up. This has an impact on autoscaling algorithm, however. In general, autoscaling an
     * individual axis will work better if it does not have to sync ticks
     */
    private final Ticks syncTicks = null;

    /**
     * Approximate pixel spacing between ticks on graph. Used during autoscaling. This number will
     * be an upper bound, actual spacing will be less.
     */
    private final Integer tickSpacing = 75;

    @Getter
    private final AxisType type;

    /**
     * There exists different axis
     *
     * @param type must not be null
     */
    private Axis(final AxisType type) {
        super(type.getAxisName());
        this.type = type;
    }

    /**
     * @return the xaxis
     */
    public static Axis createXAxis() {
        return new Axis(AxisType.XAXIS);
    }

    /**
     * @return the yaxis
     */
    public static Axis createYAxis() {
        return new Axis(AxisType.YAXIS);
    }

    /**
     * @return the xaxis2
     */
    public static Axis createXAxis2() {
        return new Axis(AxisType.X2AXIS);
    }

    /**
     * @return the yaxis2
     */
    public static Axis createYAxis2() {
        return new Axis(AxisType.Y2AXIS);
    }

    /**
     * @param value String value for label
     * @return fluent api style
     */
    public Axis setLabel(final String value) {
        if (null == emptyToNull(value)) {
            label = null;
            showLabel = null;
        } else {
            label = new JsString(value);
            showLabel = JsBoolean.TRUE;
        }
        return this;
    }

    /**
     * minimum value of the axis (in data units, not pixels).
     *
     * @param value any JsValue which fitting to axis data unit
     * @return fluent api style
     */
    public Axis setMin(final JsValue value) {
        this.min = value;
        return this;
    }

    /**
     * maximum value of the axis (in data units, not pixels).
     *
     * @param value any JsValue which fitting to axis data unit
     * @return fluent api style
     */
    public Axis setMax(final JsValue value) {
        this.max = value;
        return this;
    }

    /**
     * Setter for number of ticks.
     *
     * @param number of ticks.
     * @return axis.
     */
    public Axis setNumberTicks(final int number) {
        this.numberTicks = number;
        return this;
    }

    /**
     * Setter for tick interval.
     *
     * @param interval for the ticks.
     * @return axis.
     */
    public Axis setTickInterval(final String interval) {
        this.tickInterval = interval;
        return this;
    }

    @Override
    public String asJavaScriptObjectNotation() {

        this.addProperty("renderer", renderer);
        this.addProperty("tickRenderer", tickRenderer);
        this.addProperty("label", label);
        this.addProperty(labelOptions);
        this.addProperty("showLabel", showLabel);
        this.addProperty(tickOptions);
        this.addProperty("min", min);
        this.addProperty("max", max);
        if (null != tickInterval) {
            this.addProperty("tickInterval", new JsString(tickInterval));
        }
        // TODO : ... and so on

        return this.createAsJSON();
    }

    @Override
    public List<String> usedPlugins() {

        plugins.add(renderer);
        plugins.add(tickRenderer);
        return plugins.usedPlugins();
    }

}
