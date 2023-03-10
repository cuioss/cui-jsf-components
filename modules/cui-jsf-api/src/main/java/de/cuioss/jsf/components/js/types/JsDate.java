package de.cuioss.jsf.components.js.types;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.cuioss.jsf.components.js.support.JsValue;
import lombok.Data;

/**
 * Provide safe way to act with Date as JSON property.
 *
 * @author i000576
 */
@Data
public class JsDate implements JsValue {

    /** serial Version UID */
    private static final long serialVersionUID = -1956102839103846605L;

    /**
     * format support only Date YYYY-MM-DD
     */
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("'\"'yyyy-MM-dd'\"'");

    private final Date value;

    @Override
    public String getValueAsString() {
        if (null == this.value) {
            return null;
        }

        return this.dateFormat.format(this.value);
    }

}
