package de.cuioss.jsf.api.components.partial;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Defines the deferred loading provider. Implementors of this class manage the state and resolving
 * of the 'deferred' boolean of a component.
 * </p>
 *
 * @author Sven Haag, Sven Haag
 */
public class DeferredProvider {

    /** The key for the {@link StateHelper} */
    private static final String KEY = "deferred";

    private final CuiState state;

    /**
     * @param bridge must not be null
     */
    public DeferredProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    /**
     * @return deferred
     */
    public boolean isDeferred() {
        return state.getBoolean(KEY, false);
    }

    /**
     * @param deferred
     *            the value to set
     */
    public void setDeferred(final boolean deferred) {
        state.put(KEY, deferred);
    }
}
