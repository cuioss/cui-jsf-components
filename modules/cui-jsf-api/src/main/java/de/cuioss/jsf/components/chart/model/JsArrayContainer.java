package de.cuioss.jsf.components.chart.model;

import de.cuioss.jsf.components.js.support.JsArray;
import de.cuioss.jsf.components.js.support.JsValue;

/**
 * Mark object include {@link JsArray} representation
 *
 * @author Eugen Fischer
 */
public interface JsArrayContainer {

    /**
     * @return data as {@link JsArray}
     */
    JsArray<JsValue> getAsArray();

}
