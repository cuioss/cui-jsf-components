package de.cuioss.jsf.api.components.partial;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

/**
 * Default implementation for {@link ComponentStyleClassProvider}
 *
 * @author Oliver Wolff
 */
public class ComponentStyleClassProviderImpl implements ComponentStyleClassProvider {

    /** The key for the {@link StateHelper} */
    public static final String KEY = "styleClass";

    private final CuiState state;

    /**
     * @param bridge
     */
    public ComponentStyleClassProviderImpl(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    @Override
    public String getStyleClass() {
        return state.get(KEY);
    }

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(getStyleClass());
    }

    @Override
    public void setStyleClass(String styleClass) {
        state.put(KEY, styleClass);
    }

}
