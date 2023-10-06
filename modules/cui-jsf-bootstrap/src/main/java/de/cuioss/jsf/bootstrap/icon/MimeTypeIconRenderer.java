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

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.icon.support.CssMimeTypeIcon;
import de.cuioss.jsf.bootstrap.icon.support.IconSize;

/**
 * <p>
 * Default {@link Renderer} for {@link MimeTypeIconComponent}
 * </p>
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css classes are cui-icon-stack and cui-mime-type</li>
 * <li>Sizing: cui-icon-xl, cui-icon-lg,..</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.MIME_TYPE_ICON_COMPONENT_RENDERER)
public class MimeTypeIconRenderer extends BaseDecoratorRenderer<MimeTypeIconComponent> {

    /**
     *
     */
    public MimeTypeIconRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeEnd(FacesContext context, DecoratingResponseWriter<MimeTypeIconComponent> writer,
            MimeTypeIconComponent component) throws IOException {

        // span wrapper
        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(CssMimeTypeIcon.CUI_STACKED_ICON.getStyleClassBuilder().append(component)
                .append(IconSize.getForContextSize(component.resolveContextSize())));
        writer.withAttributeStyle(component.getStyle());
        writer.withAttributeTitle(component.resolveTitle());
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();

        // Layer 1
        writer.withStartElement(Node.ITALIC);
        writer.withStyleClass(CssMimeTypeIcon.CUI_STACKED_BASE_STRING.getStyleClassBuilder()
                .append(CssMimeTypeIcon.CUI_MIME_TYPE_FOLDER));
        writer.withEndElement(Node.ITALIC);

        // Layer 2
        writer.withStartElement(Node.ITALIC);
        writer.withStyleClass(
                CssMimeTypeIcon.CUI_STACKED_BASE_STRING.getStyleClassBuilder().append(component.getDecoratorClass()));
        writer.withEndElement(Node.ITALIC);

        var mimeTypeIcon = component.resolveMimeTypeIcon();
        // Layer 3
        writer.withStartElement(Node.ITALIC);
        writer.withStyleClass(CssMimeTypeIcon.CUI_STACKED_BASE_STRING.getStyleClassBuilder()
                .append(CssMimeTypeIcon.CUI_MIME_TYPE_PLACEHOLDER).append(mimeTypeIcon.getPlaceholder()));
        writer.withEndElement(Node.ITALIC);

        // Layer 4
        writer.withStartElement(Node.ITALIC);
        writer.withStyleClass(
                CssMimeTypeIcon.CUI_STACKED_BASE_STRING.getStyleClassBuilder().append(mimeTypeIcon.getIconClass()));
        writer.withEndElement(Node.ITALIC);

        // end span wrapper
        writer.withEndElement(Node.SPAN);
    }

}
