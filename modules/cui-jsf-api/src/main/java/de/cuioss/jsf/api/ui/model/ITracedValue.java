package de.cuioss.jsf.api.ui.model;

import java.io.Serializable;

/**
 * Interface describe methods to provide functionality for tracing value.
 * It should be possible to change / retrieve the value and roll-back this to default.
 *
 * @author i000576
 * @param <T> bounded parameter must be {@link Serializable}
 */
public interface ITracedValue<T extends Serializable> extends Serializable {

    /**
     * @return current active value
     */
    T getValue();

    /**
     * change current active value
     *
     * @param value
     */
    void setValue(T value);

    /**
     * @return {@code true} if value was changed, {@code false} otherwise
     */
    boolean isValueChanged();

    /**
     * Execute roll-back to default value. Changes will be revert.
     *
     * @return reference of it self (fluent api style)
     */
    ITracedValue<T> rollBackToDefault();

}
