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

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSeparator;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * <p>A utility class responsible for rendering {@link NavigationMenuItemSeparator} models as
 * Bootstrap-compatible menu divider lines. This renderer creates visual separators between
 * menu items in a dropdown menu.</p>
 * 
 * <p>The separator is rendered as a simple list item with the Bootstrap "divider" CSS class,
 * which displays as a horizontal dividing line in dropdown menus. This is typically used to
 * group related menu items visually, separating different functional sections within a menu.</p>
 * 
 * <h2>Features</h2>
 * <ul>
 *   <li>Lightweight rendering of horizontal divider lines in menus</li>
 *   <li>Bootstrap-compatible styling with the list-divider class</li>
 *   <li>Proper ID generation and style handling</li>
 *   <li>Conditional rendering based on the model's rendered property</li>
 * </ul>
 * 
 * <h2>HTML structure</h2>
 * <p>The renderer produces the following minimal HTML structure:</p>
 *
 * <pre>
 * &lt;li class="divider"&gt;&lt;/li&gt;
 * </pre>
 *
 * @author Sven Haag
 * @see NavigationMenuItemSeparator
 * @see NavigationMenuComponent
 * @see NavigationMenuRenderer
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NavigationMenuSeparatorRenderer {

    /**
     * <p>Renders a menu separator item based on a {@link NavigationMenuItemSeparator} model.</p>
     * 
     * <p>This method generates a simple list item with the Bootstrap divider class that
     * displays as a horizontal line separating groups of menu items in a dropdown menu.</p>
     * 
     * <p>The separator will not be rendered if the model's rendered property is false.</p>
     *
     * @param writer the decorating response writer for generating HTML output
     * @param model the navigation menu separator model to render
     * @param component the parent navigation menu component
     * @param idExtension string to append to the generated component ID for uniqueness
     * @throws IOException if an error occurs during the rendering process
     */
    static void render(DecoratingResponseWriter<NavigationMenuComponent> writer, NavigationMenuItemSeparator model,
            NavigationMenuComponent component, final String idExtension) throws IOException {
        if (!model.isRendered()) {
            return;
        }

        writer.withStartElement(Node.LI);
        writer.withClientId(idExtension);
        writer.withPassThroughAttributes();
        writer.withAttributeStyle(component.getStyle());
        writer.withStyleClass(component.getStyleClassBuilder().append(CssBootstrap.LIST_DIVIDER));

        writer.withEndElement(Node.LI);
    }
}
