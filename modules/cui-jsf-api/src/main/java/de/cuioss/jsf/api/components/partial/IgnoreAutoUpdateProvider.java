package de.cuioss.jsf.api.components.partial;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

/**
 * Sets the ignoreAutoUpdate flag to ajax calls. When set to true, {@code <p:autoUpdate/>} elements will not
 * be updated.
 */
public class IgnoreAutoUpdateProvider {

    /** The key for the {@link StateHelper} */
    private static final String KEY = "ignoreAutoUpdate";

    private final CuiState state;

    /**
     * @param bridge must not be null
     */
    public IgnoreAutoUpdateProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    /**
     * @return ignoreAutoUpdate
     */
    public boolean isIgnoreAutoUpdate() {
        return state.getBoolean(KEY, false);
    }

    /**
     * @param ignoreAutoUpdate
     *            the value to set
     */
    public void setIgnoreAutoUpdate(final boolean ignoreAutoUpdate) {
        state.put(KEY, ignoreAutoUpdate);
    }
}
