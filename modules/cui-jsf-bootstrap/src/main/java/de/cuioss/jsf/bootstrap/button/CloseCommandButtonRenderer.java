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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.api.components.renderer.ElementReplacingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;

/**
 * <h2>Rendering</h2>
 * <p>
 * See {@link CommandButtonRenderer} for details
 *
 * @author Oliver Wolff
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.CLOSE_COMMAND_BUTTON_RENDERER)
public class CloseCommandButtonRenderer extends BaseDecoratorRenderer<CloseCommandButton> {

    /**
     *
     */
    public CloseCommandButtonRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<CloseCommandButton> writer,
            final CloseCommandButton component) throws IOException {

        var wrapped = ElementReplacingResponseWriter.createWrappedReplacingResonseWriter(context, "input", "button",
                true);

        JsfHtmlComponent.COMMAND_BUTTON.renderer(context).encodeBegin(wrapped, component);

        var output = JsfHtmlComponent.SPAN.component(context);

        output.getPassThroughAttributes(true).put(AttributeName.ARIA_HIDDEN.getContent(),
                AttributeValue.TRUE.getContent());

        output.setValue("&#xD7;");
        output.setEscape(false);
        output.setStyleClass(CssBootstrap.BUTTON_TEXT.getStyleClass());

        output.encodeAll(context);
    }

    @Override
    public void decode(final FacesContext context, final UIComponent component) {
        JsfHtmlComponent.COMMAND_BUTTON.renderer(context).decode(context, component);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<CloseCommandButton> writer,
            final CloseCommandButton component) throws IOException {
        writer.withEndElement(Node.BUTTON);
    }
}
