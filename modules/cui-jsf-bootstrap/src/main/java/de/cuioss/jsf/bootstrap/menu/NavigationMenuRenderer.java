package de.cuioss.jsf.bootstrap.menu;

import java.io.IOException;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import de.cuioss.jsf.api.components.model.menu.NavigationMenuItem;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemContainer;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemExternalSingle;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSeparator;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSingle;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.tools.string.Joiner;

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

            if (menuItem instanceof NavigationMenuItemSingle) {
                NavigationMenuSingleRenderer.render(context, writer, (NavigationMenuItemSingle) menuItem, component,
                        componentId);

            } else if (menuItem instanceof NavigationMenuItemExternalSingle) {
                NavigationMenuExternalSingleRenderer.render(context, writer,
                        (NavigationMenuItemExternalSingle) menuItem, component, componentId);

            } else if (menuItem instanceof NavigationMenuItemSeparator) {
                NavigationMenuSeparatorRenderer.render(writer, (NavigationMenuItemSeparator) menuItem, component,
                        componentId);

            } else if (menuItem instanceof NavigationMenuItemContainer) {
                final var containerModel = (NavigationMenuItemContainer) menuItem;
                NavigationMenuContainerRenderer.renderBegin(context, writer, containerModel, component,
                        parentIsContainer, componentId);
                renderNavigationMenuItems(containerModel.getChildren(), context, writer, component, true,
                        componentId + "_");
                NavigationMenuContainerRenderer.renderEnd(writer, (NavigationMenuItemContainer) menuItem);
            }
        }
    }
}
