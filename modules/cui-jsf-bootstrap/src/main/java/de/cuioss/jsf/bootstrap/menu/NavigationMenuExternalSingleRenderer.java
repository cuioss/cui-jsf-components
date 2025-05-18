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

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemExternalSingle;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSingle;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import jakarta.faces.component.html.HtmlOutputLink;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.Renderer;
import lombok.experimental.UtilityClass;

import java.io.IOException;

/**
 * <p>A utility class responsible for rendering {@link NavigationMenuItemExternalSingle} models
 * as Bootstrap-compatible navigation menu items. This renderer creates menu items that link to
 * external URLs (outside the application) using standard HTML links rather than JSF outcome links.</p>
 *
 * <p>This renderer differs from {@link NavigationMenuSingleRenderer} in that it uses
 * {@link HtmlOutputLink} components to create direct URL links with optional target attributes
 * for opening links in new windows or frames.</p>
 *
 * <h2>Features</h2>
 * <ul>
 *   <li>Renders external links with proper HTML anchor tags</li>
 *   <li>Supports target attribute for controlling link behavior (e.g., "_blank")</li>
 *   <li>Includes optional icon display</li>
 *   <li>Adds proper styling for menu item text</li>
 *   <li>Handles active state styling via data attributes</li>
 * </ul>
 *
 * <h2>HTML structure</h2>
 * <p>The renderer produces the following HTML structure:</p>
 *
 * <pre>
 * &lt;li&gt;
 *   &lt;a href="https://external-url.com" target="_blank"&gt;
 *     &lt;span class="cui-icon cui-icon-tag"/&gt;
 *     &lt;span&gt;Menu Item Label&lt;/span&gt;
 *   &lt;/a&gt;
 * &lt;/li&gt;
 * </pre>
 *
 * @author Matthias Walliczek
 * @see NavigationMenuItemExternalSingle
 * @see NavigationMenuComponent
 * @see NavigationMenuRenderer
 */
@UtilityClass
public class NavigationMenuExternalSingleRenderer {

    /**
     * <p>Renders a single external link menu item based on a {@link NavigationMenuItemExternalSingle} model.</p>
     *
     * <p>This method generates:</p>
     * <ul>
     *   <li>A list item container (&lt;li&gt;)</li>
     *   <li>An anchor tag with href and optional target attributes</li>
     *   <li>Optional icon if specified in the model</li>
     *   <li>Text label with appropriate styling</li>
     * </ul>
     *
     * <p>The menu item will not be rendered if the model's rendered property is false.</p>
     *
     * @param context the current FacesContext
     * @param writer the decorating response writer
     * @param model the external single menu item model to render
     * @param component the parent navigation menu component
     * @param idExtension string to append to the generated component ID for uniqueness
     * @throws IOException if an error occurs during the rendering process
     */
    static void render(final FacesContext context, final DecoratingResponseWriter<NavigationMenuComponent> writer,
            final NavigationMenuItemExternalSingle model, final NavigationMenuComponent component,
            final String idExtension) throws IOException {
        if (!model.isRendered()) {
            return;
        }

        writer.withStartElement(Node.LI);
        writer.withClientId(idExtension);
        writer.withPassThroughAttributes();
        writer.withAttributeStyle(component.getStyle());
        writer.withStyleClass(component.getStyleClassBuilder());
        if (model.isActive()) {
            writer.withAttribute(AttributeName.DATA_ITEM_ACTIVE, Boolean.TRUE.toString());
        }

        renderCmdLink(context, model);

        writer.withEndElement(Node.LI);
    }

    /**
     * <p>Renders the anchor tag with appropriate attributes and content for an external link.</p>
     *
     * <p>This method creates an {@link HtmlOutputLink} component configured with:</p>
     * <ul>
     *   <li>The external URL from the model as the href value</li>
     *   <li>Target attribute (e.g., "_blank") if specified in the model</li>
     *   <li>Optional title/tooltip from the model</li>
     *   <li>Optional icon component if an icon class is specified</li>
     *   <li>Text label with appropriate styling</li>
     * </ul>
     *
     * @param context the current FacesContext
     * @param model the external single menu item model
     * @throws IOException if an error occurs during the rendering process
     */
    private static void renderCmdLink(final FacesContext context, final NavigationMenuItemExternalSingle model)
            throws IOException {
        final var application = context.getApplication();

        final var commandLink = (HtmlOutputLink) application.createComponent(HtmlOutputLink.COMPONENT_TYPE);
        commandLink.setValue(model.getHRef());
        commandLink.setTarget(model.getTarget());

        // Title
        if (model.isTitleAvailable()) {
            commandLink.setTitle(model.getResolvedTitle());
        }

        // Icon
        if (null != model.getIconStyleClass()) {
            final var icon = (IconComponent) application.createComponent(BootstrapFamily.ICON_COMPONENT);
            icon.setIcon(model.getIconStyleClass());
            commandLink.getChildren().add(icon);
        }

        // Label
        final var outputText = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        outputText.setStyleClass(CssCuiBootstrap.CUI_NAVIGATION_MENU_TEXT.getStyleClass());
        outputText.setValue(model.getResolvedLabel());
        commandLink.getChildren().add(outputText);

        commandLink.encodeAll(context);
    }
}
