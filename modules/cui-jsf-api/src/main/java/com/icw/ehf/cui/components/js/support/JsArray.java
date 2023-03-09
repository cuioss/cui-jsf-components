package com.icw.ehf.cui.components.js.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.cuioss.tools.string.Joiner;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * An array is an ordered collection of values. An array begins with [ (left bracket)<br/>
 * and ends with ] (right bracket). Values are separated by , (comma).<br/>
 * An array could be nested. Thats the reason why it's also implements the interface {@link JsValue}
 *
 * @see <a href="http://www.json.org/">json.org</a>
 * @author i000576
 * @param <T> bounded parameter T must implements the interface {@link JsValue}
 */
@ToString
@EqualsAndHashCode
public class JsArray<T extends JsValue> implements JavaScriptSupport, JsValue,
        Iterable<T> {

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
     * @return boolean indicating whether element is empty
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * An array is an ordered collection of values. An array begins with [ (left bracket) and ends
     * with ] (right bracket). Values are separated by , (comma).
     * If no items exits, <b>empty</b> array representation will be created.
     */
    @Override
    public String asJavaScriptObjectNotation() {

        if (items.isEmpty()) {
            return "[]";
        }

        final var builder = new StringBuilder()
                .append("[")
                .append(transformedArray())
                .append("]");

        return builder.toString();
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
