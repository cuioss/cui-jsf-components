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

import java.io.Serializable;

/**
 * Interface describe methods to provide functionality for tracing value. It
 * should be possible to change / retrieve the value and roll-back this to
 * default.
 *
 * @author Eugen Fischer
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
