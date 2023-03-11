package de.cuioss.jsf.components.js.types;

import de.cuioss.jsf.components.js.support.JsValue;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * JsNumber consolidates {@link JsValue} with {@link Number} type content
 *
 * @author Eugen Fischer
 * @param <T> bounded {@link Number} type
 */
@ToString
@EqualsAndHashCode
public class JsNumber<T extends Number> implements JsValue {

    /** serial version UID */
    private static final long serialVersionUID = -7553523540489073132L;

    private final JsValue target;

    /**
     * @param value
     */
    public JsNumber(final JsValue value) {
        this.target = value;
    }

    @Override
    public String getValueAsString() {
        if (null == this.target) {
            return null;
        }
        return this.target.getValueAsString();
    }

    /**
     * @param value
     * @return typed JsNumber instance
     */
    public static <N extends Number> JsNumber<N> create(final N value) {
        return JsNumberFactory.create(value);
    }

}
