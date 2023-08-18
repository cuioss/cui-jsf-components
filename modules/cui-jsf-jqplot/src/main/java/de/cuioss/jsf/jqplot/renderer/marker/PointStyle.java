/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.renderer.marker;

import de.cuioss.jsf.jqplot.js.support.JsValue;
import de.cuioss.jsf.jqplot.js.types.JsString;

/**
 * points style on the line
 *
 * @author Eugen Fischer
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/jqplot-markerRenderer-js.html">MarkerRenderer</a>
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
        jsValue = new JsString(value);
    }

    @Override
    public String getValueAsString() {
        return jsValue.getValueAsString();
    }

}
