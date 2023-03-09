package com.icw.ehf.cui.components.chart.model;

import com.icw.ehf.cui.components.js.support.JsArray;
import com.icw.ehf.cui.components.js.support.JsValue;

/**
 * Mark object include {@link JsArray} representation
 *
 * @author i000576
 */
public interface JsArrayContainer {

    /**
     * @return data as {@link JsArray}
     */
    JsArray<JsValue> getAsArray();

}
