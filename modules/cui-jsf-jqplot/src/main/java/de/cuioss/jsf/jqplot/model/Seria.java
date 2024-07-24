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
package de.cuioss.jsf.jqplot.model;

import java.io.Serial;
import java.util.Iterator;

import de.cuioss.jsf.jqplot.js.support.JavaScriptSupport;
import de.cuioss.jsf.jqplot.js.support.JsArray;
import de.cuioss.jsf.jqplot.js.support.JsValue;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represent a seria for {@linkplain SeriesData}. <br>
 * Will be represent is JSON Array [SeriaTupelItem1, SeriaTupelItem2, .. ,
 * SeriaTupelItemN]. <br>
 * Because SeriaTupelItem it self is a tuple of [x,y] the final JSON
 * representation will be as follow :<br>
 * [ [x1, y1] , [x2, y2], .. [xn, yn] ]
 *
 * @author Eugen Fischer
 * @param <X> bounded type for x value of {@code SeriaTupelItem}
 * @param <Y> bounded type for y value of {@code SeriaTupelItem}
 */
@ToString
@EqualsAndHashCode
public class Seria<X extends JsValue, Y extends JsValue>
        implements JavaScriptSupport, JsValue, JsArrayContainer, Iterable<JsValue> {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = 2264844925971164192L;

    private final JsArray<JsValue> data;

    /**
     *
     */
    public Seria() {
        data = new JsArray<>();
    }

    /**
     * Add values as tuple to the seria
     *
     * @param xValue x value for SeriaTupelItem
     * @param yValue y value for SeriaTupelItem
     * @return fluent api style
     */
    public Seria<X, Y> addAsTuple(final X xValue, final Y yValue) {
        return this.addTupleIfComplete(new SeriaTupelItem<>(xValue, yValue));
    }

    /**
     * Add tuple if it's not null and tuple is complete.
     * {@linkplain SeriaTupelItem#isComplete()}
     *
     * @param tuple {@code SeriaTupelItem}
     * @return fluent api style
     */
    public Seria<X, Y> addTupleIfComplete(final SeriaTupelItem<X, Y> tuple) {
        if (null != tuple && tuple.isComplete()) {
            data.addValueIfNotNull(tuple);
        }
        return this;
    }

    @Override
    public JsArray<JsValue> getAsArray() {
        return data;
    }

    @Override
    public Iterator<JsValue> iterator() {
        return data.iterator();
    }

    @Override
    public String getValueAsString() {
        return data.getValueAsString();
    }

    @Override
    public String asJavaScriptObjectNotation() {
        return data.asJavaScriptObjectNotation();
    }

}
