package de.cuioss.jsf.bootstrap.menu;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.io.Serializable;
import java.util.List;

import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItem;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemContainer;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSeparator;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSingle;
import de.cuioss.jsf.api.components.partial.ModelProvider;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;

/**
 * Holder of bootstrap conform navigation menu component model. This component
 * is responsible for the rendering of a {@link NavigationMenuItem}. Supported
 * models:
 * <ul>
 * <li>{@link NavigationMenuItemContainer}</li>
 * <li>{@link NavigationMenuItemSingle}</li>
 * <li>{@link NavigationMenuItemSeparator}</li>
 * </ul>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;boot:navigationMenu model="#{model}" /&gt;
 * &lt;boot:navigationMenu modelItems="#{modelItems}" /&gt;
 * </pre>
 *
 * @author Sven Haag
 */
@FacesComponent(BootstrapFamily.NAVIGATION_MENU_COMPONENT)
public class NavigationMenuComponent extends AbstractBaseCuiComponent {

    private static final String MODELITEMS_KEY = "modelItems";

    private final CuiState state;

    /***/
    public NavigationMenuComponent() {
        super.setRendererType(BootstrapFamily.NAVIGATION_MENU_COMPONENT_RENDERER);
        state = new CuiState(getStateHelper());
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * @param model
     */
    public void setModel(Serializable model) {
        state.put(ModelProvider.KEY, model);
    }

    /**
     * @return the contained model as {@link NavigationMenuItem}
     */
    public NavigationMenuItem getModel() {
        return state.get(ModelProvider.KEY);
    }

    /**
     * @return true if this component has rendered=true and a model.
     */
    public boolean resolveRendered() {
        return isRendered() && resolveModelItems() != null;
    }

    /**
     * @param navigationMenuItems
     */
    public void setModelItems(final List<NavigationMenuItem> navigationMenuItems) {
        state.put(MODELITEMS_KEY, navigationMenuItems);
    }

    /**
     * @return {@link List} {@link NavigationMenuItem}
     */
    public List<NavigationMenuItem> getModelItems() {
        return state.get(MODELITEMS_KEY);
    }

    /**
     * @return list of models from getModel() or getModelItems().
     */
    public List<NavigationMenuItem> resolveModelItems() {
        var modelItems = getModelItems();
        if (modelItems == null && getModel() != null) {
            modelItems = immutableList(getModel());
        }
        return modelItems;
    }
}
