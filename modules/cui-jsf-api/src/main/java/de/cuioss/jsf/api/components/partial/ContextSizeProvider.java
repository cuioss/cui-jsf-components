package de.cuioss.jsf.api.components.partial;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the Context-size. The implementation
 * relies on the correct use of attribute names, saying they must exactly match the accessor
 * methods.
 * </p>
 * <h2>size</h2>
 * <p>
 * Defines the component size. Supported values are "xs", "sm", "md", "lg", "xl", "xxl", "xxxl"
 * based on bootstrap screen sizes. Default: no size setting is set: {@link #resolveContextSize()}
 * will return {@link ContextSize#DEFAULT}
 * </p>
 *
 * @author Oliver Wolff
 */
public class ContextSizeProvider {

    /**
     * The key for the {@link StateHelper}
     */
    private static final String KEY = "size";

    private final CuiState state;

    private final String defaultValue;

    /**
     * @param bridge
     */
    public ContextSizeProvider(@NonNull ComponentBridge bridge) {
        this(bridge, null);
    }

    /**
     * @param bridge
     */
    public ContextSizeProvider(@NonNull ComponentBridge bridge, String defaultValue) {
        state = new CuiState(bridge.stateHelper());
        this.defaultValue = defaultValue;
    }

    /**
     * @return the size
     */
    public String getSize() {
        return state.get(KEY, defaultValue);
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        state.put(KEY, size);
    }

    /**
     * @return the resolved {@link ContextSize} if available, otherwise it will return
     *         {@link ContextSize#DEFAULT}.
     */
    public ContextSize resolveContextSize() {
        return ContextSize.getFromString(getSize());
    }

}
