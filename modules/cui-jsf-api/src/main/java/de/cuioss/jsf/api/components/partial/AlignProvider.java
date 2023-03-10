package de.cuioss.jsf.api.components.partial;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the align-attribute. The
 * implementation relies on the correct use of attribute names, saying they must exactly match the
 * accessor methods.
 * </p>
 * <h2>align</h2>
 * <p>
 * Defines the alignment of a component. In case it is not set or does not match one of "left" or
 * "right" the {@link #resolveAlign()} will return {@link AlignHolder#DEFAULT}, other wise it will
 * return the according value.
 * </p>
 *
 * @author Oliver Wolff
 */
public class AlignProvider {

    /** The key for the {@link StateHelper} */
    private static final String KEY = "align";

    private final CuiState state;

    /**
     * @param componentBridge
     */
    public AlignProvider(@NonNull ComponentBridge componentBridge) {
        state = new CuiState(componentBridge.stateHelper());
    }

    /**
     * @return the align
     */
    public String getAlign() {
        return state.get(KEY);
    }

    /**
     * @param align the size to set
     */
    public void setAlign(String align) {
        state.put(KEY, align);
    }

    /**
     * @return the resolved {@link AlignHolder} if available, otherwise it will return
     *         {@link AlignHolder#DEFAULT}.
     */
    public AlignHolder resolveAlign() {
        return AlignHolder.getFromString(getAlign());
    }

}
