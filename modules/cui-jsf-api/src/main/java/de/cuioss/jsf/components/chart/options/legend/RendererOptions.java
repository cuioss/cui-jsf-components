package de.cuioss.jsf.components.chart.options.legend;

import de.cuioss.jsf.components.js.support.JsObject;
import de.cuioss.jsf.components.js.types.JsBoolean;
import de.cuioss.jsf.components.js.types.JsInteger;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author i000576
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/plugins/jqplot-enhancedLegendRenderer-js.html">enhancedLegendRenderer</a>
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class RendererOptions extends JsObject {

    /**
     *
     */
    public RendererOptions() {
        super("rendererOptions");
    }

    /** serial Version UID */
    private static final long serialVersionUID = -3817562308392553207L;

    private JsInteger numberRows;

    /**
     * Maximum number of rows in the legend.
     *
     * @param value
     *            {Integer Boolean}
     * @return fluent api style
     */
    public RendererOptions setNumberRows(final Integer value) {
        this.numberRows = new JsInteger(value);
        return this;
    }

    private JsInteger numberColumns;

    /**
     * Maximum number of columns in the legend.
     *
     * @param value
     *            {Integer Boolean}
     * @return fluent api style
     */
    public RendererOptions setNumberColumns(final Integer value) {
        this.numberColumns = new JsInteger(value);
        return this;
    }

    private JsBoolean seriesToggle;

    /**
     * false to not enable series on/off toggling on the legend.
     *
     * @param value
     *            {@link Boolean}
     * @return fluent api style
     */
    public RendererOptions setSeriesToggle(final Boolean value) {
        this.seriesToggle = JsBoolean.create(value);
        return this;
    }

    private JsBoolean disableIEFading;

    /**
     * true to toggle series with a show/hide method only and not allow fading
     * in/out.
     *
     * @param value
     *            {@link Boolean}
     * @return fluent api style
     */
    public RendererOptions setDisableIEFading(final Boolean value) {
        this.disableIEFading = JsBoolean.create(value);
        return this;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("numberRows", numberRows);
        this.addProperty("numberColumns", numberColumns);
        this.addProperty("seriesToggle", seriesToggle);
        this.addProperty("disableIEFading", disableIEFading);
        return this.createAsJSON();
    }

}
