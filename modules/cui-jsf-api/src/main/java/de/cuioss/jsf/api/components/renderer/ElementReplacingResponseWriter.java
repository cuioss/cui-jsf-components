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
package de.cuioss.jsf.api.components.renderer;

import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static java.util.Objects.requireNonNull;

import java.io.IOException;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.FacesContextWrapper;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.context.ResponseWriterWrapper;

import lombok.Getter;

/**
 * This response writer is used for replacing certain html elements, see javadoc
 * of
 * {@link #ElementReplacingResponseWriter(ResponseWriter, String, String, boolean)}
 *
 * @author Oliver Wolff
 *
 */
public class ElementReplacingResponseWriter extends ResponseWriterWrapper {

    @Getter
    private final ResponseWriter wrapped;

    private final String filterElement;
    private final String replaceElement;
    private final boolean ignoreCloseElement;

    /**
     * Constructor.
     *
     * @param delegate           the {@link ResponseWriter} to be delegated to
     * @param filterElement      the Html element to be filtered, / replaced, must
     *                           not be null nor empty.
     * @param replaceElement     the replacement element, must not be null nor
     *                           empty.
     * @param ignoreCloseElement indicates whether to filter / ignore the closing of
     *                           an element
     */
    public ElementReplacingResponseWriter(final ResponseWriter delegate, final String filterElement,
            final String replaceElement, final boolean ignoreCloseElement) {
        super(delegate);
        wrapped = delegate;
        this.filterElement = requireNonNull(emptyToNull(filterElement));
        this.replaceElement = requireNonNull(emptyToNull(replaceElement));
        this.ignoreCloseElement = ignoreCloseElement;
    }

    @Override
    public void startElement(final String name, final UIComponent component) throws IOException {
        if (filterElement.equals(name)) {
            super.startElement(replaceElement, component);
        } else {
            super.startElement(name, component);
        }
    }

    @Override
    public void endElement(final String name) throws IOException {
        if (filterElement.equals(name)) {
            if (!ignoreCloseElement) {
                super.endElement(replaceElement);
            }
        } else {
            super.endElement(name);
        }
    }

    /**
     * @param context            to be wrapped
     * @param filterElement      the Html element to be filtered, / replaced, must
     *                           not be null nor empty.
     * @param replaceElement     the replacement element, must not be null nor
     *                           empty.
     * @param ignoreCloseElement indicates whether to filter / ignore the closing of
     *                           an element
     * @return a {@link FacesContextWrapper} providing an instance of
     *         {@link ElementReplacingResponseWriter} with the configured parameter
     */
    public static FacesContext createWrappedReplacingResonseWriter(final FacesContext context,
            final String filterElement, final String replaceElement, final boolean ignoreCloseElement) {
        return new FacesContextWrapper(context) {

            @Override
            public ResponseWriter getResponseWriter() {
                return new ElementReplacingResponseWriter(context.getResponseWriter(), filterElement, replaceElement,
                        ignoreCloseElement);
            }

        };
    }
}
