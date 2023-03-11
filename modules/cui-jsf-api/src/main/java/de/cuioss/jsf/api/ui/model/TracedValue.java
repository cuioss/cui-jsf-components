package de.cuioss.jsf.api.ui.model;

import java.io.Serializable;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Class provide functionality to recognize if value was changed or not and is able to restore
 * default value.
 *
 * @author Eugen Fischer
 * @param <T> bounded type must be Serializable
 */
@ToString
@EqualsAndHashCode
public class TracedValue<T extends Serializable> implements ITracedValue<T> {

    /** serial Version UID */
    private static final long serialVersionUID = 4544062606064899863L;

    private final T defaultValue;

    @Getter
    @Setter
    private T value;

    /**
     * Initialize traced object with default value. This will be used to recognize if new value was
     * set.
     * Therefore {@linkplain #equals(Object)} method will be used.
     *
     * @param initialValue object could be null, but must follow the convention of Serializable
     *            objects.
     */
    public TracedValue(final T initialValue) {
        this.defaultValue = initialValue;
        this.value = this.defaultValue;
    }

    @Override
    public boolean isValueChanged() {
        return !Objects.equals(defaultValue, value);
    }

    @Override
    public TracedValue<T> rollBackToDefault() {
        this.value = defaultValue;
        return this;
    }

}
