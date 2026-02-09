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
import lombok.Data;

import java.io.Serial;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provide safe way to act with Date as JSON property.
 *
 * @author Eugen Fischer
 */
@Data
public class JsDate implements JsValue {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = -1956102839103846605L;

    /**
     * format support only Date YYYY-MM-DD
     */
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("'\"'yyyy-MM-dd'\"'");

    private final Date value;

    @Override
    public String getValueAsString() {
        if (null == value) {
            return null;
        }

        return dateFormat.format(value);
    }

}
