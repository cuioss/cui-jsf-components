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
 * <p>
 * Default {@link Renderer} for {@link TagListComponent}
 * </p>
 * <h2>Summary</h2>
 * <p>
 * Renders a number of {@link Tag}s as a a list. The tags are created from the
 * the given {@link Collection} of {@link ConceptKeyType}, see attributes
 * #value. If you need fine grained control use {@link TagComponent} directly.
 * The attributes #state, #size and #contentEscape will be passed through to the
 * used {@link TagComponent}. More information and examples can be found in the
 * <a href=
 * "https://cuioss.de/cui-reference-documentation/pages/documentation/cui_components/demo/tag.jsf"
 * >Reference Documentation</a>
 * </p>
 * <h2>Rendering</h2>
 * <p>
 * The tags will be created as list in a {@code
 *
<ul class="list-inline">
 * }
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is "list-inline" for the surrounding ul</li>
 * <li>Sizing: Will be passed through to {@link TagComponent}</li>
 * <li>State: Will be passed through to {@link TagComponent}</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.TAG_LIST_COMPONENT_RENDERER)
public class TagListRenderer extends BaseDecoratorRenderer<TagListComponent> {

    /**
     *
     */
    public TagListRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<TagListComponent> writer,
            final TagListComponent component) throws IOException {
        TagHelper.writeDisabled(context, writer, createTagChildren(component), component.getStyle(),
                component.getStyleClass());
    }

    /**
     * @param component
     * @return
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
