package com.icw.ehf.cui.components.chart.model;

import java.io.Serializable;
import java.time.temporal.Temporal;

import com.icw.ehf.cui.components.js.support.JsArray;
import com.icw.ehf.cui.components.js.support.JsValue;
import com.icw.ehf.cui.components.js.types.JsDateTime;
import com.icw.ehf.cui.components.js.types.JsDateTimeFormat;
import com.icw.ehf.cui.components.js.types.JsNumber;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Typed {@link TimeLineSeria} use {@link Temporal} for x values, {@link Number} for y values.
 *
 * @author i000576
 * @param <T> at least {@link Number}
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class TimeLineSeria<T extends Number> implements JsArrayContainer, Serializable {

    private static final long serialVersionUID = 4435082055226755388L;

    @NonNull
    private final JsDateTimeFormat format;

    private final JsArray<JsValue> data;

    /**
     * DateDoubleSeria need to be initialized for later usage
     *
     * @param format {@link JsDateTimeFormat} granularity for display
     */
    public TimeLineSeria(@NonNull final JsDateTimeFormat format) {
        super();
        this.data = new JsArray<>();
        this.format = format;
    }

    /**
     * Add tupel
     *
     * @param date {@link Temporal} must not be null
     * @param number {@link Number} must not be null
     * @return fluent api style
     * @throws NullPointerException if date or value is null
     */
    public TimeLineSeria<T> add(@NonNull final Temporal date, @NonNull final T number) {

        final var xValue = JsDateTime.builder().formatter(format).value(date).build();

        final JsNumber<T> yValue = JsNumber.create(number);

        final var tupel = new SeriaTupelItem<>(xValue, yValue);

        data.addValueIfNotNull(tupel);

        return this;
    }

    /**
     * Add tupel if both parameters are available, otherwise ignore silently
     *
     * @param date {@link Temporal}
     * @param number {@link Number}
     * @return fluent api style
     */
    public TimeLineSeria<T> addIfNotEmpty(final Temporal date, final T number) {
        if (null != date && null != number) {
            return this.add(date, number);
        }
        return this;
    }

    @Override
    public JsArray<JsValue> getAsArray() {
        return data;
    }
}
