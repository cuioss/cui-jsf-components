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
package de.cuioss.jsf.jqplot.js.support;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;

/**
 * @author Oliver Wolff
 * @param <T> at least {@link JsValue}
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class JsProperty<T extends JsValue> implements JavaScriptSupport {

    private static final String JSON_FORMAT = "%s:%s";

    /** serial version UID */
    @Serial
    private static final long serialVersionUID = 8197592435718895633L;

    @Getter
    private final String propertyName;

    private final T propertyValue;

    @Override
    public String asJavaScriptObjectNotation() {
        if (null == propertyValue) {
            return null;
        }

        final var valueAsString = propertyValue.getValueAsString();

        if (null == valueAsString) {
            return null;
        }

        return JSON_FORMAT.formatted(propertyName, valueAsString);
    }

}
