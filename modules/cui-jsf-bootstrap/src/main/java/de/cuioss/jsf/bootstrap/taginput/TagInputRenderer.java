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
package de.cuioss.jsf.bootstrap.taginput;

import de.cuioss.jsf.api.common.accessor.LocaleAccessor;
import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.selectize.Selectize;
import de.cuioss.jsf.bootstrap.tag.TagComponent;
import de.cuioss.jsf.bootstrap.tag.support.TagHelper;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;
import java.util.*;

/**
 * Renderer for {@link TagInputComponent} that integrates the Selectize.js JavaScript library
 * to create an interactive tag input control.
 * 
 * <h2>Key Features</h2>
 * <ul>
 *   <li>Uses HTML5 data attributes to configure Selectize.js</li>
 *   <li>Manages values as {@code Set<ConceptKeyType>} objects</li>
 *   <li>Renders static tags when component is disabled</li>
 *   <li>Supports user-created tags with {@link Selectize#CLIENT_CREATED_SUFFIX} prefixing</li>
 * </ul>
 * 
 * <p>All input and output is sanitized using {@link CuiSanitizer#PLAIN_TEXT}
 * to prevent XSS attacks.</p>
 *
 * @author Oliver Wolff
 * @author Sven Haag
 * @since 1.0
 * @see TagInputComponent
 * @see ConceptKeyStringConverter
 */
@ResourceDependency(library = "thirdparty.js", name = "selectize.js", target = "head")
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.TAG_INPUT_COMPONENT_RENDERER)
public class TagInputRenderer extends BaseDecoratorRenderer<TagInputComponent> {

    private final LocaleAccessor localeAccessor = new LocaleAccessor();

    /**
     * Constructor that configures the renderer not to render children, as the
     * component doesn't support child components in its structure.
     */
    public TagInputRenderer() {
        super(false);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Extracts submitted value from request parameters and sets it as the component's
     * submitted value. The value will later be converted by {@link ConceptKeyStringConverter}.
     * </p>
     *
     * @param context          FacesContext for the request being processed
     * @param componentWrapper type-safe wrapper for the TagInputComponent
     */
    @Override
    protected void doDecode(final FacesContext context, final ComponentWrapper<TagInputComponent> componentWrapper) {
        final var value = context.getExternalContext().getRequestParameterMap().get(componentWrapper.getClientId());
        final var tagInput = componentWrapper.getWrapped();

        if (tagInput.isDisabled()) {
            return;
        }
        if (MoreStrings.isEmpty(value)) {
            tagInput.setSubmittedValue("");
            return;
        }

        tagInput.setSubmittedValue(value);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Renders the tag input component, either as an interactive input (when enabled)
     * or as static tags (when disabled).
     * </p>
     * 
     * @param context the current FacesContext
     * @param writer the response writer wrapper
     * @param component the TagInputComponent being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<TagInputComponent> writer,
            final TagInputComponent component) throws IOException {

        if (component.isDisabled()) {
            encodeDisabled(context, writer, component);
        } else {
            encodeEnabled(context, component);
        }
    }

    /**
     * Renders the enabled (interactive) version of the tag input component using
     * a standard HTML input element that will be transformed by Selectize.js.
     *
     * @param context the current FacesContext
     * @param component the TagInputComponent being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    private void encodeEnabled(final FacesContext context, final TagInputComponent component) throws IOException {
        JsfHtmlComponent.HTML_INPUT.renderer(context).encodeBegin(context, component);
        JsfHtmlComponent.HTML_INPUT.renderer(context).encodeEnd(context, component);
    }

    /**
     * Renders the disabled version of the tag input component as a collection of
     * static tag elements without interactive capabilities.
     *
     * @param context the current FacesContext
     * @param writer the response writer wrapper
     * @param component the TagInputComponent being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    private void encodeDisabled(final FacesContext context, final DecoratingResponseWriter<TagInputComponent> writer,
            final TagInputComponent component) throws IOException {
        TagHelper.writeDisabled(context, writer, createTags(component.getValue()), component.getStyle(),
                component.getStyleClass());
    }

    /**
     * Creates a list of TagComponent instances from the given collection of ConceptKeyType objects.
     * These tags will be rendered in disabled mode when the component is disabled.
     *
     * @param values the collection of ConceptKeyType objects to convert to tags
     * @return a List of TagComponent instances ready for rendering
     */
    private List<TagComponent> createTags(final Collection<ConceptKeyType> values) {
        return TagHelper.createFromConceptKeys(null != values ? new TreeSet<>(values) : Collections.emptySortedSet(),
                localeAccessor.getValue(), true, ContextSize.LG.name(), ContextState.DEFAULT.name());
    }
}
