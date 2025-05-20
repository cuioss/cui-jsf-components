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
package de.cuioss.jsf.api.components.partial;

import de.cuioss.jsf.api.components.util.CuiState;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import lombok.NonNull;

/**
 * Default implementation of {@link StyleAttributeProvider} for managing
 * inline CSS styles through the JSF component state system.
 * <p>
 * This implementation uses the {@link CuiState} wrapper to interact with the
 * underlying {@link StateHelper} mechanism for stateful components. It provides
 * a consistent implementation of the style attribute handling that integrates
 * properly with the JSF lifecycle.
 * </p>
 * <p>
 * This class is designed to be used as a delegate within UIComponent implementations
 * that need to support inline styling, providing the functionality while maintaining
 * a clean separation of concerns in the component design.
 * </p>
 * 
 * <h2>Usage Example</h2>
 * <pre>
 * public class MyComponent extends UIComponentBase implements StyleAttributeProvider {
 *     
 *     private final StyleAttributeProviderImpl styleAttributeProvider;
 *     
 *     public MyComponent() {
 *         styleAttributeProvider = new StyleAttributeProviderImpl(new ComponentBridge() {
 *             &#64;Override
 *             public StateHelper stateHelper() {
 *                 return getStateHelper();
 *             }
 *             
 *             // Other bridge methods implementation
 *         });
 *     }
 *     
 *     &#64;Override
 *     public void setStyle(String style) {
 *         styleAttributeProvider.setStyle(style);
 *     }
 *     
 *     &#64;Override
 *     public String getStyle() {
 *         return styleAttributeProvider.getStyle();
 *     }
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see StyleAttributeProvider
 * @see ComponentBridge
 * @see CuiState
 */
public class StyleAttributeProviderImpl implements StyleAttributeProvider {

    /** 
     * The key for storing the style attribute in the {@link StateHelper}.
     * This matches the expected attribute name in the JSF component model.
     */
    public static final String KEY = "style";

    /**
     * Wrapper for the component's state management
     */
    private final CuiState state;

    /**
     * Constructor creating a new StyleAttributeProviderImpl.
     * <p>
     * The provider needs a bridge to the component it belongs to in order to
     * access the component's state management.
     * </p>
     *
     * @param bridge the component bridge connecting to the owning component, must not be null
     * @throws NullPointerException if bridge is null
     */
    public StyleAttributeProviderImpl(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation stores the style value in the component's state using
     * the standard key "style".
     * </p>
     */
    @Override
    public void setStyle(String style) {
        state.put(KEY, style);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation retrieves the style value from the component's state.
     * </p>
     */
    @Override
    public String getStyle() {
        return state.get(KEY);
    }
}
