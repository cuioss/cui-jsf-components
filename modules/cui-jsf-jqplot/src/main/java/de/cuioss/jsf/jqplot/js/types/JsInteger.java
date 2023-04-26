package de.cuioss.jsf.jqplot.js.types;

import de.cuioss.jsf.jqplot.js.support.JsValue;
import lombok.Data;

/**
 * Provide safe way to act with Integer as JSON property
 *
 * @author Eugen Fischer
 */
@Data
public class JsInteger implements JsValue {

    /** serial Version UID */
    private static final long serialVersionUID = -6330792157273601368L;

    private final Integer value;

    /**
     * @param value
     */
    public JsInteger(final Integer value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        if (null == this.value) {
            return null;
        }
        return String.valueOf(this.value);
    }

}
