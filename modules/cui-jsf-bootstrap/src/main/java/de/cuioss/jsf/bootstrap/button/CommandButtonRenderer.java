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
 * Renderer for {@link CommandButton} that produces Bootstrap-styled command buttons.
 * Transforms standard JSF inputs into HTML5 button elements with proper styling and icons.
 * 
 * <h3>Generated HTML Structure</h3>
 * <pre>
 * &lt;button type="submit" class="btn btn-[state] btn-[size] [additional-classes]" id="..."&gt;
 *   [optional left icon component]
 *   &lt;span class="button-text"&gt;Button Label&lt;/span&gt;
 *   [optional right icon component]
 * &lt;/button&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see CommandButton
 * @see ButtonState
 * @see ButtonSize
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.COMMAND_BUTTON_RENDERER)
public class CommandButtonRenderer extends BaseDecoratorRenderer<CommandButton> {

    /**
     * Default constructor that configures the renderer to not handle 
     * the children of the component.
     */
    public CommandButtonRenderer() {
        super(false);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Renders the command button by:</p>
     * <ol>
     *   <li>Creating an element-replacing response writer that transforms "input" to "button"</li>
     *   <li>Resolving and applying button styling classes (state and size)</li>
     *   <li>Rendering the button content: left icon (if applicable), label text, right icon (if applicable)</li>
     * </ol>
     * 
     * @param context the FacesContext
     * @param writer the response writer wrapped for the CommandButton
     * @param component the CommandButton being rendered
     * @throws IOException if an error occurs during writing to the response
     */
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

    /**
     * {@inheritDoc}
     * 
     * <p>Delegates to the standard command button renderer for handling form submissions.</p>
     * 
     * @param context the FacesContext
     * @param component the CommandButton component
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
     * @param writer the response writer wrapped for the CommandButton
     * @param component the CommandButton being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<CommandButton> writer,
            final CommandButton component) throws IOException {
        writer.withEndElement(Node.BUTTON);
    }
}
