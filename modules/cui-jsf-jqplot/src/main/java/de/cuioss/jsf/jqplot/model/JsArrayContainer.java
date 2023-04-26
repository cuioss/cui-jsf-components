package de.cuioss.jsf.jqplot.model;

import de.cuioss.jsf.jqplot.js.support.JsArray;
import de.cuioss.jsf.jqplot.js.support.JsValue;

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
