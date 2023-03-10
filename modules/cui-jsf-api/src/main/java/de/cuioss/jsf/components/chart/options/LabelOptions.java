package de.cuioss.jsf.components.chart.options;

import de.cuioss.jsf.components.chart.options.label.ILabelDecorator;
import de.cuioss.jsf.components.chart.options.label.Label;
import de.cuioss.jsf.components.js.support.JsObject;
import de.cuioss.jsf.components.js.types.JsBoolean;
import de.cuioss.jsf.components.js.types.JsString;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * Options passed to the label renderer.
 *
 * @author i000576
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class LabelOptions extends JsObject implements ILabelDecorator<LabelOptions> {

    /** serial Version UID */
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
        this.addProperties(labelDecorator);
        return this.createAsJSON();
    }

}
