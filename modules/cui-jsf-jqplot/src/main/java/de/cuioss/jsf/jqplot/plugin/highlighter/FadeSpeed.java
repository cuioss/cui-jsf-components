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
package de.cuioss.jsf.jqplot.plugin.highlighter;

import static java.util.Objects.requireNonNull;

import lombok.Getter;

/**
 * Fade Speed
 *
 * @author Eugen Fischer
 */
public enum FadeSpeed {

    /** */
    FAST("fast"),
    /** */
    SLOW("slow"),
    /** */
    DEFAULT("def");

    @Getter
    private final String constant;

    FadeSpeed(final String value) {
        constant = requireNonNull(value, "constant must not be null");
    }
}
