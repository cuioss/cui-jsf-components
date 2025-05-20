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
 * <p>Renderer responsible for rendering the {@link FieldsetComponent} as HTML5 fieldset
 * element. This renderer handles the generation of HTML markup for the fieldset and its
 * associated legend element.</p>
 * 
 * <p>The renderer produces a standard HTML5 fieldset structure:</p>
 * <pre>
 * &lt;fieldset [attributes]&gt;
 *   &lt;legend&gt;Legend text (if provided)&lt;/legend&gt;
 *   [component children]
 * &lt;/fieldset&gt;
 * </pre>
 * 
 * <p>It handles various component attributes including:</p>
 * <ul>
 *   <li>Style and styleClass for CSS styling</li>
 *   <li>Pass-through attributes for additional HTML attributes</li>
 *   <li>Legend text with optional escaping</li>
 * </ul>
 * 
 * <p>This renderer is thread-safe as it maintains no state between requests.</p>
 * 
 * @author Oliver Wolff
 * @see FieldsetComponent The component this renderer is associated with
 * @since 1.0
 */
@FacesRenderer(rendererType = CuiFamily.FIELDSET_RENDERER, componentFamily = CuiFamily.COMPONENT_FAMILY)
public class FieldsetRenderer extends BaseDecoratorRenderer<FieldsetComponent> {

    /**
     * Default constructor that initializes the renderer.
     * Setting the parameter for deferredWriting to false as the component doesn't
     * need deferred writing capabilities.
     */
    public FieldsetRenderer() {
        super(false);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Renders the opening fieldset tag with all attributes and the legend element
     * if a legend text is provided by the component.</p>
     * 
     * @param context the FacesContext for the current request
     * @param writer the decorator writer to use for output
     * @param component the fieldset component being rendered
     * @throws IOException if an error occurs while writing to the response
     */
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

    /**
     * {@inheritDoc}
     * 
     * <p>Renders the closing fieldset tag.</p>
     * 
     * @param context the FacesContext for the current request
     * @param writer the decorator writer to use for output
     * @param component the fieldset component being rendered
     * @throws IOException if an error occurs while writing to the response
     */
    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<FieldsetComponent> writer,
            final FieldsetComponent component) throws IOException {
        writer.withEndElement(Node.FIELDSET);
    }
}
