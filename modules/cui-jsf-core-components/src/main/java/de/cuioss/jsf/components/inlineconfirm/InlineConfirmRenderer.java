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
package de.cuioss.jsf.components.inlineconfirm;

import static de.cuioss.jsf.components.CuiFamily.COMPONENT_FAMILY;
import static de.cuioss.jsf.components.CuiFamily.INLINE_CONFIRM_RENDERER;
import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * <p>Renderer responsible for rendering the {@link InlineConfirmComponent} with its two-step
 * confirmation mechanism. This renderer works by initially rendering only the 'initial' facet
 * while keeping the confirmation child hidden with CSS.</p>
 * 
 * <p>The renderer applies specific data attributes to both the initial element and the confirmation
 * element. These data attributes are then used by the associated JavaScript to handle the toggle
 * behavior when the user interacts with the initial element.</p>
 * 
 * <p>The key features of this renderer include:</p>
 * <ul>
 *   <li>Ensuring the initial facet is displayed by default</li>
 *   <li>Setting the confirmation element to be hidden initially</li>
 *   <li>Adding the required data attributes for JavaScript interaction</li>
 *   <li>Validating that the child component properly supports styling requirements</li>
 * </ul>
 * 
 * <p>The renderer delegates the actual rendering of both the initial facet and the confirmation
 * child to their respective renderers, focusing only on adding the necessary structure and
 * attributes for the toggle behavior.</p>
 * 
 * <p>This renderer is thread-safe as it maintains no state between requests.</p>
 * 
 * @author Oliver Wolff
 * @see InlineConfirmComponent The component this renderer is associated with
 * @since 1.0
 */
@FacesRenderer(rendererType = INLINE_CONFIRM_RENDERER, componentFamily = COMPONENT_FAMILY)
public class InlineConfirmRenderer extends BaseDecoratorRenderer<InlineConfirmComponent> {

    static final String DATA_IDENTIFIER = "data-inline-confirm-initial";
    static final String DATA_TARGET_IDENTIFIER = "data-inline-confirm-target";

    /**
     * Default constructor that initializes the renderer.
     * Setting the parameter for deferredWriting to false as the component doesn't
     * need deferred writing capabilities.
     */
    public InlineConfirmRenderer() {
        super(false);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Renders the initial facet as visible and the confirmation child as hidden,
     * applying the necessary data attributes for JavaScript interaction. The renderer
     * validates that the child component supports styling, which is required for the
     * hide/show functionality.</p>
     * 
     * @param context the FacesContext for the current request
     * @param writer the decorator writer to use for output
     * @param component the inline confirm component being rendered
     * @throws IOException if an error occurs while writing to the response
     * @throws IllegalArgumentException if the child component doesn't support the style attribute
     */
    @Override
    protected void doEncodeBegin(final FacesContext context,
            final DecoratingResponseWriter<InlineConfirmComponent> writer, final InlineConfirmComponent component)
            throws IOException {

        var initial = component.getInitialFacet();
        var target = component.getChildAsModifier();

        if (!target.isSupportsStyle()) {
            throw new IllegalArgumentException(
                    "The child component must support the style-attribute in order to work with this component");
        }

        initial.addPassThrough(DATA_IDENTIFIER, DATA_IDENTIFIER);

        target.setStyle(AttributeValue.STYLE_DISPLAY_NONE.getContent() + nullToEmpty(target.getStyle()));

        target.addPassThrough(DATA_TARGET_IDENTIFIER, DATA_TARGET_IDENTIFIER);

        initial.getComponent().encodeAll(context);
    }

}
