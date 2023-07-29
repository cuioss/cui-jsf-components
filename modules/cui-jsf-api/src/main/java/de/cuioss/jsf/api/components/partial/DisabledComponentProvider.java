package de.cuioss.jsf.api.components.partial;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the
 * disabled-attribute. The implementation relies on the correct use of attribute
 * names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>disabled</h2>
 * <p>
 * Defines the disable state of a component. Default is {@code false}
 * </p>
 *
 * @author Eugen Fischer
 */
public class DisabledComponentProvider {

    /** The key for the {@link StateHelper} */
    private static final String KEY = "disabled";

    private final CuiState state;

    /**
     * @param bridge must not be null
     */
    public DisabledComponentProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    /**
     * @return the disabled state
     */
    public boolean isDisabled() {
        return state.getBoolean(KEY, false);
    }

    /**
     * @param disabled
     */
    public void setDisabled(final boolean disabled) {
        state.put(KEY, disabled);
    }

}
