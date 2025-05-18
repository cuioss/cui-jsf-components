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
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSingle;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import jakarta.faces.component.UIParameter;
import jakarta.faces.component.html.HtmlOutcomeTargetLink;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.Renderer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Map.Entry;

/**
 * <p>A utility class responsible for rendering {@link NavigationMenuItemSingle} models as
 * Bootstrap-compatible navigation menu items. This renderer creates standard menu items that
 * link to internal application pages using JSF navigation outcomes.</p>
 * 
 * <p>Unlike {@link NavigationMenuExternalSingleRenderer} which handles external URLs, this
 * renderer uses {@link HtmlOutcomeTargetLink} components to create navigation links that use
 * JSF's navigation system. It fully supports JSF's navigation features including outcome
 * parameters and target attributes.</p>
 * 
 * <h2>Features</h2>
 * <ul>
 *   <li>Renders navigation links that use JSF's outcome-based navigation</li>
 *   <li>Supports outcome parameters for dynamic navigation targets</li>
 *   <li>Includes optional icon display</li>
 *   <li>Adds proper styling for menu item text</li>
 *   <li>Handles active state styling via data attributes</li>
 *   <li>Supports target attribute for controlling link behavior (e.g., "_blank")</li>
 * </ul>
 * 
 * <h2>HTML structure</h2>
 * <p>The renderer produces the following HTML structure:</p>
 *
 * <pre>
 * &lt;li&gt;
 *   &lt;a href="outcome"&gt;
 *     &lt;span class="cui-icon cui-icon-tag"/&gt;
 *     &lt;span&gt;Menu Item Label&lt;/span&gt;
 *   &lt;/a&gt;
 * &lt;/li&gt;
 * </pre>
 *
 * @author Sven Haag
 * @see NavigationMenuItemSingle
 * @see NavigationMenuComponent
 * @see NavigationMenuRenderer
 * @see NavigationMenuExternalSingleRenderer
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NavigationMenuSingleRenderer {

    /**
     * <p>Renders a single navigation menu item based on a {@link NavigationMenuItemSingle} model.</p>
     * 
     * <p>This method generates:</p>
     * <ul>
     *   <li>A list item container (&lt;li&gt;)</li>
     *   <li>An outcome-based navigation link using HtmlOutcomeTargetLink</li>
     *   <li>Optional icon if specified in the model</li>
     *   <li>Text label with appropriate styling</li>
     *   <li>Any required outcome parameters as UIParameter components</li>
     * </ul>
     * 
     * <p>The menu item will not be rendered if the model's rendered property is false.</p>
     *
     * @param context the current FacesContext
     * @param writer the decorating response writer
     * @param model the single menu item model to render
     * @param component the parent navigation menu component
     * @param idExtension string to append to the generated component ID for uniqueness
     * @throws IOException if an error occurs during the rendering process
     */
    static void render(final FacesContext context, final DecoratingResponseWriter<NavigationMenuComponent> writer,
            final NavigationMenuItemSingle model, final NavigationMenuComponent component, final String idExtension)
            throws IOException {
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
     * <p>Renders the outcome-based navigation link with appropriate attributes and content.</p>
     * 
     * <p>This method creates an {@link HtmlOutcomeTargetLink} component configured with:</p>
     * <ul>
     *   <li>The JSF outcome from the model</li>
     *   <li>Target attribute (e.g., "_blank") if specified in the model</li>
     *   <li>Any outcome parameters as UIParameter components</li>
     *   <li>Optional title/tooltip from the model</li>
     *   <li>Optional icon component if an icon class is specified</li>
     *   <li>Text label with appropriate styling</li>
     * </ul>
     *
     * @param context the current FacesContext
     * @param model the single menu item model
     * @throws IOException if an error occurs during the rendering process
     */
    private static void renderCmdLink(final FacesContext context, final NavigationMenuItemSingle model)
            throws IOException {
        final var application = context.getApplication();

        final var commandLink = (HtmlOutcomeTargetLink) application
                .createComponent(HtmlOutcomeTargetLink.COMPONENT_TYPE);
        commandLink.setOutcome(model.getOutcome());
        commandLink.setTarget(model.getTarget());

        // Output params
        for (final Entry<String, String> current : model.getOutcomeParameter().entrySet()) {
            final var param = new UIParameter();
            param.setName(current.getKey());
            param.setValue(current.getValue());
            commandLink.getChildren().add(param);
        }

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
