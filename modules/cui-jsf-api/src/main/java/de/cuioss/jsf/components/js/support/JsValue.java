package de.cuioss.jsf.components.js.support;

import java.io.Serializable;

/**
 * @author Oliver Wolff
 *
 */
public interface JsValue extends Serializable {

    /**
     * @return String representation of value which respect java script rules
     */
    String getValueAsString();

}
