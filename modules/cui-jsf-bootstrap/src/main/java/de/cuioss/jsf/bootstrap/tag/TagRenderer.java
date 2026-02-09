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
package de.cuioss.jsf.bootstrap.tag;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.tag.support.TagSize;
import de.cuioss.jsf.bootstrap.tag.support.TagState;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * Renderer for the {@link TagComponent} that transforms the component's model
 * into HTML markup as a DIV element with appropriate CSS classes.
 * 
 * <h2>CSS Structure</h2>
 * <ul>
 * <li>Base class: <code>cui-tag</code></li>
 * <li>State classes: Based on {@link TagState} (e.g., <code>cui-tag-info</code>)</li>
 * <li>Size classes: Based on {@link TagSize} (e.g., <code>cui-tag-lg</code>)</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@ResourceDependency(library = "thirdparty.js", name = "selectize.js", target = "head")
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.TAG_COMPONENT_RENDERER)
public class TagRenderer extends BaseDecoratorRenderer<TagComponent> {

    /**
     * Constructs a new TagRenderer with decoration enabled.
     * This ensures that component attributes are properly processed
     * during the rendering phase.
     */
    public TagRenderer() {
        super(true);
    }

    /**
     * Renders the opening DIV element for the tag component with all necessary
     * attributes and content. This includes:
     * <ul>
     * <li>CSS classes based on component state and size</li>
     * <li>Style attributes</li>
     * <li>Pass-through attributes</li>
     * <li>Title attribute</li>
     * <li>Client ID as needed</li>
     * <li>Content with appropriate escaping</li>
     * </ul>
     *
     * @param context the FacesContext for the current request
     * @param writer the DecoratingResponseWriter for writing HTML
     * @param component the TagComponent being rendered
     * @throws IOException if an error occurs writing to the response
     */
    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<TagComponent> writer,
            final TagComponent component) throws IOException {
        // Write element
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(computeStyleClass(component));
        writer.withAttributeStyle(component);
        writer.withPassThroughAttributes();
        // write title if available
        writer.withAttributeTitle(component);

        if (component.isDisposable()) {
            writer.withClientId();
        } else {
            // Special case here: We only need to render clientId if there are
            // no additional ClientBehavior defined, and no id is set by client
            final var componentId = component.getId();
            if (writer.getComponentWrapper().getClientBehaviors().size() > 1
                    || !MoreStrings.isEmpty(componentId) && !componentId.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
                writer.withClientId();
            }
        }

        // write content if available
        writer.withTextContent(component.resolveContent(), component.getContentEscape());
    }

    /**
     * Renders the closing DIV element for the tag component.
     *
     * @param context the FacesContext for the current request
     * @param writer the DecoratingResponseWriter for writing HTML
     * @param component the TagComponent being rendered
     * @throws IOException if an error occurs writing to the response
     */
    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<TagComponent> writer,
            final TagComponent component) throws IOException {
        writer.withEndElement(Node.DIV);
    }

    /**
     * Computes the complete CSS class string for the tag component based on:
     * <ul>
     * <li>Base cui-tag class</li>
     * <li>Component's custom CSS classes</li>
     * <li>State class from the component's context state</li>
     * <li>Size class from the component's context size</li>
     * </ul>
     *
     * @param component the TagComponent being rendered
     * @return a StyleClassBuilder containing all computed CSS classes
     */
    private static StyleClassBuilder computeStyleClass(final TagComponent component) {
        // Create style-class
        return CssCuiBootstrap.TAG.getStyleClassBuilder().append(component)
                .append(TagState.getForContextState(component.resolveContextState()))
                .append(TagSize.getForContextSize(component.resolveContextSize()));
    }
}
