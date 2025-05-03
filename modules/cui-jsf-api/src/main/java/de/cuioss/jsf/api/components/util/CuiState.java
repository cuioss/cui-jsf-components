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
package de.cuioss.jsf.api.components.util;

import jakarta.faces.component.StateHelper;
import org.omnifaces.util.State;

import java.io.Serializable;

/**
 * Extension to omnifaces {@link State} providing specific methods for dealing
 * with primitive types
 *
 * @author Oliver Wolff
 *
 */
public class CuiState extends State {

    /**
     * @param stateHelper
     */
    public CuiState(final StateHelper stateHelper) {
        super(stateHelper);
    }

    /**
     * Shorthand for accessing {@link #getBoolean(Serializable, boolean)} with
     * {@code false} for the default value
     *
     * @param key to be checked
     * @return the boolean representing the value, {@code false} if the given key is
     *         not found
     */
    public boolean getBoolean(final Serializable key) {
        return getBoolean(key, false);
    }

    /**
     * Shorthand for accessing a boolean value
     *
     * @param key          to be checked
     * @param defaultValue to be returned if the key is not found
     * @return the evaluated key or the given default value
     */
    public boolean getBoolean(final Serializable key, final boolean defaultValue) {
        return super.get(key, defaultValue);
    }

    /**
     * Shorthand for setting a boolean primitive as Value
     *
     * @param key   The name of the value in component's state.
     * @param value The value to put in component's state.
     */
    public void put(final Serializable key, final boolean value) {
        super.put(key, value);
    }

    /**
     * Shorthand for accessing {@link #getInt(Serializable, int)} with {@code 0} for
     * the default value
     *
     * @param key to be checked
     * @return the int representing the value, {@code 0} if the given key is not
     *         found
     */
    public int getInt(final Serializable key) {
        return getInt(key, 0);
    }

    /**
     * Shorthand for accessing an int value
     *
     * @param key          to be checked
     * @param defaultValue to be returned if the key is not found
     * @return the evaluated key or the given default value
     */
    public int getInt(final Serializable key, final int defaultValue) {
        return super.get(key, defaultValue);
    }

    /**
     * Shorthand for setting an int primitive as Value
     *
     * @param key   The name of the value in component's state.
     * @param value The value to put in component's state.
     */
    public void put(final Serializable key, final int value) {
        super.put(key, value);
    }
}
