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
package de.cuioss.jsf.bootstrap.waitingindicator;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * Renderer for the {@link WaitingIndicatorComponent} that creates a visual spinner
 * with five animated elements.
 * 
 * <h2>Generated HTML Structure</h2>
 * <pre>
 * &lt;div id="..." class="..."&gt;
 *   &lt;div class="waiting-indicator waiting-indicator-size-[size]"&gt;
 *     &lt;div class="item-1 item-size-[size]"&gt;&lt;/div&gt;
 *     &lt;div class="item-2 item-size-[size]"&gt;&lt;/div&gt;
 *     ...
 *   &lt;/div&gt;
 * &lt;/div&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.WAITING_INDICATOR_RENDERER)
public class WaitingIndicatorRenderer extends BaseDecoratorRenderer<WaitingIndicatorComponent> {

    /**
     * Default constructor that configures this renderer to handle the children
     * of the component.
     */
    public WaitingIndicatorRenderer() {
        super(true);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Renders the waiting indicator structure with 5 animated div elements.
     * The size of the indicator is controlled by the component's size attribute.</p>
     * 
     * @param context the FacesContext
     * @param writer the decorating response writer
     * @param component the WaitingIndicatorComponent being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    @Override
    protected void doEncodeEnd(FacesContext context, DecoratingResponseWriter<WaitingIndicatorComponent> writer,
            WaitingIndicatorComponent component) throws IOException {

        // Write element
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(component);
        writer.withAttributeStyle(component);
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();

        writer.withStartElement(Node.DIV);
        writer.withStyleClass("waiting-indicator waiting-indicator-size-" + component.getSize());

        for (var i = 1; i <= 5; i++) {
            writer.withStartElement(Node.DIV);
            writer.withStyleClass("item-" + i + " item-size-" + component.getSize());
            writer.withEndElement(Node.DIV);
            writer.write(" ");
        }

        writer.withEndElement(Node.DIV);

        writer.withEndElement(Node.DIV);
    }

}
