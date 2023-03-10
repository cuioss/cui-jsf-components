package de.cuioss.jsf.components.chart.plugin.highlighter;

import de.cuioss.jsf.components.chart.hook.PlotHookFunctionProvider;
import de.cuioss.jsf.components.js.support.JsValue;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

/**
 * tooltipContentEditorFunction used to edit/augment/replace the formatted tooltip contents.
 * Called as str = tooltipContentEditor(str, seriesIndex, pointIndex)where str is the generated
 * tooltip html and seriesIndex and pointIndex identify the data point being highlighted.
 * Should return the html for the tooltip contents.
 * This is the last possibility you should use!
 *
 * @see <a href="http://stackoverflow.com/questions/17719534/jqplot-tooltip-content-editor">
 *      TooltipContentEditor</a>
 * @author i000576
 */
@ToString
@EqualsAndHashCode
public class TooltipContentEditor implements JsValue, PlotHookFunctionProvider {

    /** serialVersionUID */
    private static final long serialVersionUID = 1088814245596596535L;

    private static final String HOOK_NAME = "tooltip_hook";

    private static final String TEMPLATE =
        "function tooltipContentEditor(str,seriesIndex,pointIndex,plot)%s";

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
        return String.format(TEMPLATE, functionContent);
    }

    @Override
    public String getIdentifier() {
        return HOOK_NAME;
    }

}
