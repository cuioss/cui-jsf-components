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
package de.cuioss.jsf.bootstrap.layout;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;

/**
 * Default renderer for {@link ControlGroupComponent}
 *
 * @author Oliver Wolff
 *
 */
@FacesRenderer(rendererType = BootstrapFamily.LAYOUT_CONTROL_GROUP_RENDERER, componentFamily = BootstrapFamily.COMPONENT_FAMILY)
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class ControlGroupRenderer extends BaseDecoratorRenderer<ControlGroupComponent> {

    /**
     *
     */
    public ControlGroupRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(FacesContext context, DecoratingResponseWriter<ControlGroupComponent> writer,
            ControlGroupComponent component) throws IOException {
        // Open Form group wrapper
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(CssBootstrap.FORM_GROUP.getStyleClassBuilder().append(component.getStyleClass()));
        writer.withAttributeStyle(component.getStyle());
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();

        // Open Column
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(component.resolveColumnCss());
    }

    @Override
    protected void doEncodeEnd(FacesContext context, DecoratingResponseWriter<ControlGroupComponent> writer,
            ControlGroupComponent component) throws IOException {
        // End Column
        writer.withEndElement(Node.DIV);
        // End Form group wrapper
        writer.withEndElement(Node.DIV);
    }
}
