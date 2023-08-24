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
package de.cuioss.jsf.jqplot.model;

import java.io.Serializable;
import java.time.temporal.Temporal;

import de.cuioss.jsf.jqplot.js.support.JsArray;
import de.cuioss.jsf.jqplot.js.support.JsValue;
import de.cuioss.jsf.jqplot.js.types.JsDateTime;
import de.cuioss.jsf.jqplot.js.types.JsDateTimeFormat;
import de.cuioss.jsf.jqplot.js.types.JsNumber;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Typed {@link TimeLineSeria} use {@link Temporal} for x values, {@link Number}
 * for y values.
 *
 * @author Eugen Fischer
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
        data = new JsArray<>();
        this.format = format;
    }

    /**
     * Add tupel
     *
     * @param date   {@link Temporal} must not be null
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
     * @param date   {@link Temporal}
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
