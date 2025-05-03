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

import de.cuioss.jsf.api.components.model.menu.NavigationMenuItem;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemContainer;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemExternalSingle;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSeparator;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSingle;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.tools.string.Joiner;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;
import jakarta.faces.render.Renderer;

import java.io.IOException;
import java.util.List;

/**
 * Default {@link Renderer} for the {@link NavigationMenuComponent} component,
 * rendering all {@linkplain NavigationMenuItem} model items provided by
 * {@linkplain NavigationMenuComponent#resolveModelItems()}.
 *
 * @author Sven Haag
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.NAVIGATION_MENU_COMPONENT_RENDERER)
public class NavigationMenuRenderer extends BaseDecoratorRenderer<NavigationMenuComponent> {

    /***/
    public NavigationMenuRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(FacesContext context, DecoratingResponseWriter<NavigationMenuComponent> writer,
            NavigationMenuComponent component) throws IOException {
        if (!component.resolveRendered()) {
            return;
        }

        renderNavigationMenuItems(component.resolveModelItems(), context, writer, component, false, "");
    }

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
