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
package de.cuioss.jsf.bootstrap.tag;

import java.io.IOException;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.tag.support.TagSize;
import de.cuioss.jsf.bootstrap.tag.support.TagState;
import de.cuioss.tools.string.MoreStrings;

/**
 * Default {@link Renderer} for {@link TagComponent}
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is label</li>
 * <li>Sizing: cui-tag-lg, cui-tag-xl. ..</li>
 * <li>State:label-info, label-error,..</li>
 * </ul>
 *
 * @author Oliver Wolff
 */

@ResourceDependency(library = "thirdparty.js", name = "selectize.js", target = "head")
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.TAG_COMPONENT_RENDERER)
public class TagRenderer extends BaseDecoratorRenderer<TagComponent> {

    /**
     * TagRenderer default constructor
     */
    public TagRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<TagComponent> writer,
            final TagComponent component) throws IOException {
        // Write element
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(computeStyleClass(component));
        writer.withAttributeStyle(component.getStyle());
        writer.withPassThroughAttributes();
        // write title if available
        writer.withAttributeTitle(component.resolveTitle());

        if (component.isDisposable()) {
            writer.withClientId();
        } else {
            // Special case here: We only need to render clientId if there are
            // no additional ClientBehavior defined and no id is set by client
            final var componentId = component.getId();
            if (writer.getComponentWrapper().getClientBehaviors().size() > 1
                    || !MoreStrings.isEmpty(componentId) && !componentId.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
                writer.withClientId();
            }
        }

        // write content if available
        writer.withTextContent(component.resolveContent(), component.getContentEscape());
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<TagComponent> writer,
            final TagComponent component) throws IOException {

        writer.withEndElement(Node.DIV);
    }

    private static StyleClassBuilder computeStyleClass(final TagComponent component) {
        // Create style-class
        return CssCuiBootstrap.TAG.getStyleClassBuilder().append(component)
                .append(TagState.getForContextState(component.resolveContextState()))
                .append(TagSize.getForContextSize(component.resolveContextSize()));
    }

}
