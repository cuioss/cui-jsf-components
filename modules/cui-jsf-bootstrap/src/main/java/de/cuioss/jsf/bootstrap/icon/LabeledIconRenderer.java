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

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * <p>
 * Renderer implementation for the {@link LabeledIconComponent} that generates HTML markup
 * combining an icon with a text label. This renderer creates a structured HTML representation
 * with appropriate CSS classes to display both an icon from the CUI icon library and
 * associated text, with configurable positioning.
 * </p>
 *
 * <h2>Rendering Behavior</h2>
 * <p>
 * The renderer implements the following behavior:
 * </p>
 * <ul>
 * <li>Renders a container span element that wraps both the icon and text label</li>
 * <li>Renders the icon using the appropriate icon CSS classes</li>
 * <li>Renders the text label in a separate span element</li>
 * <li>Controls the order of icon and text based on the iconAlign property</li>
 * <li>Applies title/tooltip attribute if provided</li>
 * <li>Adds any client-specified styles and CSS classes to the container</li>
 * </ul>
 *
 * <h2>Generated HTML Structure</h2>
 * <p>
 * For iconAlign="left" (default):
 * </p>
 * <pre>
 * &lt;span class="cui-labeled-icon [custom-classes]" title="[optional-title]" style="[optional-style]"&gt;
 *   &lt;span class="cui-icon [icon-name]"&gt;&lt;/span&gt;
 *   &lt;span class="cui-labeled-icon-text"&gt;Label text&lt;/span&gt;
 * &lt;/span&gt;
 * </pre>
 *
 * <p>
 * For iconAlign="right":
 * </p>
 * <pre>
 * &lt;span class="cui-labeled-icon [custom-classes]" title="[optional-title]" style="[optional-style]"&gt;
 *   &lt;span class="cui-labeled-icon-text"&gt;Label text&lt;/span&gt;
 *   &lt;span class="cui-icon [icon-name]"&gt;&lt;/span&gt;
 * &lt;/span&gt;
 * </pre>
 *
 * <h2>CSS Class Structure</h2>
 * <ul>
 * <li><b>Container class</b>: cui-labeled-icon (always applied as marker class)</li>
 * <li><b>Label class</b>: cui-labeled-icon-text (applied to the label span)</li>
 * <li><b>Icon classes</b>: Standard CUI icon classes (applied to the icon span)</li>
 * <li><b>Custom classes</b>: Any additional classes specified through styleClass attribute</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @see LabeledIconComponent
 * @see IconRenderer
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.LABELED_ICON_COMPONENT_RENDERER)
public class LabeledIconRenderer extends BaseDecoratorRenderer<LabeledIconComponent> {

    public LabeledIconRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<LabeledIconComponent> writer,
            final LabeledIconComponent component) throws IOException {
        // Write wrapper span
        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(CssCuiBootstrap.LABELED_ICON_WRAPPER.getStyleClassBuilder().append(component));
        writer.withAttributeStyle(component);
        writer.withClientIdIfNecessary();
        writer.withAttributeTitle(component);
        writer.withPassThroughAttributes();

        if (AlignHolder.RIGHT.equals(component.resolveIconAlign())) {
            writeLabel(writer, component);
            writeIcon(writer, component);
        } else {
            writeIcon(writer, component);
            writeLabel(writer, component);
        }

        // End wrapper span
        writer.withEndElement(Node.SPAN);
    }

    private static void writeIcon(final DecoratingResponseWriter<LabeledIconComponent> writer,
            final LabeledIconComponent component) throws IOException {
        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(component.resolveIconCss());
        writer.withEndElement(Node.SPAN);
    }


    private static void writeLabel(final DecoratingResponseWriter<LabeledIconComponent> writer,
            final LabeledIconComponent component) throws IOException {
        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(CssCuiBootstrap.LABELED_ICON_TEXT);
        writer.withTextContent(component.resolveLabel(), component.isLabelEscape());
        writer.withEndElement(Node.SPAN);
    }
}
