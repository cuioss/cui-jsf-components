package de.cuioss.jsf.jqplot.js.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.jqplot.js.support.JsArray;
import de.cuioss.jsf.jqplot.js.support.JsValue;
import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.test.valueobjects.ValueObjectTest;

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
        final var expected = "[" + "[\"arr1-val1\"]" + "," + "[\"arr2-val1\",\"arr2-val2\"]" + "]";
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
