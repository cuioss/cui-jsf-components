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
package de.cuioss.jsf.jqplot.plugin.highlighter;

import de.cuioss.jsf.jqplot.hook.PlotHookFunctionProvider;
import de.cuioss.jsf.jqplot.js.support.JsValue;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

/**
 * tooltipContentEditorFunction used to edit/augment/replace the formatted
 * tooltip contents. Called as str = tooltipContentEditor(str, seriesIndex,
 * pointIndex)where str is the generated tooltip html and seriesIndex and
 * pointIndex identify the data point being highlighted. Should return the html
 * for the tooltip contents. This is the last possibility you should use!
 *
 * @see <a href=
 *      "http://stackoverflow.com/questions/17719534/jqplot-tooltip-content-editor">
 *      TooltipContentEditor</a>
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode
public class TooltipContentEditor implements JsValue, PlotHookFunctionProvider {

    /** serialVersionUID */
    @Serial
    private static final long serialVersionUID = 1088814245596596535L;

    private static final String HOOK_NAME = "tooltip_hook";

    private static final String TEMPLATE = "function tooltipContentEditor(str,seriesIndex,pointIndex,plot)%s";

    /**
     * You have to care about to complete the JavaScript function:
     * {@code function tooltipContentEditor(str,seriesIndex,pointIndex,plot)%s}"
     * default function content is {return "";};
     */
    @Setter
    private String functionContent = "{return \"\";};";

    @Override
    public String getValueAsString() {
        return "tooltipContentEditor";
    }

    @Override
    public String getHookFunctionCode() {
        return TEMPLATE.formatted(functionContent);
    }

    @Override
    public String getIdentifier() {
        return HOOK_NAME;
    }

}
