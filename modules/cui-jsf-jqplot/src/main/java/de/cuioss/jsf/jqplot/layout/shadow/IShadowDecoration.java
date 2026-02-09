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
package de.cuioss.jsf.jqplot.layout.shadow;

import java.io.Serializable;

/**
 * Decorates shadow object which provide follow options
 *
 * @author Eugen Fischer
 * @param <T> fluent api result type
 */
public interface IShadowDecoration<T extends Serializable> {

    /**
     * wether or not to draw a shadow on the line.
     *
     * @param value Boolean
     * @return fluent api style
     */
    T setShadow(final Boolean value);

    /**
     * Shadow angle in degrees
     *
     * @param value Double
     * @return fluent api style
     */
    T setShadowAngle(final Double value);

    /**
     * Shadow offset from line in pixels
     *
     * @param value Integer
     * @return fluent api style
     */
    T setShadowOffset(final Integer value);

    /**
     * Number of times shadow is stroked, each stroke offset shadowOffset from the
     * last.
     *
     * @param value Integer
     * @return fluent api style
     */
    T setShadowDepth(final Integer value);

    /**
     * Alpha channel transparency of shadow. 0 = transparent.
     *
     * @param value css style
     * @return fluent api style
     */
    T setShadowAlpha(final String value);

}
