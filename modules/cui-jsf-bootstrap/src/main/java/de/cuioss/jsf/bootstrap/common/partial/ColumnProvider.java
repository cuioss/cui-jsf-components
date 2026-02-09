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

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.util.CuiState;
import jakarta.faces.component.StateHelper;
import lombok.NonNull;

/**
 * <p>
 * Provides Bootstrap grid system column configuration for JSF components. This partial
 * component can be mixed into other components to add Bootstrap column sizing and
 * offset capabilities.
 * </p>
 * 
 * <h2>Bootstrap Grid Integration</h2>
 * <p>
 * This provider handles the resolution of Bootstrap grid classes like "col-md-6" and
 * "offset-md-3" to create responsive layouts with properly sized columns. Components
 * using this provider can be easily integrated into Bootstrap's 12-column grid system.
 * </p>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li><b>size</b> - The column width (1-12) within Bootstrap's grid system. A value of 6 
 *       would result in a column taking up half the container width (col-md-6).</li>
 *   <li><b>offsetSize</b> - Optional offset width (1-12) to push the column from the left.
 *       An offsetSize of 3 would result in a left margin of 3 columns (offset-md-3).</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * <p>
 * Components using this provider typically delegate to it for column-related state management
 * and CSS class resolution. The provider handles both the state storage and the generation
 * of the correct CSS classes for Bootstrap's grid system.
 * </p>
 *
 * @author Oliver Wolff
 * @see ColumnCssResolver
 * @see ContentColumnProvider
 * @see LabelColumnProvider
 */
public class ColumnProvider {

    /**
     * The key for the {@link StateHelper}
     */
    private static final String OFFSET_SIZE_KEY = "offsetSize";

    /**
     * The key for the {@link StateHelper}
     */
    private static final String SIZE_KEY = "size";

    private final CuiState state;

    private final Integer defaultValue;

    /**
     * @param bridge must not be null
     */
    public ColumnProvider(@NonNull ComponentBridge bridge) {
        this(bridge, null);
    }

    public ColumnProvider(@NonNull ComponentBridge bridge, Integer defaultValue) {
        state = new CuiState(bridge.stateHelper());
        this.defaultValue = defaultValue;
    }

    /**
     * @return The size of the column
     */
    public Integer getSize() {
        return state.get(SIZE_KEY, defaultValue);
    }

    /**
     * @param size the size to set. Must be between 1-12
     */
    public void setSize(final Integer size) {
        state.put(SIZE_KEY, size);
    }

    /**
     * @return the offset-size of the column
     */
    public Integer getOffsetSize() {
        return state.get(OFFSET_SIZE_KEY);
    }

    /**
     * @param offsetSize the size to set. Must be between 1-12
     */
    public void setOffsetSize(final Integer offsetSize) {
        state.put(OFFSET_SIZE_KEY, offsetSize);
    }

    /**
     * @return the resolved column-css.
     */
    public StyleClassBuilder resolveColumnCss() {
        return new ColumnCssResolver(getSize(), getOffsetSize(), true, null).resolveColumnCss();
    }
}
