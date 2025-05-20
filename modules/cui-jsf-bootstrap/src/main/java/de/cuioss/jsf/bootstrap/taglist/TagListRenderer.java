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
package de.cuioss.jsf.bootstrap.taglist;

import de.cuioss.jsf.api.common.accessor.LocaleAccessor;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.tag.TagComponent;
import de.cuioss.jsf.bootstrap.tag.support.TagHelper;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;
import jakarta.faces.render.Renderer;
import jakarta.faces.view.facelets.Tag;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Renderer for the {@link TagListComponent} that creates and renders multiple
 * {@link TagComponent} instances as an HTML unordered list.
 * 
 * <h2>Generated Markup</h2>
 * <pre>
 * &lt;ul class="list-inline [additional-classes]"&gt;
 *   &lt;li&gt;[Tag 1]&lt;/li&gt;
 *   &lt;li&gt;[Tag 2]&lt;/li&gt;
 * &lt;/ul&gt;
 * </pre>
 * 
 * <p>Passes component configuration (state, size, contentEscape) to each created tag.</p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see TagListComponent
 * @see TagComponent
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.TAG_LIST_COMPONENT_RENDERER)
public class TagListRenderer extends BaseDecoratorRenderer<TagListComponent> {

    /**
     * Constructor that configures the renderer to allow rendering of children.
     * This is necessary as the tag list dynamically creates and renders child
     * components for each tag.
     */
    public TagListRenderer() {
        super(true);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Handles the rendering of the tag list by creating tag components from the
     * value and rendering them as a list.
     * </p>
     * 
     * @param context the current FacesContext
     * @param writer the response writer wrapper providing access to the component
     * @param component the TagListComponent being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<TagListComponent> writer,
            final TagListComponent component) throws IOException {
        TagHelper.writeDisabled(context, writer, createTagChildren(component), component.getStyle(),
                component.getStyleClass());
    }

    /**
     * Creates a list of TagComponent instances based on the TagListComponent's
     * value, applying consistent styling and configuration to all tags.
     *
     * @param component the source TagListComponent
     * @return a List of TagComponent instances ready for rendering
     */
    private static List<TagComponent> createTagChildren(final TagListComponent component) {
        final var contextSize = component.getSize();
        final var contextState = component.getState();
        final var locale = new LocaleAccessor().getValue();
        final var contentEscape = component.getContentEscape();

        return TagHelper.createFromConceptKeys(TagHelper.getValueAsSet(component.getValue()), locale, contentEscape,
                contextSize, contextState);
    }
}
