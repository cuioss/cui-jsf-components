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
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class JsBoolean implements JsValue {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = 6019189107773231238L;

    private final Boolean value;

    private JsBoolean(final Boolean value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        if (null == value) {
            return null;
        }
        return value.toString();
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
    public static JsBoolean create(final Boolean booleanValue) {

        if (null == booleanValue) {
            return EMPTY;
        }

        if (Boolean.TRUE.equals(booleanValue)) {
            return TRUE;
        }

        return FALSE;

    }

}
