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

import java.io.IOException;

import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;
import jakarta.faces.render.Renderer;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;

/**
 * {@link Renderer} for all variants of {@link AbstractLayoutComponent}
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
