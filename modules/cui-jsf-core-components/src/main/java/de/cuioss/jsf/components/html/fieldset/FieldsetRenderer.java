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
package de.cuioss.jsf.components.html.fieldset;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.components.CuiFamily;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * @author Oliver Wolff
 *
 */
@FacesRenderer(rendererType = CuiFamily.FIELDSET_RENDERER, componentFamily = CuiFamily.COMPONENT_FAMILY)
public class FieldsetRenderer extends BaseDecoratorRenderer<FieldsetComponent> {

    /**
     *
     */
    public FieldsetRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<FieldsetComponent> writer,
            final FieldsetComponent component) throws IOException {

        writer.withStartElement(Node.FIELDSET);
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();
        writer.withAttributeStyle(component.getStyle());
        writer.withStyleClass(component.getStyleClass());

        var legend = component.resolveLegend();

        if (null != legend) {
            writer.withStartElement(Node.LEGEND).withTextContent(legend, component.isLegendEscape())
                    .withEndElement(Node.LEGEND);
        }
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<FieldsetComponent> writer,
            final FieldsetComponent component) throws IOException {
        writer.withEndElement(Node.FIELDSET);
    }
}
