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
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the bootstrap
 * column-css. It is computed out of the given attributes.
 * </p>
 * <h2>offsetSize</h2>
 * <p>
 * The offset-size of the column. Must be between 1-12. if it is not set, there
 * will be no offset at all.
 * </p>
 * <h2>size</h2>
 * <p>
 * The size of the column. Must be between 1-12. Is required.
 * </p>
 *
 * @author Oliver Wolff
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
