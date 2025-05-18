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
 * Specialized provider for Bootstrap content column configuration. This class manages the state
 * and CSS class resolution for content areas within a component that follows Bootstrap's
 * grid system layout.
 * </p>
 *
 * <h2>Purpose and Usage</h2>
 * <p>
 * While {@link ColumnProvider} is a general-purpose column provider, ContentColumnProvider is
 * specifically designed for the content portion of components that have multiple column sections
 * (e.g., a labeled input field with separate columns for label and content).
 * </p>
 *
 * <h2>Attributes</h2>
 * <ul>
 *   <li><b>contentSize</b> - The width (1-12) of the content column within Bootstrap's grid.
 *       This provider always uses a configured default size if no specific size is set.</li>
 *   <li><b>contentStyleClass</b> - Additional CSS classes to apply to the content column.</li>
 * </ul>
 *
 * <h2>Integration with Bootstrap Grid</h2>
 * <p>
 * This provider generates appropriate Bootstrap column classes (e.g., "col-md-8") for content
 * areas, allowing components to create responsive layouts with properly sized content sections.
 * Unlike the base ColumnProvider, this class does not support column offsets as content
 * columns typically don't need offsetting within their parent containers.
 * </p>
 *
 * <h2>Usage Pattern</h2>
 * <p>
 * Components using this provider typically delegate to it for content column state management
 * and CSS class resolution:
 * </p>
 * <pre>
 * // In component initialization
 * contentColumnProvider = new ContentColumnProvider(this, 8); // Default size of 8 units
 *
 * // When rendering
 * StyleClassBuilder contentClasses = contentColumnProvider.resolveContentCss(true);
 * </pre>
 *
 * @author Oliver Wolff
 * @see ColumnProvider
 * @see LabelColumnProvider
 * @see ColumnCssResolver
 */
public class ContentColumnProvider {

    /** The key for the {@link StateHelper} */
    private static final String SIZE_KEY = "contentSize";

    /** The key for the {@link StateHelper} */
    private static final String STYLE_CLASS_KEY = "contentStyleClass";

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
    public ContentColumnProvider(@NonNull ComponentBridge bridge, @NonNull Integer defaultSize) {
        state = new CuiState(bridge.stateHelper());
        this.defaultSize = defaultSize;
    }

    /**
     * @return The size of the column
     */
    public Integer getContentSize() {
        return state.get(SIZE_KEY, defaultSize);
    }

    /**
     * @param size the size to set. Must be between 1-12
     */
    public void setContentSize(final Integer size) {
        state.put(SIZE_KEY, size);
    }

    /**
     * @return the styleClass
     */
    public String getContentStyleClass() {
        return state.get(STYLE_CLASS_KEY);
    }

    /**
     * @param styleClass the styleClass to set
     */
    public void setContentStyleClass(final String styleClass) {
        state.put(STYLE_CLASS_KEY, styleClass);
    }

    /**
     * @param renderAsColumn
     * @return the resolved column-css.
     */
    public StyleClassBuilder resolveContentCss(final boolean renderAsColumn) {
        return new ColumnCssResolver(getContentSize(), null, renderAsColumn, getContentStyleClass()).resolveColumnCss();
    }
}
