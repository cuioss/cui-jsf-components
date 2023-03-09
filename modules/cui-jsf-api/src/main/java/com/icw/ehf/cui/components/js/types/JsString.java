package com.icw.ehf.cui.components.js.types;

import com.icw.ehf.cui.components.js.support.JsValue;

import lombok.Data;

/**
 * JSON string value.<br/>
 * <img width="400" height="300" alt=
 * "Abstract machine string in JSON" src="http://www.json.org/string.gif"/>
 *
 * @author i000576
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
        if (null == this.value) {
            return null;
        }
        return String.format(FORMAT, this.value);
    }

}
