package de.cuioss.jsf.components.js.types;

import de.cuioss.jsf.components.js.support.JsValue;
import lombok.Data;

/**
 * JSON string value.
 *
 * @author Eugen Fischer
 */
@Data
public class JsString implements JsValue {

    private static final String FORMAT = "\"%s\"";

    /** serial Version UID */
    private static final long serialVersionUID = -619748245871932410L;

    private final String value;

    /**
     * @param value
     */
    public JsString(final String value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        if (null == value) {
            return null;
        }
        return String.format(FORMAT, value);
    }

}
