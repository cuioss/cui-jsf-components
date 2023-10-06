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
package de.cuioss.jsf.bootstrap.accordion;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.layout.BootstrapPanelComponent;

/**
 * Renderer for {@linkplain AccordionComponent}.
 *
 * @author Matthias Walliczek
 * @author Sven Haag
 */
@FacesRenderer(rendererType = BootstrapFamily.ACCORDION_RENDERER, componentFamily = BootstrapFamily.COMPONENT_FAMILY)
public class AccordionRenderer extends BaseDecoratorRenderer<AccordionComponent> {

    /***/
    public AccordionRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<AccordionComponent> writer,
            final AccordionComponent component) throws IOException {
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(CssBootstrap.PANEL_GROUP.getStyleClassBuilder().append(component.getStyleClass()));
        writer.withAttributeStyle(component.getStyle());
        writer.withClientId();
        writer.withPassThroughAttributes();
        writer.withAttribute(AttributeName.ROLE, AttributeValue.TABLIST);
        writer.withAttribute(AttributeName.ARIA_MULTISELECTABLE, String.valueOf(component.isMultiselectable()));
    }

    @Override
    protected void doEncodeChildren(final FacesContext context,
            final DecoratingResponseWriter<AccordionComponent> writer, final AccordionComponent component)
            throws IOException {

        final var activeIndexes = component.resolveActiveIndexes();

        var i = 0;
        for (UIComponent currentChild : component.getChildren()) {
            if (currentChild instanceof BootstrapPanelComponent panel) {
                if (!component.isMultiselectable()) {
                    // TODO deliver own JS that collects the toggle state/s and updates with a
                    // single AJAX request.
                    panel.setAsyncUpdate(false);
                    panel.setDeferred(false);
                    panel.setDataParent(component.getClientId());
                }

                // take precedence over other collapse attributes
                panel.setCollapsible(true);
                panel.setCollapsed(!activeIndexes.contains(i));
                i++;

                panel.encodeAll(context);
            }
        }
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<AccordionComponent> writer,
            final AccordionComponent component) throws IOException {
        writer.withEndElement(Node.DIV);
    }
}
