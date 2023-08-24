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
package de.cuioss.jsf.bootstrap.icon;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.partial.IconProvider;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.icon.support.IconSize;
import de.cuioss.jsf.bootstrap.icon.support.IconState;

/**
 * <p>
 * Default {@link Renderer} for an {@link IconComponent}. In case of
 * {@link IconComponent#getIcon()} id <code>null</code> or does not define a
 * valid icon, regarding cui-icon contract it default to the icon
 * {@link IconProvider#FALLBACK_ICON_STRING}
 * </p>
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is cui-icon</li>
 * <li>Sizing: cui-icon-xl, cui-icon-lg,..</li>
 * <li>State: cui-icon-state-info, cui-icon-state-error,..</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.ICON_COMPONENT_RENDERER)
public class IconRenderer extends BaseDecoratorRenderer<IconComponent> {

    /**
     *
     */
    public IconRenderer() {
        super(true);
    }

    @Override
    @SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                                  // controlled by JSF
    protected void doEncodeEnd(FacesContext context, DecoratingResponseWriter<IconComponent> writer,
            IconComponent component) throws IOException {

        // Write element
        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(computeStyleClass(component));
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();

        // write title if available
        writer.withAttributeTitle(component.resolveTitle());

        // write style attribute if available
        writer.withAttributeStyle(component.getStyle());

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
