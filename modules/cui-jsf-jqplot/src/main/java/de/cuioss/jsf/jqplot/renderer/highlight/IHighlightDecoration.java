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
package de.cuioss.jsf.jqplot.renderer.highlight;

import java.io.Serializable;

/**
 * Provide Highlight Decoration Methods
 *
 * @author Eugen Fischer
 * @param <T> at least {@link Serializable}
 */
public interface IHighlightDecoration<T extends Serializable> {

    /**
     * True to highlight slice when moused over.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    T setHighlightMouseOver(final Boolean value);

    /**
     * True to highlight when a mouse button is pressed over an area on a filled
     * plot.
     *
     * @param value {@link Boolean}
     * @return fluent api style
     */
    T setHighlightMouseDown(final Boolean value);

}
