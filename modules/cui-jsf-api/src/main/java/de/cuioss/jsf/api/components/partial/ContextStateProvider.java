package de.cuioss.jsf.api.components.partial;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the
 * Context-state. The implementation relies on the correct use of attribute
 * names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>state</h2>
 * <p>
 * Different general styles available (state): one of 'primary', 'success',
 * 'info', 'warning', 'danger'. If none of those is set it uses 'default'.
 * </p>
 *
 * @author Oliver Wolff
 */
public class ContextStateProvider {

    /** The key for the {@link StateHelper} */
    private static final String KEY = "state";

    private final CuiState state;

    /**
     * @param bridge
     */
    public ContextStateProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    /**
     * @return the stateString
     */
    public String getState() {
        return state.get(KEY);
    }

    /**
     * @param stateString the state to set
     */
    public void setState(String stateString) {
        state.put(KEY, stateString);
    }

    /**
     * @return the resolved {@link ContextState} if available, otherwise it will
     *         return {@link ContextState#DEFAULT}.
     */
    public ContextState resolveContextState() {
        return ContextState.getFromString(getState());
    }

}
