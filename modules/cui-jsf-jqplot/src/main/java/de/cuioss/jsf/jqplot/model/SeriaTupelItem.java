package de.cuioss.jsf.jqplot.model;

import de.cuioss.jsf.jqplot.js.support.JsArray;
import de.cuioss.jsf.jqplot.js.support.JsValue;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A single seria item which composed of two elements [x,y].
 * Only if both are available the item has a valid value.
 *
 * @author Eugen Fischer
 * @param <X> bounded type for x value
 * @param <Y> bounded type for y value
 */
@ToString
@EqualsAndHashCode
public class SeriaTupelItem<X extends JsValue, Y extends JsValue> implements JsValue {

    /** serial ersion UID */
    private static final long serialVersionUID = -688482351626904626L;

    private final JsArray<JsValue> array;

    /**
     * @param x
     * @param y
     */
    public SeriaTupelItem(final X x, final Y y) {
        if (null != x && null != y) {
            array = new JsArray<>();
            array.addValueIfNotNull(x);
            array.addValueIfNotNull(y);
        } else {
            array = null;
        }
    }

    /**
     * @return {@code true} if both values are available, {@code false} otherwise
     */
    public boolean isComplete() {
        return null != array;
    }

    @Override
    public String getValueAsString() {
        if (isComplete()) {
            return array.asJavaScriptObjectNotation();
        }
        return null;
    }

}
