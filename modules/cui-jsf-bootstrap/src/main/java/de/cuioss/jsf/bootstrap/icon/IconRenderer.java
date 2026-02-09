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
package de.cuioss.jsf.bootstrap.icon;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.partial.IconProvider;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.icon.support.IconSize;
import de.cuioss.jsf.bootstrap.icon.support.IconState;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * <p>
 * Renderer implementation for the {@link IconComponent} that generates HTML markup for
 * CUI icons following the Bootstrap component model. This renderer outputs a span element
 * with appropriate CSS classes to display vector icons from the CUI icon library.
 * </p>
 * 
 * <h2>Rendering Behavior</h2>
 * <p>
 * The renderer implements the following behavior:
 * </p>
 * <ul>
 *   <li>Renders a single HTML span element with appropriate CSS classes</li>
 *   <li>Applies icon-specific CSS classes based on the icon name property</li>
 *   <li>Adds contextual size classes (xs, sm, md, lg, xl) based on the size property</li>
 *   <li>Adds contextual state classes (info, warning, danger, etc.) based on the state property</li>
 *   <li>Applies title/tooltip attribute if provided</li>
 *   <li>Adds any client-specified styles and CSS classes</li>
 *   <li>Falls back to {@link IconProvider#FALLBACK_ICON_STRING} if no valid icon is specified</li>
 * </ul>
 * 
 * <h2>Generated HTML Structure</h2>
 * <pre>
 * &lt;span class="cui-icon [icon-name] [size-class] [state-class] [custom-classes]" 
 *       title="[optional-title]" style="[optional-style]"&gt;&lt;/span&gt;
 * </pre>
 * 
 * <h2>CSS Class Structure</h2>
 * <ul>
 *   <li><b>Base class</b>: cui-icon (always applied as marker class)</li>
 *   <li><b>Icon type</b>: Icon-specific class like cui-icon-user, cui-icon-home</li>
 *   <li><b>Sizing classes</b>: cui-icon-xs, cui-icon-sm, cui-icon-md, cui-icon-lg, cui-icon-xl</li>
 *   <li><b>State classes</b>: cui-icon-state-info, cui-icon-state-danger, cui-icon-state-warning, etc.</li>
 *   <li><b>Custom classes</b>: Any additional classes specified through styleClass attribute</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @see IconComponent
 * @see IconSize
 * @see IconState
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.ICON_COMPONENT_RENDERER)
public class IconRenderer extends BaseDecoratorRenderer<IconComponent> {

    public IconRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeEnd(FacesContext context, DecoratingResponseWriter<IconComponent> writer,
            IconComponent component) throws IOException {

        // Write element
        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(computeStyleClass(component));
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();

        // write title if available
        writer.withAttributeTitle(component);

        // write style attribute if available
        writer.withAttributeStyle(component);

        writer.withEndElement(Node.SPAN);
    }

    private static StyleClassBuilder computeStyleClass(IconComponent component) {

        // Create style-class
        StyleClassBuilder styleClassBuilder = new StyleClassBuilderImpl(component.resolveIconCss());
        styleClassBuilder.append(component);

        // Consider State and size
        var iconState = IconState.getForContextState(component.resolveContextState());
        var iconSize = IconSize.getForContextSize(component.resolveContextSize());

        styleClassBuilder.append(iconState).append(iconSize);

        return styleClassBuilder;
    }

}
