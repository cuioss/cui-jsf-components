/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.menu;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItem;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemContainer;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSeparator;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSingle;
import de.cuioss.jsf.api.components.partial.ModelProvider;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;

import java.io.Serializable;
import java.util.List;

/**
 * Renders Bootstrap-compatible navigation menus based on {@link NavigationMenuItem} models.
 * Supports hierarchical menu structures with dropdowns and nested items.
 * 
 * <h3>Supported Menu Item Types</h3>
 * <ul>
 *   <li>{@link NavigationMenuItemContainer} - Container for nested menus (dropdowns)</li>
 *   <li>{@link NavigationMenuItemSingle} - Single navigation link</li>
 *   <li>{@link NavigationMenuItemSeparator} - Menu separator</li>
 * </ul>
 * 
 * <h3>Usage</h3>
 * <p>With a single menu item:</p>
 * <pre>
 * &lt;boot:navigationMenu model="#{singleMenuItemModel}" /&gt;
 * </pre>
 * 
 * <p>With multiple menu items:</p>
 * <pre>
 * &lt;boot:navigationMenu modelItems="#{collectionOfMenuItems}" /&gt;
 * </pre>
 * 
 * <h3>Model Example</h3>
 * <pre>
 * // Single menu item
 * var homeItem = new NavigationMenuItemSingle("Home", "home.jsf");
 * 
 * // Dropdown menu
 * var adminMenu = new NavigationMenuItemContainer("Admin");
 * adminMenu.addItem(new NavigationMenuItemSingle("Users", "admin/users.jsf"));
 * adminMenu.addItem(new NavigationMenuItemSingle("Settings", "admin/settings.jsf"));
 * 
 * // Use as collection
 * List&lt;NavigationMenuItem&gt; menuItems = Arrays.asList(homeItem, adminMenu);
 * </pre>
 *
 * @author Sven Haag
 */
@FacesComponent(BootstrapFamily.NAVIGATION_MENU_COMPONENT)
@ResourceDependency(library = "javascript.enabler", name = "enabler.sub_menu.js", target = "head")
public class NavigationMenuComponent extends AbstractBaseCuiComponent {

    private static final String MODEL_ITEMS_KEY = "modelItems";

    private final CuiState state;

    /**
     * Default constructor.
     * Initializes the component's renderer type and state helper.
     */
    public NavigationMenuComponent() {
        super.setRendererType(BootstrapFamily.NAVIGATION_MENU_COMPONENT_RENDERER);
        state = new CuiState(getStateHelper());
    }

    /**
     * {@inheritDoc}
     * 
     * @return the component family, always {@link BootstrapFamily#COMPONENT_FAMILY}
     */
    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    /**
     * Returns the single navigation menu item model if set.
     * This is typically used when rendering a single menu structure.
     *
     * @return the contained model as {@link NavigationMenuItem} or null if not set
     */
    public NavigationMenuItem getModel() {
        return state.get(ModelProvider.KEY);
    }

    /**
     * Sets the single navigation menu item model.
     * This is typically used when rendering a single menu structure.
     *
     * @param model the navigation menu item model to be set. Must be serializable and
     *              typically an implementation of {@link NavigationMenuItem}
     */
    public void setModel(Serializable model) {
        state.put(ModelProvider.KEY, model);
    }

    /**
     * Determines whether this component should be rendered.
     * A component is rendered if it is set to rendered=true AND has at least one
     * model item to display.
     *
     * @return true if this component has rendered=true and a model, false otherwise
     */
    public boolean resolveRendered() {
        return isRendered() && resolveModelItems() != null;
    }

    /**
     * Returns the collection of navigation menu items if set.
     * This is typically used when rendering multiple top-level menu items.
     *
     * @return a list of {@link NavigationMenuItem} objects or null if not set
     */
    public List<NavigationMenuItem> getModelItems() {
        return state.get(MODEL_ITEMS_KEY);
    }

    /**
     * Sets the collection of navigation menu items.
     * This is typically used when rendering multiple top-level menu items.
     *
     * @param navigationMenuItems the list of navigation menu items to be set
     */
    public void setModelItems(final List<NavigationMenuItem> navigationMenuItems) {
        state.put(MODEL_ITEMS_KEY, navigationMenuItems);
    }

    /**
     * Resolves the menu items to be rendered, either from the single model or the model items collection.
     * <p>
     * The resolution logic is:
     * <ol>
     *   <li>If modelItems is set, use those directly</li>
     *   <li>If modelItems is not set but model is set, create a list containing only the model</li>
     *   <li>If neither is set, return null</li>
     * </ol>
     *
     * @return list of models from getModel() or getModelItems(), or null if neither is available
     */
    public List<NavigationMenuItem> resolveModelItems() {
        var modelItems = getModelItems();
        if (modelItems == null && getModel() != null) {
            modelItems = immutableList(getModel());
        }
        return modelItems;
    }
}
