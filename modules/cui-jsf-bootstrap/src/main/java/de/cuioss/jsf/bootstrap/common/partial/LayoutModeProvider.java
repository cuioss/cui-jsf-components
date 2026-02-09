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

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.layout.LayoutMode;
import jakarta.faces.component.StateHelper;
import lombok.NonNull;

/**
 * <p>
 * Provider component that manages the layout mode configuration for JSF components that
 * can be rendered in different visual styles. This provider enables components to switch
 * between different layout strategies based on the context in which they're used.
 * </p>
 * 
 * <h2>Purpose and Functionality</h2>
 * <p>
 * Many Bootstrap components need to adapt their rendering based on where they appear in
 * an application. The LayoutModeProvider allows components to:
 * </p>
 * <ul>
 *   <li>Switch between different structural layouts (plain, form group, column-based)</li>
 *   <li>Handle accessibility features like screen-reader-only labels</li>
 *   <li>Apply appropriate Bootstrap CSS classes based on the selected mode</li>
 *   <li>Store and retrieve the layout mode configuration from component state</li>
 * </ul>
 * 
 * <h2>Available Layout Modes</h2>
 * <p>
 * The provider supports the following layout modes:
 * </p>
 * <ul>
 *   <li>{@link LayoutMode#PLAIN} - Simple layout with minimal structure</li>
 *   <li>{@link LayoutMode#FORMGROUP} - Renders as a Bootstrap form-group</li>
 *   <li>{@link LayoutMode#LABEL_SR_ONLY} - Like FORMGROUP but with screen-reader-only labels</li>
 *   <li>{@link LayoutMode#COLUMN} - Column-based layout for grid structures</li>
 * </ul>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li><b>layoutMode</b> - String representation of the layout mode. If not set, uses the
 *       configured default mode. Valid values match the enum names in {@link LayoutMode}.</li>
 * </ul>
 * 
 * <h2>Usage Pattern</h2>
 * <p>
 * Components that require layout flexibility typically instantiate this provider during
 * initialization with a default mode, then delegate to it for layout-related decisions:
 * </p>
 * <pre>
 * // In component initialization
 * layoutModeProvider = new LayoutModeProvider(this, LayoutMode.FORMGROUP);
 * 
 * // When rendering
 * switch(layoutModeProvider.resolveLayoutMode()) {
 *     case PLAIN:
 *         // Render plain layout
 *         break;
 *     case FORMGROUP:
 *         // Render form-group layout
 *         break;
 *     // Handle other modes...
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @see LayoutMode
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
