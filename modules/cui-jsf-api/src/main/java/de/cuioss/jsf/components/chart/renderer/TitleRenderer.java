package de.cuioss.jsf.components.chart.renderer;

import de.cuioss.jsf.components.js.support.JsValue;
import lombok.Data;

/**
 * A class for creating a DOM element for the title.
 * Value will be returned plain!
 *
 * @author i000576
 */
@Data
public class TitleRenderer implements JsValue {

    /** serialVersionUID */
    private static final long serialVersionUID = -5758045347477455164L;

    private final String value;

    @Override
    public String getValueAsString() {
        return value;
    }
}
