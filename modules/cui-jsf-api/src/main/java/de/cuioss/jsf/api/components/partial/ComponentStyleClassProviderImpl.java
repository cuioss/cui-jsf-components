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

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.util.CuiState;
import jakarta.faces.component.StateHelper;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Default implementation for {@link ComponentStyleClassProvider} that manages the 
 * component's style class attributes using JSF's state management facilities.
 * This implementation distinguishes between the "local" style class (as set by the 
 * user) and the "computed" style class (which includes component-specific style classes).
 * </p>
 * 
 * <h2>State Management</h2>
 * <p>
 * This implementation uses {@link CuiState} to handle state management, which requires
 * a {@link ComponentBridge} to be provided at construction time. Component state is 
 * stored using two separate keys:
 * </p>
 * <ul>
 *   <li>{@link #KEY} - Stores the final computed style class including component-specific classes</li>
 *   <li>{@link #LOCAL_STYLE_CLASS_KEY} - Stores only the user-provided style classes</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 * <p>
 * This implementation is designed to be used as a delegate within JSF components:
 * </p>
 * <pre>
 * public class MyComponent extends UIComponentBase implements ComponentStyleClassProvider {
 *     
 *     private final ComponentStyleClassProviderImpl styleClassProvider;
 *     
 *     public MyComponent() {
 *         styleClassProvider = new ComponentStyleClassProviderImpl(new ComponentBridge() {
 *             &#64;Override
 *             public StateHelper stateHelper() {
 *                 return getStateHelper();
 *             }
 *         });
 *     }
 *     
 *     &#64;Override
 *     public String getStyleClass() {
 *         return styleClassProvider.getStyleClass();
 *     }
 *     
 *     &#64;Override
 *     public void setStyleClass(String styleClass) {
 *         styleClassProvider.setStyleClass(styleClass);
 *     }
 *     
 *     &#64;Override
 *     public String computeAndStoreFinalStyleClass(StyleClassBuilder componentSpecificStyleClass) {
 *         return styleClassProvider.computeAndStoreFinalStyleClass(componentSpecificStyleClass);
 *     }
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ComponentStyleClassProvider
 * @see ComponentBridge
 * @see CuiState
 */
public class ComponentStyleClassProviderImpl implements ComponentStyleClassProvider {

    /**
     * The key for the {@link StateHelper} that stores the final computed style class
     * (including component-specific style classes).
     */
    public static final String KEY = "styleClass";

    /**
     * The key for the {@link StateHelper} that stores only the user-provided style class
     * (excluding component-specific style classes).
     */
    public static final String LOCAL_STYLE_CLASS_KEY = "localStyleClass";

    private final CuiState state;

    /**
     * Constructor that initializes the state management system.
     * 
     * @param bridge to be used for accessing the component's {@link StateHelper}.
     *              Must not be {@code null}.
     * @throws NullPointerException if {@code bridge} is {@code null}
     */
    public ComponentStyleClassProviderImpl(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    @Override
    public void setStyleClass(String styleClass) {
        state.put(KEY, styleClass);
        state.put(LOCAL_STYLE_CLASS_KEY, styleClass);
    }

    @Override
    public String computeAndStoreFinalStyleClass(StyleClassBuilder componentSpecificStyleClass) {
        String styleClass = componentSpecificStyleClass.append(getLocalStyleClassBuilder()).getStyleClass();
        state.put(KEY, styleClass);
        return styleClass;
    }

    @Override
    public String getStyleClass() {
        return state.get(KEY);
    }

    /**
     * Creates a StyleClassBuilder containing the user-provided style classes.
     * This method is used internally to combine user-provided style classes
     * with component-specific ones.
     * 
     * @return a new {@link StyleClassBuilder} instance containing the user-provided style classes
     */
    private StyleClassBuilder getLocalStyleClassBuilder() {
        return new StyleClassBuilderImpl(state.get(LOCAL_STYLE_CLASS_KEY));
    }
}
