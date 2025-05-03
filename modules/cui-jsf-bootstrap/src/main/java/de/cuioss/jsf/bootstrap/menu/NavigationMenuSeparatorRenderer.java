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

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSeparator;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import jakarta.faces.render.Renderer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * {@link Renderer} utility for the {@link NavigationMenuItemSeparator} model.
 *
 * <h2>HTML structure</h2>
 *
 * <pre>
 * &lt;li class="divider"&gt;
 * </pre>
 *
 * @author Sven Haag
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NavigationMenuSeparatorRenderer {

    /**
     * @param writer
     * @param model
     * @param component
     * @param idExtension
     * @throws IOException
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
