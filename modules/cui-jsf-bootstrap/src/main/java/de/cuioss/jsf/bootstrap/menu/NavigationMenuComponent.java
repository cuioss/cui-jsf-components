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
 * Holder of the bootstrap conform navigation menu component model.
 * This component is responsible for the rendering of a {@link NavigationMenuItem}.
 * Supported models:
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
@ResourceDependency(library = "javascript.enabler", name = "enabler.sub_menu.js", target = "head")
public class NavigationMenuComponent extends AbstractBaseCuiComponent {

    private static final String MODEL_ITEMS_KEY = "modelItems";

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
     * @return the contained model as {@link NavigationMenuItem}
     */
    public NavigationMenuItem getModel() {
        return state.get(ModelProvider.KEY);
    }

    /**
     * @param model to be set
     */
    public void setModel(Serializable model) {
        state.put(ModelProvider.KEY, model);
    }

    /**
     * @return true if this component has rendered=true and a model.
     */
    public boolean resolveRendered() {
        return isRendered() && resolveModelItems() != null;
    }

    /**
     * @return {@link List} {@link NavigationMenuItem}
     */
    public List<NavigationMenuItem> getModelItems() {
        return state.get(MODEL_ITEMS_KEY);
    }

    /**
     * @param navigationMenuItems to be set
     */
    public void setModelItems(final List<NavigationMenuItem> navigationMenuItems) {
        state.put(MODEL_ITEMS_KEY, navigationMenuItems);
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
