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
package de.cuioss.jsf.bootstrap.link;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;

/**
 * @author Oliver Wolff
 *
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.OUTPUT_LINK_BUTTON_RENDERER)
public class OutputLinkButtonRenderer extends BaseDecoratorRenderer<OutputLinkButton> {

    /**
     * Constructor.
     */
    public OutputLinkButtonRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<OutputLinkButton> writer,
            final OutputLinkButton component) throws IOException {
        JsfHtmlComponent.HTML_OUTPUT_LINK.renderer(context).encodeBegin(context, component);
    }

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

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<OutputLinkButton> writer,
            final OutputLinkButton component) throws IOException {
        JsfHtmlComponent.HTML_OUTPUT_LINK.renderer(context).encodeEnd(context, component);
    }

}
