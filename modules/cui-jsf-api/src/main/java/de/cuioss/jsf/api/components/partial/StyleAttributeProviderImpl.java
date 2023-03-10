package de.cuioss.jsf.api.components.partial;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

/**
 * Default implementation for dealing with the styleClass attribute.
 *
 * @author Oliver Wolff
 *
 */
public class StyleAttributeProviderImpl implements StyleAttributeProvider {

    /** The key for the {@link StateHelper} */
    public static final String KEY = "style";

    private final CuiState state;

    /**
     * @param bridge
     */
    public StyleAttributeProviderImpl(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    @Override
    public void setStyle(String style) {
        state.put(KEY, style);
    }

    @Override
    public String getStyle() {
        return state.get(KEY);
    }

}
