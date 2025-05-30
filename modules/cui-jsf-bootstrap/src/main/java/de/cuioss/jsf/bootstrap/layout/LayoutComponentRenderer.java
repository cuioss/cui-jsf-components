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
package de.cuioss.jsf.bootstrap.layout;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;
import jakarta.faces.render.Renderer;

import java.io.IOException;

/**
 * <p>
 * Core renderer implementation for all variants of {@link AbstractLayoutComponent} in the Bootstrap
 * component hierarchy. This renderer provides the common rendering behavior for layout components.
 * </p>
 * 
 * <h2>Rendering Structure</h2>
 * <p>
 * This renderer creates a basic div wrapper with the component's resolved style classes:
 * </p>
 * <pre>
 * &lt;div class="[resolved-style-classes]" style="[style]"&gt;
 *   [child components]
 * &lt;/div&gt;
 * </pre>
 * 
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Rendering the basic Bootstrap layout container structure</li>
 *   <li>Applying component-specific style classes resolved by the component</li>
 *   <li>Handling client ID and pass-through attributes consistently</li>
 *   <li>Providing a unified rendering approach for all layout components</li>
 * </ul>
 * 
 * <p>
 * This renderer supports various Bootstrap components like {@link RowComponent}, 
 * {@link ColumnComponent}, {@link QuickControlGroupComponent}, and other layout components
 * that extend from {@link AbstractLayoutComponent}.
 * </p>
 *
 * @author Oliver Wolff
 */
@FacesRenderer(rendererType = BootstrapFamily.LAYOUT_RENDERER, componentFamily = BootstrapFamily.COMPONENT_FAMILY)
public class LayoutComponentRenderer extends BaseDecoratorRenderer<AbstractLayoutComponent> {

    /**
     */
    public LayoutComponentRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context,
            final DecoratingResponseWriter<AbstractLayoutComponent> writer, final AbstractLayoutComponent component)
            throws IOException {
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(component.resolveStyleClass());
        writer.withAttributeStyle(component.getStyle());
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();
    }

    @Override
    protected void doEncodeEnd(final FacesContext context,
            final DecoratingResponseWriter<AbstractLayoutComponent> writer, final AbstractLayoutComponent component)
            throws IOException {
        writer.withEndElement(Node.DIV);
    }
}
