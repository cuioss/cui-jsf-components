package com.icw.ehf.cui.components.chart.renderer.marker;

import com.icw.ehf.cui.components.js.support.JsValue;
import com.icw.ehf.cui.components.js.types.JsString;

/**
 * points style on the line
 *
 * @author i000576
 * @see <a href="http://www.jqplot.com/docs/files/jqplot-markerRenderer-js.html">MarkerRenderer</a>
 */
public enum PointStyle implements JsValue {
    /** */
    DIAMOND("diamond"),
    /** */
    CIRCLE("circle"),
    /** */
    SQUARE("square"),
    /** */
    X("x"),
    /** */
    PLUS("plus"),
    /** */
    DASH("dash"),
    /** */
    FILLEDDIAMOND("filledDiamond"),
    /** */
    FILLEDCIRCLE("filledCircle"),
    /** */
    FILLEDSQUARE("filledSquare");

    private final JsString jsValue;

    PointStyle(final String value) {
        this.jsValue = new JsString(value);
    }

    @Override
    public String getValueAsString() {
        return this.jsValue.getValueAsString();
    }

}
