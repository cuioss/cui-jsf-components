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
package de.cuioss.jsf.bootstrap.common.partial;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.util.CuiState;
import jakarta.faces.component.StateHelper;
import lombok.NonNull;

/**
 * <p>
 * Specialized provider for Bootstrap label column configuration. This class manages the state
 * and CSS class resolution for label areas within form components that follow Bootstrap's
 * grid system layout.
 * </p>
 * 
 * <h2>Purpose and Usage</h2>
 * <p>
 * This provider is designed specifically for the label portion of form components that have
 * separate columns for labels and input fields/content (such as form groups, labeled containers, etc.).
 * It works as a complementary provider to {@link ContentColumnProvider}, with both providers
 * typically used together to create proper form layouts.
 * </p>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li><b>labelSize</b> - The width (1-12) of the label column within Bootstrap's grid.
 *       This provider always uses a configured default size if no specific size is set.</li>
 *   <li><b>labelStyleClass</b> - Additional CSS classes to apply to the label column.</li>
 * </ul>
 * 
 * <h2>Integration with Bootstrap Grid</h2>
 * <p>
 * This provider generates appropriate Bootstrap column classes (e.g., "col-md-4") for label
 * areas, supporting responsive form layouts. Similar to {@link ContentColumnProvider}, this class
 * does not support column offsets as label columns typically don't need offsetting within
 * their parent containers.
 * </p>
 * 
 * <h2>Usage Pattern</h2>
 * <p>
 * Components using this provider typically delegate to it for label column state management
 * and CSS class resolution, often alongside a ContentColumnProvider:
 * </p>
 * <pre>
 * // In component initialization
 * labelColumnProvider = new LabelColumnProvider(this, 4); // Default size of 4 units
 * contentColumnProvider = new ContentColumnProvider(this, 8); // Default size of 8 units
 * 
 * // When rendering
 * StyleClassBuilder labelClasses = labelColumnProvider.resolveLabelCss(true);
 * StyleClassBuilder contentClasses = contentColumnProvider.resolveContentCss(true);
 * </pre>
 * 
 * <h2>Form Layout Coordination</h2>
 * <p>
 * To ensure proper Bootstrap grid behavior, the sum of label and content column sizes should
 * not exceed 12 (the total width of Bootstrap's grid system). Components using both providers
 * should coordinate the sizes accordingly.
 * </p>
 *
 * @author Oliver Wolff
 * @see ContentColumnProvider
 * @see ColumnProvider
 * @see ColumnCssResolver
 */
public class LabelColumnProvider {

    /** The key for the {@link StateHelper} */
    private static final String SIZE_KEY = "labelSize";

    /** The key for the {@link StateHelper} */
    private static final String STYLE_CLASS_KEY = "labelStyleClass";

    private final CuiState state;

    /**
     * Defines the default size for this partial, in case no explicit size is given
     * by the client.
     */
    private final Integer defaultSize;

    /**
     * @param bridge      must not be null
     * @param defaultSize the default size for this partial, in case no explicit
     *                    size is given by the client.
     */
    public LabelColumnProvider(@NonNull ComponentBridge bridge, @NonNull Integer defaultSize) {
        state = new CuiState(bridge.stateHelper());
        this.defaultSize = defaultSize;
    }

    /**
     * @return The size of the column
     */
    public Integer getLabelSize() {
        return state.get(SIZE_KEY, defaultSize);
    }

    /**
     * @param size the size to set. Must be between 1-12
     */
    public void setLabelSize(final Integer size) {
        state.put(SIZE_KEY, size);
    }

    /**
     * @return the styleClass
     */
    public String getLabelStyleClass() {
        return state.get(STYLE_CLASS_KEY);
    }

    /**
     * @param styleClass the styleClass to set
     */
    public void setLabelStyleClass(final String styleClass) {
        state.put(STYLE_CLASS_KEY, styleClass);
    }

    /**
     * @param renderAsColumn
     * @return the resolved column-css.
     */
    public StyleClassBuilder resolveLabelCss(final boolean renderAsColumn) {
        return new ColumnCssResolver(getLabelSize(), null, renderAsColumn, getLabelStyleClass()).resolveColumnCss();
    }
}
