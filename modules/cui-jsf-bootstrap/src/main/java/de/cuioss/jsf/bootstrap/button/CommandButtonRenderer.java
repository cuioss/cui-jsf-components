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
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.api.components.renderer.ElementReplacingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.button.support.ButtonSize;
import de.cuioss.jsf.bootstrap.button.support.ButtonState;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;
import jakarta.faces.render.Renderer;

import java.io.IOException;

/**
 * <h2>Rendering</h2>
 * <p>
 * This {@link Renderer} uses the concrete implementation specific Renderer for
 * {@code h:commandButton} accessed by accessed by
 * {@link JsfHtmlComponent#BUTTON}. This is used for creating the start element
 * of the element including all attributes like onclick,.. . On the fly the
 * {@code input} element will be replaced by {@code button}. This is done by
 * passing an instance of {@link ElementReplacingResponseWriter}. The
 * {@link Renderer#decode(FacesContext, UIComponent)} will be passed to the
 * specific renderer as well.
 * </p>
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is btn</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.COMMAND_BUTTON_RENDERER)
public class CommandButtonRenderer extends BaseDecoratorRenderer<CommandButton> {

    /**
     *
     */
    public CommandButtonRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<CommandButton> writer,
            final CommandButton component) throws IOException {
        var wrapped = ElementReplacingResponseWriter.createWrappedReplacingResonseWriter(context, "input", "button",
                true);

        component.resolveAndStoreTitle();
        component.writeTitleToParent();

        component.computeAndStoreFinalStyleClass(CssBootstrap.BUTTON.getStyleClassBuilder()
                .append(ButtonState.getForContextState(component.getState()))
                .append(ButtonSize.getForContextSize(component.resolveContextSize())));

        component.writeStyleClassToParent();

        JsfHtmlComponent.COMMAND_BUTTON.renderer(context).encodeBegin(wrapped, component);

        if (component.isDisplayIconLeft()) {
            var icon = IconComponent.createComponent(context);
            icon.setIcon(component.getIcon());
            icon.encodeAll(context);
        }
        var label = component.resolveLabel();
        if (null != label) {
            var output = JsfHtmlComponent.SPAN.component(context);
            output.setValue(label);
            output.setEscape(component.isLabelEscape());
            output.setStyleClass(CssBootstrap.BUTTON_TEXT.getStyleClass());
            output.encodeAll(context);
        }
        if (component.isDisplayIconRight()) {
            var icon = IconComponent.createComponent(context);
            icon.setIcon(component.getIcon());
            icon.encodeAll(context);
        }
    }

    @Override
    public void decode(final FacesContext context, final UIComponent component) {
        JsfHtmlComponent.COMMAND_BUTTON.renderer(context).decode(context, component);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<CommandButton> writer,
            final CommandButton component) throws IOException {
        writer.withEndElement(Node.BUTTON);
    }
}
