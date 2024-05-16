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

import jakarta.faces.component.StateHelper;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.layout.LayoutMode;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the
 * {@link LayoutMode}. It is computed out of the given attribute 'layoutMode'.
 * </p>
 * <h2>layoutMode</h2>
 * <p>
 * The String representation of the layout mode. If not set it uses the
 * configured default. Supported values are {@linkplain LayoutMode#PLAIN},
 * {@linkplain LayoutMode#FORMGROUP}, {@linkplain LayoutMode#LABEL_SR_ONLY} and
 * {@linkplain LayoutMode#COLUMN}
 * </p>
 *
 * @author Oliver Wolff
 */
public class LayoutModeProvider {

    /** The key for the {@link StateHelper} */
    private static final String KEY = "layoutMode";

    /**
     * Defines the default {@link LayoutMode} for this partial, in case no explicit
     * layoutMode is given by the client.
     */
    @NonNull
    private final LayoutMode defaultLayoutMode;

    private final CuiState state;

    /**
     * @param bridge            must not be null
     * @param defaultLayoutMode Defines the default {@link LayoutMode} for this
     *                          partial, in case no explicit layoutMode is given by
     *                          the client.
     */
    public LayoutModeProvider(@NonNull ComponentBridge bridge, @NonNull LayoutMode defaultLayoutMode) {
        state = new CuiState(bridge.stateHelper());
        this.defaultLayoutMode = defaultLayoutMode;
    }

    /**
     * Store Layout mode. Supported values are {@linkplain LayoutMode#PLAIN},
     * {@linkplain LayoutMode#FORMGROUP}, {@linkplain LayoutMode#LABEL_SR_ONLY} and
     * {@linkplain LayoutMode#COLUMN}
     *
     * @param value must not be null or empty
     */
    public void setLayoutMode(final String value) {
        state.put(KEY, value);
    }

    /**
     * Retrieve current used LayoutMode
     *
     * @return as String
     */
    public String getLayoutMode() {
        return state.get(KEY, defaultLayoutMode.name());
    }

    /**
     * Retrieve current LayoutMode in its enum-representation
     *
     * @return as Enum
     */
    public LayoutMode resolveLayoutMode() {
        return LayoutMode.transform(getLayoutMode());
    }

}
