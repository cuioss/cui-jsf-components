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
 * Renderer for the {@link Button} component that produces Bootstrap-compliant button markup.
 * Enhances standard button rendering with Bootstrap styling, icons, and formatted text.
 * 
 * <h3>Generated HTML Structure</h3>
 * <pre>
 * &lt;button class="btn btn-[state] btn-[size] [custom-classes]" id="..." type="button"&gt;
 *   &lt;!-- Left icon if configured --&gt;
 *   &lt;span class="glyphicon glyphicon-[icon]"&gt;&lt;/span&gt;
 *   &lt;span class="button-text"&gt;Button Label&lt;/span&gt;
 *   &lt;!-- Right icon if configured --&gt;
 *   &lt;span class="glyphicon glyphicon-[icon]"&gt;&lt;/span&gt;
 * &lt;/button&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see Button
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.BUTTON_RENDERER)
public class ButtonRenderer extends BaseDecoratorRenderer<Button> {

    public ButtonRenderer() {
        super(false);
    }

    /**
     * Renders the beginning of the button component, including any left-aligned icon
     * and the button text. 
     * <p>
     * The process includes:
     * <ol>
     *   <li>Creating a wrapped response writer that changes 'input' to 'button'</li>
     *   <li>Resolving and storing the component title</li>
     *   <li>Computing final CSS classes with Bootstrap styling</li>
     *   <li>Delegating the basic element rendering to the standard button renderer</li>
     *   <li>Rendering left icon if configured</li>
     *   <li>Rendering button text</li>
     *   <li>Rendering right icon if configured</li>
     * </ol>
     * 
     * @param context the FacesContext
     * @param writer the decorating response writer
     * @param component the Button component being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<Button> writer,
            final Button component) throws IOException {
        var wrapped = ElementReplacingResponseWriter.createWrappedReplacingResonseWriter(context, "input", "button",
                true);

        component.resolveAndStoreTitle();


        component.computeAndStoreFinalStyleClass(CssBootstrap.BUTTON.getStyleClassBuilder()
                .append(ButtonState.getForContextState(component.getState()))
                .append(ButtonSize.getForContextSize(component.resolveContextSize())));
        component.writeStyleClassToParent();

        JsfHtmlComponent.BUTTON.renderer(context).encodeBegin(wrapped, component);

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
     * Delegates the decode process to the standard button renderer.
     * 
     * @param context the FacesContext
     * @param component the UIComponent being decoded
     */
    @Override
    public void decode(final FacesContext context, final UIComponent component) {
        JsfHtmlComponent.BUTTON.renderer(context).decode(context, component);
    }

    /**
     * Renders the closing button tag to complete the component rendering.
     * 
     * @param context the FacesContext
     * @param writer the decorating response writer
     * @param component the Button component being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<Button> writer,
            final Button component) throws IOException {
        writer.withEndElement(Node.BUTTON);
    }

}
