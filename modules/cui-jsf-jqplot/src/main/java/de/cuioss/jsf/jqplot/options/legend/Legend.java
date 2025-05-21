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
package de.cuioss.jsf.jqplot.options.legend;

import static java.lang.Boolean.TRUE;

import de.cuioss.jsf.jqplot.js.support.JsArray;
import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.jsf.jqplot.layout.Location;
import de.cuioss.jsf.jqplot.options.color.ColorProperty;
import de.cuioss.jsf.jqplot.plugin.IPluginConsumer;
import de.cuioss.jsf.jqplot.plugin.PluginSupport;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.util.List;

/**
 * @author Eugen Fischer
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/jqplot-core-js.html#Legend">Legend</a>
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class Legend extends JsObject implements IPluginConsumer {

    @Serial
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
        show = JsBoolean.create(value);
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
            location = null;
        } else {
            location = value.getAsJsString();
        }
        return this;
    }

    private JsArray<JsString> labels;

    /**
     * Add label to labels array. On adding a label, showLabels will be set to true
     *
     * @param value {@link String}
     * @return fluent api style
     */
    public Legend addLabel(final String value) {
        if (null != value) {
            showLabels = JsBoolean.create(TRUE);
        }
        if (null == labels) {
            labels = new JsArray<>();
        }
        labels.addValueIfNotNull(new JsString(value));
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
        showLabels = JsBoolean.create(value);
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
        showSwatch = JsBoolean.create(value);
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
            placement = null;
        } else {
            placement = new JsString(value.getConstant());
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
        border = new JsString(value);
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
        background = new JsString(value);
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
        fontFamily = new JsString(value);
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
        fontSize = new JsString(value);
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
        rowSpacing = new JsString(value);
        return this;
    }

    private final TableLegendRenderer renderer = new TableLegendRenderer();

    @Getter
    private final RendererOptions rendererOptions = new RendererOptions();

    private JsBoolean predraw;

    /**
     * Wether to draw the legend before the series or not. Used with series specific
     * legend renderers for pie, donut, mekko charts, etc.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setPredraw(final Boolean value) {
        predraw = JsBoolean.create(value);
        return this;
    }

    private JsString marginTop;

    /**
     * CSS margin for the legend DOM element. This will set an element CSS style for
     * the margin which will override any style sheet setting. The default will be
     * taken from the stylesheet.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setMarginTop(final String value) {
        marginTop = new JsString(value);
        return this;
    }

    private JsString marginRight;

    /**
     * CSS margin for the legend DOM element. This will set an element CSS style for
     * the margin which will override any style sheet setting. The default will be
     * taken from the stylesheet.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setMarginRight(final String value) {
        marginRight = new JsString(value);
        return this;
    }

    private JsString marginBottom;

    /**
     * CSS margin for the legend DOM element. This will set an element CSS style for
     * the margin which will override any style sheet setting. The default will be
     * taken from the stylesheet.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setMarginBottom(final String value) {
        marginBottom = new JsString(value);
        return this;
    }

    private JsString marginLeft;

    /**
     * CSS margin for the legend DOM element. This will set an element CSS style for
     * the margin which will override any style sheet setting. The default will be
     * taken from the stylesheet.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public Legend setMarginLeft(final String value) {
        marginLeft = new JsString(value);
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
        return createAsJSON();
    }

    @Override
    public List<String> usedPlugins() {
        plugins.add(renderer);
        return plugins.usedPlugins();
    }

}
