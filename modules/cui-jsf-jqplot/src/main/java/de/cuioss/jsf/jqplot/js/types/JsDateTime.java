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

import java.io.Serial;
import java.time.temporal.Temporal;

import de.cuioss.jsf.jqplot.js.support.JsValue;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/**
 * Provide safe way to act with Date as JSON property.
 *
 * @author Eugen Fischer
 */
@ToString(of = { "value" })
@EqualsAndHashCode(of = { "value" })
@Builder
@Value
public class JsDateTime implements JsValue {

    @Serial
    private static final long serialVersionUID = -726077446906162610L;

    @SuppressWarnings("squid:S1948") // All known implementations are Serializable
    private final Temporal value;

    @NonNull
    private final JsDateTimeFormat formatter;

    @Override
    public String getValueAsString() {
        if (null == value) {
            return null;
        }
        return new JsString(formatter.format(value)).getValueAsString();
    }

}
