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
package de.cuioss.jsf.jqplot.js.types;

import de.cuioss.jsf.jqplot.js.support.JsValue;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

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
    @Serial
    private static final long serialVersionUID = -7553523540489073132L;

    private final JsValue target;

    /**
     * @param value
     */
    public JsNumber(final JsValue value) {
        target = value;
    }

    @Override
    public String getValueAsString() {
        if (null == target) {
            return null;
        }
        return target.getValueAsString();
    }

    /**
     * @param value
     * @return typed JsNumber instance
     */
    public static <N extends Number> JsNumber<N> create(final N value) {
        return JsNumberFactory.create(value);
    }

}
