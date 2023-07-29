package de.cuioss.jsf.api.components.util;

import java.io.Serializable;

import javax.faces.component.StateHelper;

import org.omnifaces.util.State;

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
        return (Boolean) super.get(key, defaultValue);
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
        return (Integer) super.get(key, defaultValue);
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
