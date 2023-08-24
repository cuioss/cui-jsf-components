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
package de.cuioss.jsf.jqplot.options;

import static de.cuioss.jsf.jqplot.options.DateFormatConverter.convertToJavaScriptDateFormat;
import static de.cuioss.tools.string.MoreStrings.emptyToNull;

import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.options.label.ILabelDecorator;
import de.cuioss.jsf.jqplot.options.label.Label;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * Options that will be passed to the tickRenderer, see
 * $.jqplot.AxisTickRenderer options.
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class TickOptions extends JsObject implements ILabelDecorator<TickOptions> {

    /** serial Version UID */
    private static final long serialVersionUID = 7838981710103651028L;

    @Delegate
    private final Label<TickOptions> labelDecorator;

    /**
     *
     */
    public TickOptions() {
        super("tickOptions");
        labelDecorator = new Label<>(this);
    }

    private JsBoolean showGridline;

    /**
     * wether or not to draw the gridline on the grid at this tick.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public TickOptions setShowGridline(final Boolean value) {
        showGridline = JsBoolean.create(value);
        return this;
    }

    private JsBoolean showMark;

    /**
     * wether or not to show the mark on the axis.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    public TickOptions setShowMark(final Boolean value) {
        showMark = JsBoolean.create(value);
        return this;
    }

    /**
     * Set date time format (supported are Joda / Java date formats). Manual changes
     * could be done by using {@linkplain #setFormatString(String)}, but you need to
     * take care about correct formatting <br>
     *
     * @param value pattern value (empty value is ignored)
     * @return fluent api style
     * @see <a href="http://sandbox.kendsnyder.com/date/?q=sandbox/date/#src" >
     *      Date</a>
     */
    public TickOptions setDateTimeFormat(final String value) {
        final var checkedValue = emptyToNull(value);
        if (null != checkedValue) {
            final var dateFormat = convertToJavaScriptDateFormat(checkedValue);
            setFormatString(dateFormat);
        }
        return this;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("showGridline", showGridline);
        this.addProperty("showMark", showMark);
        addProperties(labelDecorator);
        return createAsJSON();
    }

}
