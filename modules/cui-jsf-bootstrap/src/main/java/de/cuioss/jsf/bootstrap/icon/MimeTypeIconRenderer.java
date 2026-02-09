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

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.icon.support.CssMimeTypeIcon;
import de.cuioss.jsf.bootstrap.icon.support.IconSize;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * <p>
 * Specialized renderer for the {@link MimeTypeIconComponent} that generates a multi-layered
 * HTML structure to display richly styled MIME type icons. This renderer creates a complex
 * icon representation using multiple styled layers to represent different file types.
 * </p>
 * 
 * <h2>Rendering Structure</h2>
 * <p>
 * The renderer implements a stacked icon approach with multiple layers:
 * </p>
 * <ol>
 *   <li><b>Base layer</b> - A folder icon that serves as the background</li>
 *   <li><b>Decorator layer</b> - An optional layer for additional styling or effects</li>
 *   <li><b>Placeholder layer</b> - A placeholder representation specific to the MIME type</li>
 *   <li><b>Icon layer</b> - The specific MIME type icon representation</li>
 * </ol>
 * 
 * <h2>Generated HTML Structure</h2>
 * <pre>
 * &lt;span class="cui-icon-stack cui-mime-type [size-class] [custom-classes]" title="[optional-title]"&gt;
 *   &lt;i class="cui-stack-base cui-mime-type-folder"&gt;&lt;/i&gt;
 *   &lt;i class="cui-stack-base [decorator-class]"&gt;&lt;/i&gt;
 *   &lt;i class="cui-stack-base cui-mime-type-placeholder [mime-type]-placeholder"&gt;&lt;/i&gt;
 *   &lt;i class="cui-stack-base [mime-type-class]"&gt;&lt;/i&gt;
 * &lt;/span&gt;
 * </pre>
 * 
 * <h2>CSS Class Structure</h2>
 * <ul>
 *   <li><b>Container classes</b>: cui-icon-stack, cui-mime-type (marker classes)</li>
 *   <li><b>Layer class</b>: cui-stack-base (applied to all layers)</li>
 *   <li><b>Base layer</b>: cui-mime-type-folder</li>
 *   <li><b>Decorator layer</b>: Customizable via decoratorClass attribute</li>
 *   <li><b>Placeholder layer</b>: cui-mime-type-placeholder plus mime-type specific placeholder class</li>
 *   <li><b>Icon layer</b>: MIME type specific icon class (e.g., cui-mime-type-pdf)</li>
 *   <li><b>Sizing classes</b>: cui-icon-xs, cui-icon-sm, cui-icon-md, cui-icon-lg, cui-icon-xl</li>
 * </ul>
 * 
 * <h2>Rendering Behavior</h2>
 * <ul>
 *   <li>Resolves the appropriate {@link MimeTypeIcon} based on component attributes</li>
 *   <li>Applies size styling based on the contextSize attribute</li>
 *   <li>Applies title/tooltip if provided</li>
 *   <li>Adds any client-specified styles and CSS classes to the container</li>
 *   <li>Generates the complete multi-layered structure for the icon</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @see MimeTypeIconComponent
 * @see MimeTypeIcon
 * @see CssMimeTypeIcon
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.MIME_TYPE_ICON_COMPONENT_RENDERER)
public class MimeTypeIconRenderer extends BaseDecoratorRenderer<MimeTypeIconComponent> {

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
        writer.withAttributeStyle(component);
        writer.withAttributeTitle(component);
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
