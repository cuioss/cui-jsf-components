package de.cuioss.jsf.components.chart.plugin.cursor;

import java.util.List;

import de.cuioss.jsf.components.chart.layout.Location;
import de.cuioss.jsf.components.chart.plugin.IPluginConsumer;
import de.cuioss.jsf.components.chart.plugin.PluginSupport;
import de.cuioss.jsf.components.js.support.JsObject;
import de.cuioss.jsf.components.js.types.JsBoolean;
import de.cuioss.jsf.components.js.types.JsDouble;
import de.cuioss.jsf.components.js.types.JsString;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Eugen Fischer
 * @see <a href="http://www.jqplot.com/docs/files/plugins/jqplot-cursor-js.html">Cursor</a>
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class Cursor extends JsObject implements IPluginConsumer {

    private static final long serialVersionUID = -1652272763548113051L;

    private final PluginSupport pSupport = new PluginSupport();

    /**
     *
     */
    public Cursor() {
        super("cursor");
        pSupport.add("jqplot.cursor.min.js");
    }

    private JsString style;

    /**
     * CSS spec for cursor style
     *
     * @param value
     * @return fluent api style
     */
    public Cursor setStyle(final String value) {
        style = new JsString(value);
        return this;
    }

    private JsBoolean show;

    /**
     * wether to show the cursor or not.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setShow(final Boolean value) {
        show = JsBoolean.create(value);
        return this;
    }

    private JsBoolean showTooltip;

    /**
     * show a cursor position tooltip.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setShowTooltip(final Boolean value) {
        showTooltip = JsBoolean.create(value);
        return this;
    }

    private JsBoolean followMouse;

    /**
     * Tooltip follows the mouse, it is not at a fixed location.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setFollowMouse(final Boolean value) {
        followMouse = JsBoolean.create(value);
        return this;
    }

    private JsString tooltipLocation;

    /**
     * Where to position tooltip. If followMouse is true, this is relative to the cursor, otherwise,
     * it is relative to the grid. One of ‘n’, ‘ne’, ‘e’, ‘se’, ‘s’, ‘sw’, ‘w’, ‘nw’
     *
     * @param value {@link Location}
     * @return fluent api style
     */
    public Cursor setTooltipLocation(final Location value) {
        if (null == value) {
            tooltipLocation = null;
        } else {
            tooltipLocation = new JsString(value.getConstant());
        }
        return this;
    }

    private JsDouble tooltipOffset;

    /**
     * Pixel offset of tooltip from the grid boudaries or cursor center.
     *
     * @param value {@link Double}
     * @return fluent api style
     */
    public Cursor setTooltipOffset(final Double value) {
        tooltipOffset = new JsDouble(value);
        return this;
    }

    private JsBoolean showTooltipGridPosition;

    /**
     * show the grid pixel coordinates of the mouse.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setShowTooltipGridPosition(final Boolean value) {
        showTooltipGridPosition = JsBoolean.create(value);
        return this;
    }

    private JsBoolean showTooltipUnitPosition;

    /**
     * show the unit (data) coordinates of the mouse.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setShowTooltipUnitPosition(final Boolean value) {
        showTooltipUnitPosition = JsBoolean.create(value);
        return this;
    }

    private JsBoolean showTooltipDataPosition;

    /**
     * Used with showVerticalLine to show intersecting data points in the tooltip.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setShowTooltipDataPosition(final Boolean value) {
        showTooltipDataPosition = JsBoolean.create(value);
        return this;
    }

    private JsString tooltipFormatString;

    /**
     * format string for the tooltip
     *
     * @param value
     * @return fluent api style
     */
    public Cursor setTooltipFormatString(final String value) {
        tooltipFormatString = new JsString(value);
        return this;
    }

    private JsBoolean useAxesFormatters;

    /**
     * Use the x and y axes formatters to format the text in the tooltip.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setUseAxesFormatters(final Boolean value) {
        useAxesFormatters = JsBoolean.create(value);
        return this;
    }

    private JsBoolean zoom;

    /**
     * Enable plot zooming.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setZoom(final Boolean value) {
        zoom = JsBoolean.create(value);
        return this;
    }

    private JsBoolean looseZoom;

    /**
     * Will expand zoom range to provide more rounded tick values. Works only with linear axes and
     * date axes.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setLooseZoom(final Boolean value) {
        looseZoom = JsBoolean.create(value);
        return this;
    }

    private JsBoolean clickReset;

    /**
     * Will reset plot zoom if single click on plot without drag.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setClickReset(final Boolean value) {
        clickReset = JsBoolean.create(value);
        return this;
    }

    private JsBoolean dblClickReset;

    /**
     * Will reset plot zoom if double click on plot without drag.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setDblClickReset(final Boolean value) {
        dblClickReset = JsBoolean.create(value);
        return this;
    }

    private JsBoolean showVerticalLine;

    /**
     * draw a vertical line across the plot which follows the cursor. When the line is near a data
     * point, a special legend and/or tooltip can be updated with the data values.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setShowVerticalLine(final Boolean value) {
        showVerticalLine = JsBoolean.create(value);
        return this;
    }

    private JsBoolean showHorizontalLine;

    /**
     * draw a horizontal line across the plot which follows the cursor.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setShowHorizontalLine(final Boolean value) {
        showHorizontalLine = JsBoolean.create(value);
        return this;
    }

    private JsString constrainZoomTo;

    /**
     * ’none’, ‘x’ or ‘y’
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setConstrainZoomTo(final ZoomToConstrain value) {
        if (null == value) {
            constrainZoomTo = null;
        } else {
            constrainZoomTo = new JsString(value.getConstant());
        }
        return this;
    }

    private JsBoolean showCursorLegend;

    /**
     * Replace the plot legend with an enhanced legend displaying intersection information.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Cursor setShowCursorLegend(final Boolean value) {
        showCursorLegend = JsBoolean.create(value);
        return this;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("style", style);
        this.addProperty("show", show);
        this.addProperty("showTooltip", showTooltip);
        this.addProperty("followMouse", followMouse);
        this.addProperty("tooltipLocation", tooltipLocation);
        this.addProperty("tooltipOffset", tooltipOffset);
        this.addProperty("showTooltipGridPosition", showTooltipGridPosition);
        this.addProperty("showTooltipUnitPosition", showTooltipUnitPosition);
        this.addProperty("showTooltipDataPosition", showTooltipDataPosition);
        this.addProperty("tooltipFormatString", tooltipFormatString);
        this.addProperty("useAxesFormatters", useAxesFormatters);
        this.addProperty("zoom", zoom);
        this.addProperty("looseZoom", looseZoom);
        this.addProperty("clickReset", clickReset);
        this.addProperty("dblClickReset", dblClickReset);
        this.addProperty("showVerticalLine", showVerticalLine);
        this.addProperty("showHorizontalLine", showHorizontalLine);
        this.addProperty("constrainZoomTo", constrainZoomTo);
        this.addProperty("showCursorLegend", showCursorLegend);
        return createAsJSON();
    }

    @Override
    public List<String> usedPlugins() {
        return pSupport.usedPlugins();
    }

}
