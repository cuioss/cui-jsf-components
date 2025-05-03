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
package de.cuioss.jsf.jqplot.js.support;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.test.valueobjects.ValueObjectTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class JsArrayTest extends ValueObjectTest<JsArray<JsValue>> {

    @Test
    void shouldProduceEmptyJSONArray() {
        final var target = new JsArray<JsString>();
        assertEquals("[]", target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldProduceJSONArrayWithOneElement() {
        final var target = new JsArray<JsString>();
        target.addValueIfNotNull(new JsString("val1"));
        assertEquals("[\"val1\"]", target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldProduceJSONArrayWithSeveralElements() {
        final var target = new JsArray<JsString>();
        target.addValueIfNotNull(new JsString("val1"));
        target.addValueIfNotNull(new JsString("val2"));
        assertEquals("[\"val1\",\"val2\"]", target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldIgnoreNullValues() {
        final var target = new JsArray<JsString>();
        target.addValueIfNotNull(new JsString("val1"));
        target.addValueIfNotNull(null);
        target.addValueIfNotNull(new JsString("val2"));
        assertEquals("[\"val1\",\"val2\"]", target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldImplementIterable() {
        final var target = new JsArray<JsString>();
        assertNotNull(target.iterator());
        final var item = new JsString("val1");
        target.addValueIfNotNull(item);
        assertTrue(target.getValueAsString().contains(item.getValue()));
    }

    @Test
    void shouldSupportNestedArrays() {
        final var target = new JsArray<JsArray<JsString>>();
        final var array1 = new JsArray<JsString>();
        array1.addValueIfNotNull(new JsString("arr1-val1"));
        final var array2 = new JsArray<JsString>();
        array2.addValueIfNotNull(new JsString("arr2-val1"));
        array2.addValueIfNotNull(new JsString("arr2-val2"));
        target.addValueIfNotNull(array1);
        target.addValueIfNotNull(array2);
        final var expected = """
                [\
                ["arr1-val1"]\
                ,\
                ["arr2-val1","arr2-val2"]\
                ]""";
        assertEquals(expected, target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldHandleAddAmountOfEntries() {
        final var target = new JsArray<JsString>();
        final List<JsString> items = new ArrayList<>();
        target.addAll(items);
        // no entries expected
        assertEquals("[]", target.asJavaScriptObjectNotation());
        items.add(null);
        items.add(new JsString("val1"));
        items.add(null);
        target.addAll(items);
        assertEquals("[\"val1\"]", target.asJavaScriptObjectNotation());
    }

    @Override
    protected JsArray<JsValue> anyValueObject() {
        return new JsArray<>();
    }
}
