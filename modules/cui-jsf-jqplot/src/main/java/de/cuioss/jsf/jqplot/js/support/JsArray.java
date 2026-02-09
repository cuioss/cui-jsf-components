/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.js.support;

import de.cuioss.tools.string.Joiner;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An array is an ordered collection of values. An array begins with [ (left
 * bracket)<br>
 * and ends with ] (right bracket). Values are separated by, (comma).<br>
 * An array could be nested. That's the reason why it's also implements the
 * interface {@link JsValue}
 *
 * @param <T> bounded parameter T must implement the interface {@link JsValue}
 * @author Eugen Fischer
 * @see <a href="http://www.json.org/">json.org</a>
 */
@ToString
@EqualsAndHashCode
public class JsArray<T extends JsValue> implements JavaScriptSupport, JsValue, Iterable<T> {

    @Serial
    private static final long serialVersionUID = 4745761442808870666L;

    private final ArrayList<T> items = new ArrayList<>();

    /**
     * @param value
     * @return {@link JsArray}
     */
    public JsArray<T> addValueIfNotNull(final T value) {
        if (null != value) {
            items.add(value);
        }
        return this;
    }

    /**
     * @param itemList
     * @return {@link JsArray}
     */
    public JsArray<T> addAll(final List<T> itemList) {
        for (T item : itemList) {
            this.addValueIfNotNull(item);
        }
        return this;
    }

    private String transformedArray() {
        final List<String> stringRepresentation = new ArrayList<>(items.size());
        for (JsValue item : items) {
            stringRepresentation.add(item.getValueAsString());
        }
        return Joiner.on(",").join(stringRepresentation);
    }

    /**
     * @return boolean indicating whether the element is empty
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * An array is an ordered collection of values. An array begins with [ (left
     * bracket) and ends with ] (right bracket). Values are separated by, (comma).
     * If no items exist, <b>empty</b> array representation will be created.
     */
    @Override
    public String asJavaScriptObjectNotation() {

        if (items.isEmpty()) {
            return "[]";
        }

        return "[" + transformedArray() + "]";
    }

    @Override
    public String getValueAsString() {
        return asJavaScriptObjectNotation();
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

}
