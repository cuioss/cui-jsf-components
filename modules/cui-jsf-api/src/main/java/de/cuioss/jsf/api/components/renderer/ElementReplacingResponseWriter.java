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
package de.cuioss.jsf.api.components.renderer;

import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static java.util.Objects.requireNonNull;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.FacesContextWrapper;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.context.ResponseWriterWrapper;
import lombok.Getter;

import java.io.IOException;

/**
 * A specialized {@link ResponseWriterWrapper} that dynamically replaces one HTML element type
 * with another during rendering.
 * <p>
 * This class can be used when you need to render a component with a different HTML element
 * than originally defined, without modifying the component implementation itself. It works
 * by intercepting calls to write start and end elements, and replacing specified element names
 * with alternatives.
 * </p>
 * <p>
 * For example, you might want to render a component that normally outputs a &lt;div&gt;
 * element as a &lt;span&gt; element instead. This writer enables that transformation
 * transparently during the rendering process.
 * </p>
 * <p>
 * The class also supports optionally omitting the closing tag of the replaced element
 * if needed.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * // Replace div elements with span elements
 * ResponseWriter original = context.getResponseWriter();
 * ElementReplacingResponseWriter writer = new ElementReplacingResponseWriter(
 *     original, "div", "span", false);
 *     
 * // Use the writer normally - div elements will be rendered as span
 * writer.startElement("div", component); // Will actually write &lt;span&gt;
 * writer.writeText("Content", null);
 * writer.endElement("div"); // Will actually write &lt;/span&gt;
 * 
 * // Alternative usage with wrapped FacesContext
 * FacesContext wrappedContext = ElementReplacingResponseWriter.createWrappedReplacingResonseWriter(
 *     context, "div", "span", false);
 * // Use wrappedContext with components that will now render div as span
 * </pre>
 * <p>
 * This class is not thread-safe and should be used within a single request context.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ResponseWriterWrapper
 * @see FacesContextWrapper
 */
public class ElementReplacingResponseWriter extends ResponseWriterWrapper {

    /**
     * The wrapped response writer that this element-replacing writer delegates to.
     */
    @Getter
    private final ResponseWriter wrapped;

    /**
     * The HTML element name that will be replaced during rendering.
     */
    private final String filterElement;

    /**
     * The HTML element name that will replace the filtered element.
     */
    private final String replaceElement;

    /**
     * Flag indicating whether to skip writing the closing tag for the replaced element.
     */
    private final boolean ignoreCloseElement;

    /**
     * Creates a new ElementReplacingResponseWriter with specified element replacement settings.
     * <p>
     * This constructor initializes the writer with the given ResponseWriter delegate and
     * element replacement configuration.
     * </p>
     *
     * @param delegate the {@link ResponseWriter} to be delegated to, must not be null
     * @param filterElement the HTML element name to be replaced (e.g., "div"), 
     *                     must not be null or empty
     * @param replaceElement the replacement HTML element name (e.g., "span"), 
     *                      must not be null or empty
     * @param ignoreCloseElement if true, the closing tag for the replaced element will be omitted;
     *                         if false, the closing tag will be written with the replacement element name
     * @throws NullPointerException if delegate, filterElement, or replaceElement is null
     * @throws IllegalArgumentException if filterElement or replaceElement is empty
     */
    public ElementReplacingResponseWriter(final ResponseWriter delegate, final String filterElement,
            final String replaceElement, final boolean ignoreCloseElement) {
        super(delegate);
        wrapped = delegate;
        this.filterElement = requireNonNull(emptyToNull(filterElement));
        this.replaceElement = requireNonNull(emptyToNull(replaceElement));
        this.ignoreCloseElement = ignoreCloseElement;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overrides the startElement method to replace the filtered element name with
     * the replacement element name when encountered.
     * </p>
     *
     * @param name the original element name
     * @param component the UIComponent instance being rendered
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public void startElement(final String name, final UIComponent component) throws IOException {
        if (filterElement.equals(name)) {
            super.startElement(replaceElement, component);
        } else {
            super.startElement(name, component);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Overrides the endElement method to replace the filtered element name with
     * the replacement element name when encountered, or to omit the closing tag
     * entirely if ignoreCloseElement is true.
     * </p>
     *
     * @param name the original element name
     * @throws IOException if an I/O error occurs during writing
     */
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
     * Creates a wrapped FacesContext that provides an ElementReplacingResponseWriter.
     * <p>
     * This convenience method creates a FacesContextWrapper that automatically provides
     * an ElementReplacingResponseWriter when getResponseWriter() is called. This allows
     * components and renderers to transparently use the element replacement functionality
     * without explicitly creating the writer.
     * </p>
     *
     * @param context the original FacesContext to wrap, must not be null
     * @param filterElement the HTML element name to be replaced (e.g., "div"),
     *                     must not be null or empty
     * @param replaceElement the replacement HTML element name (e.g., "span"),
     *                      must not be null or empty
     * @param ignoreCloseElement if true, the closing tag for the replaced element will be omitted;
     *                         if false, the closing tag will be written with the replacement element name
     * @return a wrapped FacesContext that provides an ElementReplacingResponseWriter
     * @throws NullPointerException if any parameter is null
     * @throws IllegalArgumentException if filterElement or replaceElement is empty
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
