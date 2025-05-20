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
package de.cuioss.jsf.bootstrap.button;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.api.components.renderer.ElementReplacingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * Renderer for {@link CloseCommandButton} that produces a Bootstrap-styled close button.
 * Adds proper styling and accessibility attributes to the standard JSF command button.
 * 
 * <h3>Generated HTML Structure</h3>
 * <pre>
 * &lt;button type="submit" class="close" aria-label="Close"&gt;
 *   &lt;span aria-hidden="true" class="button-text"&gt;×&lt;/span&gt;
 * &lt;/button&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see CloseCommandButton
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.CLOSE_COMMAND_BUTTON_RENDERER)
public class CloseCommandButtonRenderer extends BaseDecoratorRenderer<CloseCommandButton> {

    public CloseCommandButtonRenderer() {
        super(false);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Renders the close button by:</p>
     * <ol>
     *   <li>Creating an element-replacing response writer that transforms "input" to "button"</li>
     *   <li>Applying Bootstrap close button styling</li>
     *   <li>Adding a span element containing the close icon ("×") with proper accessibility attributes</li>
     * </ol>
     * 
     * @param context the FacesContext
     * @param writer the response writer wrapped for the CloseCommandButton
     * @param component the CloseCommandButton being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<CloseCommandButton> writer,
            final CloseCommandButton component) throws IOException {

        var wrapped = ElementReplacingResponseWriter.createWrappedReplacingResonseWriter(context, "input", "button",
                true);

        component.resolveAndStoreTitle();
        component.writeTitleToParent();

        component.computeAndStoreFinalStyleClass(CssBootstrap.BUTTON_CLOSE.getStyleClassBuilder());
        component.writeStyleClassToParent();

        JsfHtmlComponent.COMMAND_BUTTON.renderer(context).encodeBegin(wrapped, component);

        var output = JsfHtmlComponent.SPAN.component(context);

        output.getPassThroughAttributes(true).put(AttributeName.ARIA_HIDDEN.getContent(),
                AttributeValue.TRUE.getContent());

        output.setValue("&#xD7;");
        output.setEscape(false);
        output.setStyleClass(CssBootstrap.BUTTON_TEXT.getStyleClass());

        output.encodeAll(context);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Delegates to the standard command button renderer for handling form submissions.</p>
     * 
     * @param context the FacesContext
     * @param component the CloseCommandButton component
     */
    @Override
    public void decode(final FacesContext context, final UIComponent component) {
        JsfHtmlComponent.COMMAND_BUTTON.renderer(context).decode(context, component);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Completes the button element by writing the closing tag.</p>
     * 
     * @param context the FacesContext
     * @param writer the response writer wrapped for the CloseCommandButton
     * @param component the CloseCommandButton being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<CloseCommandButton> writer,
            final CloseCommandButton component) throws IOException {
        writer.withEndElement(Node.BUTTON);
    }
}
