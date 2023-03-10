package de.cuioss.jsf.components.js.types;

import de.cuioss.jsf.components.js.support.JsValue;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author i000576
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class JsBoolean implements JsValue {

    /** serial Version UID */
    private static final long serialVersionUID = 6019189107773231238L;

    private final Boolean value;

    private JsBoolean(final Boolean value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        if (null == this.value) {
            return null;
        }
        return this.value.toString();
    }

    /** */
    public static final JsBoolean TRUE = new JsBoolean(Boolean.TRUE);

    /** */
    public static final JsBoolean FALSE = new JsBoolean(Boolean.FALSE);

    private static final JsBoolean EMPTY = new JsBoolean(null);

    /**
     * @param booleanValue
     * @return the {@link JsBoolean}
     */
    public static final JsBoolean create(final Boolean booleanValue) {

        if (null == booleanValue) {
            return EMPTY;
        }

        if (Boolean.TRUE.equals(booleanValue)) {
            return TRUE;
        }

        return FALSE;

    }

}
