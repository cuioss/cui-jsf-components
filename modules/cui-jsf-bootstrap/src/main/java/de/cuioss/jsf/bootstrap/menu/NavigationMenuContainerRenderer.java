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
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemContainer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import jakarta.faces.component.html.HtmlOutcomeTargetLink;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.Renderer;
import java.io.IOException;

/**
 * {@link Renderer} utility for the {@link NavigationMenuItemContainer} model.
 * <p>
 * Renders a bootstrap conform navigation menu structure.
 *
 * <h2>HTML structure</h2>
 * <p>The renderer produces the following HTML structure for dropdown menus:</p>
 *
 * <pre>
 * &lt;li class="nav-item dropdown"&gt;
 *   &lt;a href="" role="button" class="nav-link dropdown-toggle" data-toggle="dropdown"&gt;
 *     &lt;span class="cui-icon cui-icon-tag"/&gt;
 *     &lt;span&gt;label&lt;/span&gt;
 *     &lt;span class="cui-icon cui-icon-triangle_s"/&gt;
 *   &lt;/a&gt;
 *   &lt;ul class="dropdown-menu"&gt;
 *     ...children...
 *   &lt;/ul&gt;
 * &lt;/li&gt;
 * </pre>
 *
 * <p>For submenus (nested dropdowns), additional CSS classes are applied to enable
 * proper positioning and behavior:</p>
 *
 * <pre>
 * &lt;li class="dropdown dropdown-submenu"&gt;
 *   ...
 * </pre>
 *
 * @author Sven Haag
 * @see NavigationMenuItemContainer
 * @see NavigationMenuComponent
 * @see NavigationMenuRenderer
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NavigationMenuContainerRenderer {

    private static final String ICON_TOP_MENU = "cui-icon-triangle_s";
    private static final String ICON_SUB_MENU = "cui-icon-triangle_e";

    /**
     * <p>Renders the beginning tags of a dropdown menu structure based on a
     * {@link NavigationMenuItemContainer} model.</p>
     *
     * <p>This method generates:</p>
     * <ul>
     *   <li>The list item container with appropriate dropdown classes</li>
     *   <li>The toggle link with icon, label and dropdown indicator</li>
     *   <li>The opening tag for the dropdown menu container</li>
     * </ul>
     *
     * <p>The container will not be rendered if the model's rendered property is false.</p>
     *
     * @param context the current FacesContext
     * @param writer the decorating response writer
     * @param model the navigation menu item container model to render
     * @param component the parent navigation menu component
     * @param parentIsContainer flag indicating whether this menu is within another container (submenu)
     * @param idExtension string to append to the generated component ID for uniqueness
     * @throws IOException if an error occurs during the rendering process
     */
    static void renderBegin(final FacesContext context, final DecoratingResponseWriter<NavigationMenuComponent> writer,
            final NavigationMenuItemContainer model, final NavigationMenuComponent component,
            final boolean parentIsContainer, final String idExtension) throws IOException {
        if (!model.isRendered()) {
            return;
        }

        writer.withStartElement(Node.LI);
        writer.withClientId(idExtension);
        writer.withPassThroughAttributes();
        writer.withAttributeStyle(component.getStyle());
        writer.withStyleClass(component.getStyleClassBuilder()
            .append(CssBootstrap.NAVIGATION_MENU_ITEM)
            .append(CssBootstrap.LIST_DROPDOWN)
            .appendIfTrue(CssBootstrap.LIST_DROP_DOWN_SUBMENU, parentIsContainer));

        if (model.isActive()) {
            writer.withAttribute(AttributeName.DATA_ITEM_ACTIVE, Boolean.TRUE.toString());
        }

        renderCmdLink(context, model, parentIsContainer);

        writer.withStartElement(Node.UL);
        writer.withStyleClass(CssBootstrap.LIST_DROP_DOWN_MENU);
    }

    /**
     * <p>Renders the closing tags for a dropdown menu structure.</p>
     *
     * <p>This method generates:</p>
     * <ul>
     *   <li>The closing tag for the dropdown menu container (&lt;/ul&gt;)</li>
     *   <li>The closing tag for the list item container (&lt;/li&gt;)</li>
     * </ul>
     *
     * <p>The closing tags will not be rendered if the model's rendered property is false.</p>
     *
     * @param writer the decorating response writer
     * @param model the navigation menu item container model
     * @throws IOException if an error occurs during the rendering process
     */
    static void renderEnd(final DecoratingResponseWriter<NavigationMenuComponent> writer,
            final NavigationMenuItemContainer model) throws IOException {
        if (!model.isRendered()) {
            return;
        }
        writer.withEndElement(Node.UL);
        writer.withEndElement(Node.LI);
    }

    /**
     * <p>Renders the dropdown toggle link with appropriate styling, icon, label and
     * dropdown indicator.</p>
     *
     * <p>The link is rendered with:</p>
     * <ul>
     *   <li>dropdown-toggle CSS class</li>
     *   <li>data-toggle="dropdown" attribute for Bootstrap JavaScript</li>
     *   <li>Optional icon if specified in the model</li>
     *   <li>Text label from the model</li>
     *   <li>Dropdown indicator icon (triangle pointing down for top menus, right for submenus)</li>
     * </ul>
     *
     * @param context the current FacesContext
     * @param model the navigation menu item container model
     * @param parentIsContainer flag indicating whether this menu is within another container (submenu)
     * @throws IOException if an error occurs during the rendering process
     */
    private static void renderCmdLink(final FacesContext context, final NavigationMenuItemContainer model,
                                      final boolean parentIsContainer) throws IOException {

        final var application = context.getApplication();

        var commandLink = (HtmlOutcomeTargetLink) application.createComponent(HtmlOutcomeTargetLink.COMPONENT_TYPE);
        commandLink.setOutcome("#");
        commandLink.setRole("button");
        commandLink.setStyleClass(CssBootstrap.LIST_DROP_DOWN_TOGGLE.getStyleClassBuilder().append(CssBootstrap.NAVIGATION_MENU_LINK).getStyleClass());
        commandLink.getPassThroughAttributes().put("data-bs-toggle", "dropdown");

        // Title
        if (model.isTitleAvailable()) {
            commandLink.setTitle(model.getResolvedTitle());
        }

        // Item Icon
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

        // Collapse Icon
        final var caret = (IconComponent) context.getApplication().createComponent(BootstrapFamily.ICON_COMPONENT);
        if (parentIsContainer) {
            caret.setIcon(ICON_SUB_MENU);
        } else {
            caret.setIcon(ICON_TOP_MENU);
        }
        commandLink.getChildren().add(caret);

        commandLink.encodeAll(context);
    }
}
