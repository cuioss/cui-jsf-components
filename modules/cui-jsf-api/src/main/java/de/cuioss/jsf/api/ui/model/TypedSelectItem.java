package de.cuioss.jsf.api.ui.model;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * TypedSelectItem is a wrapper for {@link SelectItem} which provide a Type
 * safety for stored Value
 *
 * @author Eugen Fischer
 * @param <T> bounded Parameter Type for {@link SelectItem} Value which must be
 *            {@link Serializable}
 */
@EqualsAndHashCode(callSuper = false)
@ToString
public class TypedSelectItem<T extends Serializable> extends SelectItem {

    private static final long serialVersionUID = 909687137381175245L;

    @Getter
    private T typedValue;

    /**
     * Constructor.
     */
    public TypedSelectItem() {
    }

    /**
     * Constructor.
     *
     * @param value to be set, label will be null
     */
    public TypedSelectItem(final T value) {
        this(value, null);
    }

    /**
     * Constructor.
     *
     * @param value value to be set
     * @param label value to be set
     */
    public TypedSelectItem(final T value, final String label) {
        super(value, label);
        this.setTypedValue(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setValue(final Object value) {
        super.setValue(value);
        this.typedValue = (T) value;
    }

    /**
     * Sets the typed value
     *
     * @param value to be set
     */
    public void setTypedValue(final T value) {
        super.setValue(value);
        this.typedValue = value;
    }

}
