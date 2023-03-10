package de.cuioss.jsf.components.chart.renderer.series;

import de.cuioss.jsf.components.chart.layout.shadow.IShadowDecoration;
import de.cuioss.jsf.components.chart.layout.shadow.Shadow;
import de.cuioss.jsf.components.chart.options.color.Color;
import de.cuioss.jsf.components.chart.renderer.RendererOptions;
import de.cuioss.jsf.components.chart.renderer.highlight.Highlighting;
import de.cuioss.jsf.components.chart.renderer.highlight.IHighlightDecoration;
import de.cuioss.jsf.components.js.support.JsArray;
import de.cuioss.jsf.components.js.types.JsBoolean;
import de.cuioss.jsf.components.js.types.JsDouble;
import de.cuioss.jsf.components.js.types.JsInteger;
import de.cuioss.jsf.components.js.types.JsString;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * A plugin renderer for jqPlot to draw a bar plot
 *
 * @author i000576
 * @see <a href="http://www.jqplot.com/docs/files/plugins/jqplot-barRenderer-js.html">BarRenderer
 *      </a>
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class BarRendererOptions extends RendererOptions implements
        IShadowDecoration<BarRendererOptions>, IHighlightDecoration<BarRendererOptions> {

    /** serial Version UID */
    private static final long serialVersionUID = -9152687440017064259L;

    @Delegate
    private final Shadow<BarRendererOptions> shadowDecorator;

    private final Highlighting<BarRendererOptions> highlightDecorator;

    /**
     * BarRendererOptions Constructor
     */
    public BarRendererOptions() {
        super();
        shadowDecorator = new Shadow<>(this);
        highlightDecorator = new Highlighting<>(this);
    }

    @Override
    public BarRendererOptions setHighlightMouseOver(Boolean value) {
        return highlightDecorator.setHighlightMouseOver(value);
    }

    @Override
    public BarRendererOptions setHighlightMouseDown(Boolean value) {
        return highlightDecorator.setHighlightMouseDown(value);
    }

    private JsInteger barPadding;

    /**
     * Number of pixels between adjacent bars at the same axis value.
     *
     * @param value {@link Integer}
     * @return fluent api style
     */
    public BarRendererOptions setBarPadding(final Integer value) {
        this.barPadding = new JsInteger(value);
        return this;
    }

    private JsInteger barMargin;

    /**
     * Number of pixels between groups of bars at adjacent axis values.
     *
     * @param value {@link Integer}
     * @return fluent api style
     */
    public BarRendererOptions setBarMargin(final Integer value) {
        this.barMargin = new JsInteger(value);
        return this;
    }

    private JsString barDirection;

    /**
     * ’vertical’ = up and down bars, ‘horizontal’ = side to side bars
     *
     * @param value {@link BarDirection}
     * @return fluent api style
     */
    public BarRendererOptions setBarDirection(final BarDirection value) {
        if (null == value) {
            barDirection = null;
        } else {
            barDirection = new JsString(value.getDirection());
        }
        return this;
    }

    private JsDouble barWidth;

    /**
     * Width of the bar in pixels (auto by devaul). null = calculated automatically.
     *
     * @param value {@link Double} bar with in pixel
     * @return fluent api style
     */
    public BarRendererOptions setBarWidth(final Double value) {
        this.barWidth = new JsDouble(value);
        return this;
    }

    private JsBoolean waterfall;

    /**
     * true to enable waterfall plot.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public BarRendererOptions setWaterfall(final Boolean value) {
        this.waterfall = JsBoolean.create(value);
        return this;
    }

    private JsInteger groups;

    /**
     * group bars into this many groups
     *
     * @param value {@link Integer}
     * @return fluent api style
     */
    public BarRendererOptions setGroups(final Integer value) {
        this.groups = new JsInteger(value);
        return this;
    }

    private JsBoolean varyBarColor;

    /**
     * true to color each bar of a series separately rather than have every bar of a given series
     * the same color. If used for non-stacked multiple series bar plots, user should specify a
     * separate ‘seriesColors’ array for each series. Otherwise, each series will set their bars to
     * the same color array. This option has no Effect for stacked bar charts and is disabled.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public BarRendererOptions setVaryBarColor(final Boolean value) {
        this.varyBarColor = JsBoolean.create(value);
        return this;
    }

    private JsArray<Color> highlightColors;

    /**
     * add color to use when highlighting
     *
     * @param color {@link Color}
     * @return fluent api style
     */
    public BarRendererOptions addHighlightColors(final Color color) {
        if (null == highlightColors) {
            highlightColors = new JsArray<>();
        }
        highlightColors.addValueIfNotNull(color);
        return this;
    }

    @Override
    protected void addPropertiesForJsonObject() {
        this.addProperty("barPadding", barPadding);
        this.addProperty("barMargin", barMargin);
        this.addProperty("barDirection", barDirection);
        this.addProperty("barWidth", barWidth);

        this.addProperty("waterfall", waterfall);
        this.addProperty("groups", groups);
        this.addProperty("varyBarColor", varyBarColor);
        this.addProperty("highlightColors", highlightColors);

        this.addProperties(shadowDecorator);
        this.addProperties(highlightDecorator);
    }

}
