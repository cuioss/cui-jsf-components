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
package de.cuioss.jsf.api.components.util;

import jakarta.faces.component.StateHelper;
import org.omnifaces.util.State;

import java.io.Serializable;

/**
 * <p>Extension to OmniFaces {@link State} utility class that provides additional convenience methods
 * for handling primitive types when working with {@link StateHelper} in JSF components.</p>
 * 
 * <p>The JSF {@link StateHelper} interface manages component state across requests, but working directly
 * with it can be cumbersome. OmniFaces' {@link State} class simplifies this, and this extension
 * adds further convenience methods specifically for primitive boolean and int types.</p>
 * 
 * <p>Usage example:</p>
 * <pre>
 * public class MyComponent extends UIComponentBase {
 *     private final CuiState state;
 *     
 *     public MyComponent() {
 *         state = new CuiState(getStateHelper());
 *     }
 *     
 *     public boolean isDisabled() {
 *         return state.getBoolean("disabled");
 *     }
 *     
 *     public void setDisabled(boolean disabled) {
 *         state.put("disabled", disabled);
 *     }
 *     
 *     public int getTabIndex() {
 *         return state.getInt("tabIndex", 0);
 *     }
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @see State
 * @see StateHelper
 */
public class CuiState extends State {

    /**
     * <p>Creates a new instance of CuiState for the given StateHelper.</p>
     *
     * @param stateHelper The JSF component StateHelper to be wrapped, must not be null
     */
    public CuiState(final StateHelper stateHelper) {
        super(stateHelper);
    }

    /**
     * <p>Shorthand for accessing {@link #getBoolean(Serializable, boolean)} with
     * {@code false} as the default value.</p>
     * 
     * <p>This method simplifies retrieving boolean values when the default should be false,
     * which is a common scenario for flags like 'disabled', 'readonly', etc.</p>
     *
     * @param key A unique key to identify the state value, must not be null
     * @return The boolean value from the component state, or {@code false} if the given key is
     *         not found
     */
    public boolean getBoolean(final Serializable key) {
        return getBoolean(key, false);
    }

    /**
     * <p>Shorthand for accessing a boolean value from the component state.</p>
     * 
     * <p>This method handles type conversion from the stored Object to boolean.</p>
     *
     * @param key          A unique key to identify the state value, must not be null
     * @param defaultValue The default value to return if the key is not found
     * @return The evaluated boolean value for the given key, or the default value if
     *         the key is not present in the state
     */
    public boolean getBoolean(final Serializable key, final boolean defaultValue) {
        return super.get(key, defaultValue);
    }

    /**
     * <p>Shorthand for setting a boolean primitive value in the component state.</p>
     * 
     * <p>This method improves code readability when storing boolean values by
     * providing a specific method signature for the boolean primitive type.</p>
     *
     * @param key   The unique key to identify the state value, must not be null
     * @param value The boolean value to store in the component's state
     */
    public void put(final Serializable key, final boolean value) {
        super.put(key, value);
    }

    /**
     * <p>Shorthand for accessing {@link #getInt(Serializable, int)} with {@code 0} as
     * the default value.</p>
     * 
     * <p>This method simplifies retrieving integer values when the default should be zero,
     * which is a common scenario for numeric properties.</p>
     *
     * @param key A unique key to identify the state value, must not be null
     * @return The integer value from the component state, or {@code 0} if the given key is
     *         not found
     */
    public int getInt(final Serializable key) {
        return getInt(key, 0);
    }

    /**
     * <p>Shorthand for accessing an integer value from the component state.</p>
     * 
     * <p>This method handles type conversion from the stored Object to int.</p>
     *
     * @param key          A unique key to identify the state value, must not be null
     * @param defaultValue The default value to return if the key is not found
     * @return The evaluated integer value for the given key, or the default value if
     *         the key is not present in the state
     */
    public int getInt(final Serializable key, final int defaultValue) {
        return super.get(key, defaultValue);
    }

    /**
     * <p>Shorthand for setting an integer primitive value in the component state.</p>
     * 
     * <p>This method improves code readability when storing integer values by
     * providing a specific method signature for the int primitive type.</p>
     *
     * @param key   The unique key to identify the state value, must not be null
     * @param value The integer value to store in the component's state
     */
    public void put(final Serializable key, final int value) {
        super.put(key, value);
    }
}
