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
package de.cuioss.jsf.jqplot.layout;

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.jqplot.js.types.JsString;
import lombok.Getter;

/**
 * compass direction, nw, n, ne, e, se, s, sw, w.
 *
 * @author Eugen Fischer
 */
public enum Location {

    /** */
    NW("nw"),
    /** */
    N("n"),
    /** */
    NE("ne"),
    /** */
    E("e"),
    /** */
    SE("se"),
    /** */
    S("s"),
    /** */
    SW("sw"),
    /** */
    W("w");

    @Getter
    private final String constant;

    @Getter
    private final JsString asJsString;

    Location(final String value) {
        constant = requireNonNull(value, "constant must not be null");
        asJsString = new JsString(constant);
    }

}
