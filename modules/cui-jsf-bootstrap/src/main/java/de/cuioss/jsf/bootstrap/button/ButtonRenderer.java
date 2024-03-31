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
import java.util.Optional;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlOutcomeTargetButton;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

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

/**
 * <h2>Rendering</h2>
 * <p>
 * This {@link Renderer} uses the concrete implementation specific Renderer for
 * {@code h:button} accessed by {@link JsfHtmlComponent#BUTTON}. This is used
 * for creating the start element of the element including all attributes like
 * onclick,.. . On the fly the {@code input} element will be replaced by
 * {@code button}. This is done by passing an instance of
 * {@link ElementReplacingResponseWriter}. The
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
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.BUTTON_RENDERER)
public class ButtonRenderer extends BaseDecoratorRenderer<Button> {

    public ButtonRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<Button> writer,
            final Button component) throws IOException {
        var wrapped = ElementReplacingResponseWriter.createWrappedReplacingResonseWriter(context, "input", "button",
                true);

        component.resolveAndStoreTitle();
        var finalStyleClass = component.computeFinalStyleClass(CssBootstrap.BUTTON.getStyleClassBuilder()
            .append(ButtonState.getForContextState(component.getState()))
            .append(ButtonSize.getForContextSize(component.resolveContextSize())));
        var delegate = createButtonAndCopyAttributes(context, component);
        delegate.setStyleClass(finalStyleClass);

        JsfHtmlComponent.BUTTON.renderer(context).encodeBegin(wrapped, delegate);

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
        JsfHtmlComponent.BUTTON.renderer(context).decode(context, component);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<Button> writer,
            final Button component) throws IOException {
        writer.withEndElement(Node.BUTTON);
    }

    static HtmlOutcomeTargetButton createButtonAndCopyAttributes(FacesContext facesContext, HtmlOutcomeTargetButton source) {
        var delegate = JsfHtmlComponent.BUTTON.component(facesContext);
        Optional.ofNullable(source.getId()).ifPresent(delegate::setId);
        Optional.ofNullable(source.getStyle()).ifPresent(delegate::setStyle);
        Optional.ofNullable(source.getTitle()).ifPresent(delegate::setTitle);
        Optional.ofNullable(source.getValue()).ifPresent(delegate::setValue);
        Optional.ofNullable(source.getAlt()).ifPresent(delegate::setAlt);
        Optional.ofNullable(source.getDir()).ifPresent(delegate::setDir);
        Optional.of(source.isDisabled()).ifPresent(delegate::setDisabled);
        Optional.ofNullable(source.getAccesskey()).ifPresent(delegate::setAccesskey);
        Optional.ofNullable(source.getImage()).ifPresent(delegate::setImage);
        delegate.getPassThroughAttributes(true).putAll(source.getPassThroughAttributes(true));
        return delegate;
    }
}
