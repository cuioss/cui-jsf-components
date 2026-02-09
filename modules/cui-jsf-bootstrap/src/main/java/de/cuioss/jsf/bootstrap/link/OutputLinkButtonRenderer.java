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
package de.cuioss.jsf.bootstrap.link;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * Renderer for {@link OutputLinkButton} that generates Bootstrap-styled link buttons.
 * 
 * <h3>Generated Markup</h3>
 * <pre>
 * &lt;a href="..." class="btn btn-{state} btn-{size}" title="..."&gt;
 *   &lt;!-- Optional left icon --&gt;
 *   &lt;i class="icon-class"&gt;&lt;/i&gt;
 *   &lt;span class="btn-text"&gt;Button Label&lt;/span&gt;
 *   &lt;!-- Optional child content --&gt;
 *   &lt;!-- Optional right icon --&gt;
 * &lt;/a&gt;
 * </pre>
 * 
 * <h3>Rendering Process</h3>
 * <ol>
 *   <li>Delegates to standard HtmlOutputLink renderer for base structure</li>
 *   <li>Adds configured icons and button text with appropriate styling</li>
 *   <li>Renders any child components</li>
 * </ol>
 *
 * @author Oliver Wolff
 * @see OutputLinkButton
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.OUTPUT_LINK_BUTTON_RENDERER)
public class OutputLinkButtonRenderer extends BaseDecoratorRenderer<OutputLinkButton> {

    /**
     * Constructor that initializes the renderer as a partial renderer.
     * Configures the renderer to accept and process child components.
     */
    public OutputLinkButtonRenderer() {
        super(true);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Delegates the beginning of component rendering to the standard HtmlOutputLink renderer.
     * This ensures that the basic anchor tag structure is correctly generated.
     * </p>
     * 
     * @param context the FacesContext for the current request
     * @param writer the response writer for rendering content
     * @param component the OutputLinkButton component being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<OutputLinkButton> writer,
            final OutputLinkButton component) throws IOException {
        JsfHtmlComponent.HTML_OUTPUT_LINK.renderer(context).encodeBegin(context, component);
    }

    /**
     * {@inheritDoc}
     * 
     * Renders the component's children, including:
     * <ol>
     *   <li>Left-aligned icon (if configured)</li>
     *   <li>Button text in a span with the "btn-text" CSS class</li>
     *   <li>Child components (through delegation to the standard renderer)</li>
     *   <li>Right-aligned icon (if configured)</li>
     * </ol>
     * 
     * @param context the FacesContext for the current request
     * @param writer the response writer for rendering content
     * @param component the OutputLinkButton component being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    @Override
    protected void doEncodeChildren(final FacesContext context, final DecoratingResponseWriter<OutputLinkButton> writer,
            final OutputLinkButton component) throws IOException {
        if (component.isDisplayIconLeft() && component.isIconSet()) {
            var icon = IconComponent.createComponent(context);
            icon.setIcon(component.getIcon());
            icon.encodeAll(context);
        }
        var label = component.resolveLabel();
        if (null != label) {
            var output = JsfHtmlComponent.createComponent(context, JsfHtmlComponent.SPAN);
            output.setValue(label);
            output.setEscape(component.isLabelEscape());
            output.setStyleClass(CssBootstrap.BUTTON_TEXT.getStyleClass());
            output.encodeAll(context);
        }

        JsfHtmlComponent.HTML_OUTPUT_LINK.renderer(context).encodeChildren(context, component);
        if (component.isDisplayIconRight() && component.isIconSet()) {
            var icon = IconComponent.createComponent(context);
            icon.setIcon(component.getIcon());
            icon.encodeAll(context);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Delegates the end of component rendering to the standard HtmlOutputLink renderer.
     * This ensures that the anchor tag is properly closed.
     * </p>
     * 
     * @param context the FacesContext for the current request
     * @param writer the response writer for rendering content
     * @param component the OutputLinkButton component being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<OutputLinkButton> writer,
            final OutputLinkButton component) throws IOException {
        JsfHtmlComponent.HTML_OUTPUT_LINK.renderer(context).encodeEnd(context, component);
    }
}
