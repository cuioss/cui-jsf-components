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
package de.cuioss.jsf.bootstrap.layout;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * <p>
 * Renderer implementation for {@link ControlGroupComponent} that applies Bootstrap's
 * form-group structure and column-based layout to control groups.
 * </p>
 * 
 * <h2>Rendering Structure</h2>
 * <p>
 * The rendered output follows this HTML structure:
 * </p>
 * <pre>
 * &lt;div class="form-group [additional-classes]" style="[style]"&gt;
 *   &lt;div class="[column-classes]"&gt;
 *     [child components]
 *   &lt;/div&gt;
 * &lt;/div&gt;
 * </pre>
 * 
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Applying proper form-group Bootstrap styling</li>
 *   <li>Handling column-based layout via responsive grid classes</li>
 *   <li>Managing client ID and pass-through attributes</li>
 *   <li>Maintaining proper nesting structure for Bootstrap's form system</li>
 * </ul>
 * 
 * <p>
 * This renderer works in conjunction with {@link ControlGroupComponent} to provide
 * a consistent layout for form controls and button groups that follows Bootstrap's
 * design patterns.
 * </p>
 *
 * @author Oliver Wolff
 * @see ControlGroupComponent
 */
@FacesRenderer(rendererType = BootstrapFamily.LAYOUT_CONTROL_GROUP_RENDERER, componentFamily = BootstrapFamily.COMPONENT_FAMILY)
public class ControlGroupRenderer extends BaseDecoratorRenderer<ControlGroupComponent> {

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
