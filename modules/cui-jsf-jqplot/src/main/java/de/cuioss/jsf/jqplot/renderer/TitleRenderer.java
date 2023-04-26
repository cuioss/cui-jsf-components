package de.cuioss.jsf.jqplot.renderer;

import de.cuioss.jsf.jqplot.js.support.JsValue;
import lombok.Data;

/**
 * A class for creating a DOM element for the title.
 * Value will be returned plain!
 *
 * @author Eugen Fischer
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
