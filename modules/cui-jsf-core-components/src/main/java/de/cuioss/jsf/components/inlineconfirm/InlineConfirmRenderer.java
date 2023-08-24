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
package de.cuioss.jsf.components.inlineconfirm;

import static de.cuioss.jsf.components.CuiFamily.COMPONENT_FAMILY;
import static de.cuioss.jsf.components.CuiFamily.INLINE_CONFIRM_RENDERER;
import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;

/**
 * @author Oliver Wolff
 */
@FacesRenderer(rendererType = INLINE_CONFIRM_RENDERER, componentFamily = COMPONENT_FAMILY)
public class InlineConfirmRenderer extends BaseDecoratorRenderer<InlineConfirmComponent> {

    static final String DATA_IDENTIFIER = "data-inline-confirm-initial";
    static final String DATA_TARGET_IDENTIFIER = "data-inline-confirm-target";

    /**
     *
     */
    public InlineConfirmRenderer() {
        super(false);
    }

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
