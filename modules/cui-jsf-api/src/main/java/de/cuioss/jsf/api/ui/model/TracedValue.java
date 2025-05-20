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
package de.cuioss.jsf.api.ui.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class provide functionality to recognize if value was changed or not and is
 * able to restore default value.
 *
 * @author Eugen Fischer
 * @param <T> bounded type must be Serializable
 */
@ToString
@EqualsAndHashCode
public class TracedValue<T extends Serializable> implements ITracedValue<T> {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = 4544062606064899863L;

    private final T defaultValue;

    @Getter
    @Setter
    private T value;

    /**
     * Initialize traced object with default value. This will be used to recognize
     * if new value was set. Therefore {@linkplain #equals(Object)} method will be
     * used.
     *
     * @param initialValue object could be null, but must follow the convention of
     *                     Serializable objects.
     */
    public TracedValue(final T initialValue) {
        defaultValue = initialValue;
        value = defaultValue;
    }

    @Override
    public boolean isValueChanged() {
        return !Objects.equals(defaultValue, value);
    }

    @Override
    public TracedValue<T> rollBackToDefault() {
        value = defaultValue;
        return this;
    }

}
