/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.options;

import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.jsf.jqplot.options.label.ILabelDecorator;
import de.cuioss.jsf.jqplot.options.label.Label;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

import java.io.Serial;

/**
 * Options passed to the label renderer.
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class LabelOptions extends JsObject implements ILabelDecorator<LabelOptions> {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = -2906679217775661045L;

    @Delegate
    private final Label<LabelOptions> labelDecorator;

    /**
     *
     */
    public LabelOptions() {
        super("labelOptions");
        labelDecorator = new Label<>(this);
    }

    // whether or not to show the tick (mark and label).
    private JsBoolean show = JsBoolean.TRUE;

    /**
     * @param value
     * @return the {@link LabelOptions}
     */
    public LabelOptions setShow(final Boolean value) {
        show = JsBoolean.create(value);
        return this;
    }

    // The text or html for the label.
    private JsString label = null;

    /**
     * @param value
     * @return the {@link LabelOptions}
     */
    public LabelOptions setLabel(final String value) {
        label = new JsString(value);
        return this;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("show", show);
        this.addProperty("label", label);
        addProperties(labelDecorator);
        return createAsJSON();
    }

}
