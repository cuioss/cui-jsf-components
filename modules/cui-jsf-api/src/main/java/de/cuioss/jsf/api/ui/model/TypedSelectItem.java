/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.ui.model;

import jakarta.faces.model.SelectItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

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

    @Serial
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
        typedValue = (T) value;
    }

    /**
     * Sets the typed value
     *
     * @param value to be set
     */
    public void setTypedValue(final T value) {
        super.setValue(value);
        typedValue = value;
    }

}
