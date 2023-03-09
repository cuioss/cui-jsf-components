package com.icw.ehf.cui.components.bootstrap.menu;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.io.Serializable;
import java.util.List;

import javax.faces.component.FacesComponent;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.menu.model.NavigationMenuItem;
import com.icw.ehf.cui.components.bootstrap.menu.model.NavigationMenuItemContainer;
import com.icw.ehf.cui.components.bootstrap.menu.model.NavigationMenuItemSeparator;
import com.icw.ehf.cui.components.bootstrap.menu.model.NavigationMenuItemSingle;
import com.icw.ehf.cui.core.api.components.base.AbstractBaseCuiComponent;
import com.icw.ehf.cui.core.api.components.partial.ModelProvider;
import com.icw.ehf.cui.core.api.components.util.CuiState;

/**
 * <p>
 * Holder of bootstrap conform navigation menu component model.
 * This component is responsible for the rendering of a {@link NavigationMenuItem}.
 * Supported models:
 * <ul>
 * <li>{@link NavigationMenuItemContainer}</li>
 * <li>{@link NavigationMenuItemSingle}</li>
 * <li>{@link NavigationMenuItemSeparator}</li>
 * </ul>
 * </p>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;cui:navigationMenu model="#{model}" /&gt;
 * &lt;cui:navigationMenu modelItems="#{modelItems}" /&gt;
 * </pre>
 *
 * @author Sven Haag, Sven Haag
 */
@FacesComponent(BootstrapFamily.NAVIGATION_MENU_COMPONENT)
public class NavigationMenuComponent extends AbstractBaseCuiComponent {

    private static final String MODELITEMS_KEY = "modelItems";

    private final CuiState state;

    /***/
    public NavigationMenuComponent() {
        super();
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
     * @return List<NavigationMenuItem>
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
