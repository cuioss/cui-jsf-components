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
package de.cuioss.jsf.jqplot.options.legend;

import static java.util.Objects.requireNonNull;

import lombok.Getter;

/**
 * "insideGrid" places legend inside the grid area of the plot. "outsideGrid"
 * places the legend outside the grid but inside the plot container, shrinking
 * the grid to accomodate the legend. "inside" synonym for "insideGrid",
 * "outside" places the legend ouside the grid area, but does not shrink the
 * grid which can cause the legend to overflow the plot container.
 *
 * @author Eugen Fischer
 */
public enum Placement {

    /** */
    INSIDE("insideGrid"),
    /** */
    OUTSIDE("outsideGrid");

    @Getter
    private final String constant;

    Placement(final String value) {
        constant = requireNonNull(value, "constant must not be null");
    }
}
