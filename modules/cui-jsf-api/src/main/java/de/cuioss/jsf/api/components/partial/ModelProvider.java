package de.cuioss.jsf.api.components.partial;

import java.io.Serializable;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of any
 * {@link Serializable} model class. The implementation relies on the correct
 * use of attribute names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>model</h2>
 * <p>
 * The model to be attached to the component. This can be any object that
 * implements {@link Serializable}.
 * </p>
 *
 * @author Oliver Wolff
 */
public class ModelProvider {

    /** The key for the {@link StateHelper} */
    public static final String KEY = "model";

    private final CuiState state;

    /**
     * @param bridge
     */
    public ModelProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    /**
     * @param model to be set.
     */
    public void setModel(Serializable model) {
        state.put(KEY, model);

    }

    /**
     * @return the previously set model or null if none has been set.
     */
    public Serializable getModel() {
        return state.get(KEY);
    }

}
