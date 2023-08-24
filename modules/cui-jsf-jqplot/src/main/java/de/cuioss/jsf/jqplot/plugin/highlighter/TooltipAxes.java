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
package de.cuioss.jsf.jqplot.plugin.highlighter;

import static java.util.Objects.requireNonNull;

import lombok.Getter;

/**
 * Which axes to display in tooltip, 'x', 'y' or 'both', 'xy' or 'yx' 'both' and
 * 'xy' are equivalent, 'yx' reverses order of labels.
 *
 * @author Eugen Fischer
 */
public enum TooltipAxes {

    /** */
    ONLY_X("x"),
    /** */
    ONLY_Y("y"),
    /** */
    BOTH_XY("xy"),
    /** */
    BOTH_YX("xy");

    @Getter
    private final String constant;

    TooltipAxes(final String value) {
        constant = requireNonNull(value, "constant must not be null");
    }
}
