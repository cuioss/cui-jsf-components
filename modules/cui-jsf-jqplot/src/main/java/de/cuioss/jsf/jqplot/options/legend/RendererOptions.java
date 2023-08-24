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

import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.js.types.JsInteger;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Eugen Fischer
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
     * @param value {Integer Boolean}
     * @return fluent api style
     */
    public RendererOptions setNumberRows(final Integer value) {
        numberRows = new JsInteger(value);
        return this;
    }

    private JsInteger numberColumns;

    /**
     * Maximum number of columns in the legend.
     *
     * @param value {Integer Boolean}
     * @return fluent api style
     */
    public RendererOptions setNumberColumns(final Integer value) {
        numberColumns = new JsInteger(value);
        return this;
    }

    private JsBoolean seriesToggle;

    /**
     * false to not enable series on/off toggling on the legend.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public RendererOptions setSeriesToggle(final Boolean value) {
        seriesToggle = JsBoolean.create(value);
        return this;
    }

    private JsBoolean disableIEFading;

    /**
     * true to toggle series with a show/hide method only and not allow fading
     * in/out.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public RendererOptions setDisableIEFading(final Boolean value) {
        disableIEFading = JsBoolean.create(value);
        return this;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("numberRows", numberRows);
        this.addProperty("numberColumns", numberColumns);
        this.addProperty("seriesToggle", seriesToggle);
        this.addProperty("disableIEFading", disableIEFading);
        return createAsJSON();
    }

}
