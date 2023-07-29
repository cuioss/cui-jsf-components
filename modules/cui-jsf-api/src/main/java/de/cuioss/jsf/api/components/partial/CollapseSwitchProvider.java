package de.cuioss.jsf.api.components.partial;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.api.ui.model.ToggleSwitch;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Default implementation for a collapsible component.
 * </p>
 * It encapsulates a components ability
 * <ul>
 * <li>to collapse something and</li>
 * <li>the current state (collapsed or not)</li>
 * </ul>
 * The state can be represented either by
 * <ul>
 * <li>a boolean or</li>
 * <li>a {@link ToggleSwitch}</li>
 * </ul>
 * <h2>collapsible</h2>
 * <p>
 * [true|false] Component can be collapsed.
 * </p>
 * <h2>collapsed</h2>
 * <p>
 * [true|false] Collapse state - component is collapsed or not.
 * </p>
 * <h2>collapseSwitch</h2>
 * <p>
 * {@link ToggleSwitch} Collapse state - component is collapsed or not. This
 * takes precedence over the <code>collapsed</code> attribute.
 * </p>
 *
 * @author Sven Haag
 */
public class CollapseSwitchProvider {

    /** The key for the {@link StateHelper} */
    private static final String COLLAPSESWITCH_COLLAPSESWITCH_KEY = "collapseSwitch";

    /** The key for the {@link StateHelper} */
    private static final String COLLAPSESWITCH_COLLAPSED_KEY = "collapsed";

    /** The key for the {@link StateHelper} */
    private static final String COLLAPSESWITCH_COLLAPSIBLE_KEY = "collapsible";

    private final CuiState state;

    /**
     * @param componentBridge
     */
    public CollapseSwitchProvider(@NonNull ComponentBridge componentBridge) {
        state = new CuiState(componentBridge.stateHelper());
    }

    /**
     * @param toggleSwitch the toggleSwitch to set
     */
    public void setCollapseSwitch(final ToggleSwitch toggleSwitch) {
        state.put(COLLAPSESWITCH_COLLAPSESWITCH_KEY, toggleSwitch);
    }

    /**
     * @return toggleSwitch
     */

    public ToggleSwitch getCollapseSwitch() {
        return state.get(COLLAPSESWITCH_COLLAPSESWITCH_KEY);
    }

    /**
     * @param collapsed the collapsed to set
     */
    public void setCollapsed(final boolean collapsed) {
        state.put(COLLAPSESWITCH_COLLAPSED_KEY, collapsed);
    }

    /**
     * @return collapsed
     */
    public boolean isCollapsed() {
        return state.getBoolean(COLLAPSESWITCH_COLLAPSED_KEY, false);
    }

    private Boolean hasCollapsed() {
        return state.get(COLLAPSESWITCH_COLLAPSED_KEY);
    }

    /**
     * @param collapsible the collapsible to set
     */
    public void setCollapsible(final boolean collapsible) {
        state.put(COLLAPSESWITCH_COLLAPSIBLE_KEY, collapsible);
    }

    /**
     * @return collapsible
     */
    public boolean isCollapsible() {
        return state.get(COLLAPSESWITCH_COLLAPSIBLE_KEY, true);
    }

    /**
     * Set the value for the current collapse state (true=collapsed,
     * false=expanded). This is an abstraction method to ensure the correct usage of
     * the underlying state holder which can be either <code>collapseSwitch</code>
     * or <code>collapsed</code>.
     *
     * @param collapsed
     */
    public void setCollapsedState(final boolean collapsed) {
        final var toggleSwitch = getCollapseSwitch();
        if (toggleSwitch != null) {
            toggleSwitch.setToggled(collapsed);
        } else if (hasCollapsed() != null) {
            setCollapsed(collapsed);
        } else {
            setCollapseSwitch(new ToggleSwitch(collapsed));
        }
    }

    /**
     * Returns the actual collapse state. If <code>collapseSwitch</code> and
     * <code>collapsed</code> are set, the <code>collapseSwitch</code> takes
     * precedence.
     *
     * @return toggle state.
     */
    public boolean resolveCollapsed() {
        final var toggleSwitch = getCollapseSwitch();
        if (toggleSwitch != null) {
            return toggleSwitch.isToggled();
        }
        if (hasCollapsed() != null) {
            return isCollapsed();
        }
        return false;
    }
}
