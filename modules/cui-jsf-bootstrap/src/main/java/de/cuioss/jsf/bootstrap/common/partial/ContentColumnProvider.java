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

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the bootstrap
 * column-css. It is computed out of the given attributes.
 * </p>
 * <h2>contentSize</h2>
 * <p>
 * The size of the column. Must be between 1-12. Is required.
 * </p>
 * <h2>contentStyleClass</h2>
 * <p>
 * Additional styleClass attributes for the column. Is not required.
 * </p>
 *
 * @author Oliver Wolff
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
