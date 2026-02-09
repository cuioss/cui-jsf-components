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
package de.cuioss.jsf.bootstrap.common.partial;

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * Utility class that resolves Bootstrap grid system CSS classes based on column configuration
 * parameters. This class generates the appropriate Bootstrap CSS classes for responsive grid
 * layouts following the 12-column system.
 * </p>
 * 
 * <h2>Bootstrap Grid System Integration</h2>
 * <p>
 * This resolver creates the proper CSS classes for Bootstrap's grid system, such as:
 * </p>
 * <ul>
 *   <li><code>col-md-6</code> - For a column taking up half the container width</li>
 *   <li><code>col-md-offset-3</code> - For a column offset by 3 grid units from the left</li>
 * </ul>
 * 
 * <h2>Input Parameters</h2>
 * <p>
 * The class requires the following configuration to generate appropriate column classes:
 * </p>
 * <ul>
 *   <li><b>size</b>: Integer (1-12) - Required when renderAsColumn=true. Defines the width of the column.</li>
 *   <li><b>offsetSize</b>: Integer (1-12) - Optional. Creates an offset for the column.</li>
 *   <li><b>renderAsColumn</b>: Boolean - Determines whether to generate column classes.</li>
 *   <li><b>styleClass</b>: String - Additional CSS classes to be appended to the result.</li>
 * </ul>
 * 
 * <h2>Validation</h2>
 * <p>
 * The resolver performs validation on the input parameters:
 * </p>
 * <ul>
 *   <li>When renderAsColumn=true, size is required and must be between 1-12</li>
 *   <li>When offsetSize is provided, it must be between 1-12</li>
 * </ul>
 * 
 * <h2>Usage Pattern</h2>
 * <p>
 * This class is typically used by components or providers that need to generate
 * Bootstrap grid system classes:
 * </p>
 * <pre>
 * // Create a half-width column with 2-column offset
 * StyleClassBuilder css = new ColumnCssResolver(6, 2, true, "my-custom-class").resolveColumnCss();
 * // Result: "col-md-6 col-md-offset-2 my-custom-class"
 * </pre>
 *
 * @author Matthias Walliczek
 * @see ColumnProvider
 * @see StyleClassBuilder
 */
@RequiredArgsConstructor
public class ColumnCssResolver {

    private static final int MAX_SIZE = 12;

    private static final int MIN_SIZE = 1;

    /**
     * Default column prefix
     */
    public static final String COL_PREFIX = "col-md-";

    /**
     * Default column offset prefix
     */
    public static final String COL_OFFSET_PREFIX = "col-md-offset-";

    private final Integer size;

    private final Integer offsetSize;

    private final boolean renderAsColumn;

    private final String styleClass;

    /**
     * @return a {@link StyleClassBuilder} containing the the column specific css
     *         classes, e.g. "col-md-4 col-md-offset-2"
     */
    public StyleClassBuilder resolveColumnCss() {
        final StyleClassBuilder builder = new StyleClassBuilderImpl();
        if (renderAsColumn) {
            requireNonNull(size, "size");
            final int sizeInt = size;
            if (sizeInt < MIN_SIZE || sizeInt > MAX_SIZE) {
                throw new IllegalArgumentException("size must be between 1 and 12");
            }

            builder.append(COL_PREFIX + size);

            if (null != offsetSize) {
                final int offsetSizeInt = offsetSize;
                if (offsetSizeInt < MIN_SIZE || offsetSizeInt > MAX_SIZE) {
                    throw new IllegalArgumentException("offsetSize must be between 1 and 12");
                }
                builder.append(COL_OFFSET_PREFIX + offsetSize);
            }
        }
        builder.append(styleClass);
        return builder;

    }

}
