package de.cuioss.jsf.components.chart.options.legend;

import static java.lang.Boolean.TRUE;

import java.util.List;

import de.cuioss.jsf.components.chart.layout.Location;
import de.cuioss.jsf.components.chart.options.color.ColorProperty;
import de.cuioss.jsf.components.chart.plugin.IPluginConsumer;
import de.cuioss.jsf.components.chart.plugin.PluginSupport;
import de.cuioss.jsf.components.js.support.JsArray;
import de.cuioss.jsf.components.js.support.JsObject;
import de.cuioss.jsf.components.js.types.JsBoolean;
import de.cuioss.jsf.components.js.types.JsString;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Eugen Fischer
 * @see <a href="http://www.jqplot.com/docs/files/jqplot-core-js.html#Legend">Legend</a>
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class Legend extends JsObject implements IPluginConsumer {

    private static final long serialVersionUID = -8754508301630460493L;

    /**
     * Default constructor
     */
    public Legend() {
        super("legend");
    }

    /** serial Version UID */

    private final PluginSupport plugins = new PluginSupport();

    private JsBoolean show;

    /**
     * Wether to display the legend on the graph. <br>
     * <b>Default</b> value is <i>false</i>
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setShow(final Boolean value) {
        this.show = JsBoolean.create(value);
        return this;
    }

    private JsString location;

    /**
     * Placement of the legend.
     *
     * @param value {@link Location}
     * @return fluent api style
     */
    public Legend setLocation(final Location value) {
        if (null == value) {
            this.location = null;
        } else {
            this.location = value.getAsJsString();
        }
        return this;
    }

    private JsArray<JsString> labels;

    /**
     * Add label to labels array.
     * On adding a label, showLabels will be set to true
     *
     * @param value {@link String}
     * @return fluent api style
     */
    public Legend addLabel(final String value) {
        if (null != value) {
            this.showLabels = JsBoolean.create(TRUE);
        }
        if (null == labels) {
            this.labels = new JsArray<>();
        }
        this.labels.addValueIfNotNull(new JsString(value));
        return this;
    }

    private JsBoolean showLabels;

    /**
     * Wether to display the legend on the graph.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setShowLabels(final Boolean value) {
        this.showLabels = JsBoolean.create(value);
        return this;
    }

    private JsBoolean showSwatch;

    /**
     * true to show the color swatches on the legend.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setShowSwatch(final Boolean value) {
        this.showSwatch = JsBoolean.create(value);
        return this;
    }

    private JsString placement;

    /**
     * “insideGrid” places legend inside the grid area of the plot.
     *
     * @param value {@link Placement}
     * @return fluent api style
     */
    public Legend setPlacement(final Placement value) {
        if (null == value) {
            this.placement = null;
        } else {
            this.placement = new JsString(value.getConstant());
        }
        return this;
    }

    private JsString border;

    /**
     * css spec for the border around the legend box.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setBorder(final String value) {
        this.border = new JsString(value);
        return this;
    }

    private JsString background;

    /**
     * css spec for the background of the legend box.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setBackground(final String value) {
        this.background = new JsString(value);
        return this;
    }

    private final ColorProperty textColor = new ColorProperty("textColor");

    /**
     * css color spec for the legend text.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setTextColor(final String value) {
        textColor.setColorValue(value);
        return this;
    }

    private JsString fontFamily;

    /**
     * css font-family spec for the legend text.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setFontFamily(final String value) {
        this.fontFamily = new JsString(value);
        return this;
    }

    private JsString fontSize;

    /**
     * css font-size spec for the legend text.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setFontSize(final String value) {
        this.fontSize = new JsString(value);
        return this;
    }

    private JsString rowSpacing;

    /**
     * css padding-top spec for the rows in the legend.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setRowSpacing(final String value) {
        this.rowSpacing = new JsString(value);
        return this;
    }

    private final TableLegendRenderer renderer = new TableLegendRenderer();

    @Getter
    private final RendererOptions rendererOptions = new RendererOptions();

    private JsBoolean predraw;

    /**
     * Wether to draw the legend before the series or not. Used with series specific legend
     * renderers for pie, donut, mekko charts, etc.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setPredraw(final Boolean value) {
        this.predraw = JsBoolean.create(value);
        return this;
    }

    private JsString marginTop;

    /**
     * CSS margin for the legend DOM element. This will set an element CSS style for the margin
     * which will override any style sheet setting. The default will be taken from the stylesheet.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setMarginTop(final String value) {
        this.marginTop = new JsString(value);
        return this;
    }

    private JsString marginRight;

    /**
     * CSS margin for the legend DOM element. This will set an element CSS style for the margin
     * which will override any style sheet setting. The default will be taken from the stylesheet.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setMarginRight(final String value) {
        this.marginRight = new JsString(value);
        return this;
    }

    private JsString marginBottom;

    /**
     * CSS margin for the legend DOM element. This will set an element CSS style for the margin
     * which will override any style sheet setting. The default will be taken from the stylesheet.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setMarginBottom(final String value) {
        this.marginBottom = new JsString(value);
        return this;
    }

    private JsString marginLeft;

    /**
     * CSS margin for the legend DOM element. This will set an element CSS style for the margin
     * which will override any style sheet setting. The default will be taken from the stylesheet.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setMarginLeft(final String value) {
        this.marginLeft = new JsString(value);
        return this;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("show", show);
        this.addProperty("location", location);
        this.addProperty("labels", labels);
        this.addProperty("showLabels", showLabels);
        this.addProperty("showSwatch", showSwatch);
        this.addProperty("placement", placement);
        this.addProperty("border", border);
        this.addProperty("background", background);
        this.addProperty(textColor);
        this.addProperty("fontFamily", fontFamily);
        this.addProperty("fontSize", fontSize);
        this.addProperty("rowSpacing", rowSpacing);
        this.addProperty("renderer", renderer);
        this.addProperty(rendererOptions);
        this.addProperty("predraw", predraw);
        this.addProperty("marginTop", marginTop);
        this.addProperty("marginRight", marginRight);
        this.addProperty("marginBottom", marginBottom);
        this.addProperty("marginLeft", marginLeft);
        return this.createAsJSON();
    }

    @Override
    public List<String> usedPlugins() {
        plugins.add(renderer);
        return plugins.usedPlugins();
    }

}
