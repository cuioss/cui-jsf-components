/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.menu;

import de.cuioss.jsf.api.components.model.menu.*;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.tools.string.Joiner;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;
import java.util.List;

/**
 * <p>The primary renderer for the {@link NavigationMenuComponent}, responsible for processing and
 * rendering all navigation menu items from the component's model. This renderer delegates the actual
 * rendering of specific menu item types to specialized renderers based on the model instance type.</p>
 * 
 * <h2>Features</h2>
 * <ul>
 *   <li>Acts as the entry point for rendering the complete navigation menu structure</li>
 *   <li>Handles traversal of hierarchical menu structures</li>
 *   <li>Delegates rendering to type-specific renderers based on menu item type</li>
 *   <li>Maintains unique IDs for menu items within the hierarchy</li>
 *   <li>Skips non-renderable menu items</li>
 * </ul>
 * 
 * <h2>Renderer Delegation</h2>
 * <p>This renderer delegates to specialized renderers based on menu item type:</p>
 * <ul>
 *   <li>{@link NavigationMenuSingleRenderer} - For internal application links</li>
 *   <li>{@link NavigationMenuExternalSingleRenderer} - For external URL links</li>
 *   <li>{@link NavigationMenuSeparatorRenderer} - For menu separators</li>
 *   <li>{@link NavigationMenuContainerRenderer} - For dropdown menus and submenus</li>
 * </ul>
 * 
 * <h2>ID Generation</h2>
 * <p>The renderer generates unique IDs for each menu item by combining:</p>
 * <ul>
 *   <li>Parent prefix (for nested items)</li>
 *   <li>Sequential index</li>
 *   <li>Item's own ID from the model</li>
 * </ul>
 *
 * @author Sven Haag
 * @see NavigationMenuComponent
 * @see NavigationMenuItem
 * @see NavigationMenuSingleRenderer
 * @see NavigationMenuExternalSingleRenderer
 * @see NavigationMenuSeparatorRenderer
 * @see NavigationMenuContainerRenderer
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.NAVIGATION_MENU_COMPONENT_RENDERER)
public class NavigationMenuRenderer extends BaseDecoratorRenderer<NavigationMenuComponent> {

    /**
     * Default constructor that configures this renderer as a non-partial renderer.
     * This means that children within the component are not expected to be rendered directly.
     */
    public NavigationMenuRenderer() {
        super(false);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Initiates the rendering of the navigation menu by retrieving the menu items from
     * the component's model and delegating to the {@link #renderNavigationMenuItems} method.
     * </p>
     * 
     * <p>If the component's {@link NavigationMenuComponent#resolveRendered()} method returns false,
     * no rendering will be performed.</p>
     *
     * @param context the current FacesContext
     * @param writer the decorating response writer
     * @param component the navigation menu component being rendered
     * @throws IOException if an error occurs during the rendering process
     */
    @Override
    protected void doEncodeBegin(FacesContext context, DecoratingResponseWriter<NavigationMenuComponent> writer,
            NavigationMenuComponent component) throws IOException {
        if (!component.resolveRendered()) {
            return;
        }

        renderNavigationMenuItems(component.resolveModelItems(), context, writer, component, false, "");
    }

    /**
     * <p>Recursively renders a list of navigation menu items, delegating to the appropriate
     * type-specific renderers based on the class of each menu item.</p>
     * 
     * <p>This method:</p>
     * <ul>
     *   <li>Handles null safety for the input list</li>
     *   <li>Skips rendering of items with rendered=false</li>
     *   <li>Generates unique IDs for each menu item</li>
     *   <li>Delegates to the appropriate renderer based on the item's type</li>
     *   <li>Recursively processes child items within container items</li>
     * </ul>
     *
     * @param menuItems the list of navigation menu items to render
     * @param context the current FacesContext
     * @param writer the decorating response writer
     * @param component the parent navigation menu component
     * @param parentIsContainer flag indicating whether the parent is a container (for submenu styling)
     * @param modelIdPrefix the ID prefix to use for generating unique menu item IDs
     * @throws IOException if an error occurs during the rendering process
     */
    private static void renderNavigationMenuItems(final List<NavigationMenuItem> menuItems, final FacesContext context,
            final DecoratingResponseWriter<NavigationMenuComponent> writer, final NavigationMenuComponent component,
            final boolean parentIsContainer, final String modelIdPrefix) throws IOException {

        if (null == menuItems) {
            return;
        }

        var i = 0;
        for (NavigationMenuItem menuItem : menuItems) {
            if (!menuItem.isRendered()) {
                continue;
            }

            // This will be the menu item ID, consisting of the parents ID and the model ID
            final var componentId = Joiner.on("").useForNull("menu").join(modelIdPrefix, String.valueOf(i), "_",
                    menuItem.getId());
            i++;

            if (menuItem instanceof NavigationMenuItemSingle single) {
                NavigationMenuSingleRenderer.render(context, writer, single, component, componentId);

            } else if (menuItem instanceof NavigationMenuItemExternalSingle single) {
                NavigationMenuExternalSingleRenderer.render(context, writer, single, component, componentId);

            } else if (menuItem instanceof NavigationMenuItemSeparator separator) {
                NavigationMenuSeparatorRenderer.render(writer, separator, component, componentId);

            } else if (menuItem instanceof NavigationMenuItemContainer containerModel) {
                NavigationMenuContainerRenderer.renderBegin(context, writer, containerModel, component,
                        parentIsContainer, componentId);
                renderNavigationMenuItems(containerModel.getChildren(), context, writer, component, true,
                        componentId + "_");
                NavigationMenuContainerRenderer.renderEnd(writer, containerModel);
            }
        }
    }
}
