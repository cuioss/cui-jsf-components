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
package de.cuioss.jsf.jqplot.js.types;

import de.cuioss.jsf.jqplot.js.support.JsValue;
import lombok.Data;

import java.io.Serial;

/**
 * JSON string value.
 *
 * @author Eugen Fischer
 */
@Data
public class JsString implements JsValue {

    private static final String FORMAT = "\"%s\"";

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = -619748245871932410L;

    private final String value;

    /**
     * @param value
     */
    public JsString(final String value) {
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        if (null == value) {
            return null;
        }
        return FORMAT.formatted(value);
    }

}
