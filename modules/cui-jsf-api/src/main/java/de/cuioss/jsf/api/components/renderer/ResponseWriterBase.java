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

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.tools.string.MoreStrings;
import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.context.ResponseWriterWrapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Base class for implementing enhanced JSF response writers with a fluent API.
 * <p>
 * This class extends {@link ResponseWriterWrapper} to provide convenience methods
 * for common HTML rendering tasks without requiring a {@link UIComponent} context,
 * unlike {@link DecoratingResponseWriter} which is component-aware.
 * </p>
 * <p>
 * The class uses a fluent interface pattern, where each method returns the writer
 * instance to allow method chaining for more readable and concise code.
 * </p>
 * <p>
 * Key features include:
 * </p>
 * <ul>
 *   <li>Simplified element writing with predefined HTML nodes</li>
 *   <li>Streamlined attribute handling</li>
 *   <li>Integrated support for style classes and inline styles</li>
 *   <li>Easy handling of pass-through attributes</li>
 * </ul>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * ResponseWriter original = context.getResponseWriter();
 * ResponseWriterBase writer = new ResponseWriterBase(original);
 * 
 * writer.withStartElement(Node.DIV)
 *       .withStyleClass("container")
 *       .withAttribute(AttributeName.ID, "content")
 *       .withStartElement(Node.SPAN)
 *       .withStyleClass("label")
 *       .withEndElement(Node.SPAN)
 *       .withEndElement(Node.DIV);
 * </pre>
 * <p>
 * This class serves as the base for more specialized response writers and provides
 * the core functionality for HTML generation in JSF renderers.
 * </p>
 * <p>
 * Note: This class is not thread-safe and should be used within a single request context.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ResponseWriterWrapper
 * @see DecoratingResponseWriter
 */
@RequiredArgsConstructor
public class ResponseWriterBase extends ResponseWriterWrapper {

    /**
     * The wrapped response writer that this base writer delegates to.
     */
    @Getter
    @NonNull
    private final ResponseWriter wrapped;

    /**
     * Starts writing an HTML element with the specified node type.
     * <p>
     * This is a convenience method that delegates to 
     * {@link ResponseWriter#startElement(String, UIComponent)} using the node's
     * content as the element name and null as the component.
     * </p>
     *
     * @param node the HTML element type to write, must not be null
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    public ResponseWriterBase withStartElement(final Node node) throws IOException {
        startElement(node.getContent(), null);
        return this;
    }

    /**
     * Ends writing an HTML element with the specified node type.
     * <p>
     * This is a convenience method that delegates to 
     * {@link ResponseWriter#endElement(String)} using the node's
     * content as the element name.
     * </p>
     *
     * @param node the HTML element type to close, must not be null
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    public ResponseWriterBase withEndElement(final Node node) throws IOException {
        endElement(node.getContent());
        return this;
    }

    /**
     * Adds a CSS class attribute to the current element.
     * <p>
     * If the provided styleClass is null or empty, no attribute will be written.
     * </p>
     *
     * @param styleClass the CSS class name to add
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    public ResponseWriterBase withStyleClass(final String styleClass) throws IOException {
        if (styleClass != null && !styleClass.isEmpty()) {
            writeAttribute(AttributeName.CLASS.getContent(), styleClass, AttributeName.CLASS.getContent());
        }
        return this;
    }

    /**
     * Adds a CSS class attribute to the current element using a StyleClassProvider.
     * <p>
     * This is a convenience method that delegates to {@link #withStyleClass(String)}
     * using the provider's styleClass value.
     * </p>
     *
     * @param styleClass the provider of CSS class names
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     * @see StyleClassProvider#getStyleClass()
     */
    public ResponseWriterBase withStyleClass(final StyleClassProvider styleClass) throws IOException {
        return withStyleClass(styleClass.getStyleClass());
    }

    /**
     * Adds a CSS class attribute to the current element using a StyleClassBuilder.
     * <p>
     * This is a convenience method that delegates to {@link #withStyleClass(String)}
     * using the builder's constructed styleClass value.
     * </p>
     *
     * @param styleClass the builder containing CSS class names
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     * @see StyleClassBuilder#getStyleClass()
     */
    public ResponseWriterBase withStyleClass(final StyleClassBuilder styleClass) throws IOException {
        return withStyleClass(styleClass.getStyleClass());
    }

    /**
     * Adds an attribute with the specified name and value to the current element.
     * <p>
     * This method writes an attribute to the current element using the attribute name
     * from the enum as both the name and property.
     * </p>
     *
     * @param attributeName the name of the attribute, must not be null
     * @param attributeValue the value of the attribute
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    public ResponseWriterBase withAttribute(final AttributeName attributeName, final String attributeValue)
            throws IOException {
        writeAttribute(attributeName.getContent(), attributeValue, attributeName.getContent());
        return this;
    }

    /**
     * Adds an attribute with the specified name and predefined value to the current element.
     * <p>
     * This is a convenience method that delegates to {@link #withAttribute(AttributeName, String)}
     * using the AttributeValue's content as the value.
     * </p>
     *
     * @param attributeName the name of the attribute, must not be null
     * @param attributeValue the predefined value of the attribute
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    public ResponseWriterBase withAttribute(final AttributeName attributeName, final AttributeValue attributeValue)
            throws IOException {
        return withAttribute(attributeName, attributeValue.getContent());
    }

    /**
     * Adds a title attribute to the current element.
     * <p>
     * If the provided title is null or empty, no attribute will be written.
     * </p>
     *
     * @param title the title text to add
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    public ResponseWriterBase withAttributeTitle(final String title) throws IOException {
        if (!MoreStrings.isEmpty(title)) {
            return withAttribute(AttributeName.TITLE, title);
        }
        return this;
    }

    /**
     * Adds a title attribute to the current element using a TitleProvider.
     * <p>
     * This is a convenience method that delegates to {@link #withAttributeTitle(String)}
     * using the provider's resolved title.
     * </p>
     *
     * @param title the provider of the title text
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     * @see TitleProvider#resolveTitle()
     */
    public ResponseWriterBase withAttributeTitle(final TitleProvider title) throws IOException {
        return withAttributeTitle(title.resolveTitle());
    }

    /**
     * Adds a style attribute to the current element.
     * <p>
     * If the provided style is null or empty, no attribute will be written.
     * </p>
     *
     * @param style the CSS inline style to add
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    public ResponseWriterBase withAttributeStyle(final String style) throws IOException {
        if (!MoreStrings.isEmpty(style)) {
            return withAttribute(AttributeName.STYLE, style);
        }
        return this;
    }

    /**
     * Adds a style attribute to the current element using a StyleAttributeProvider.
     * <p>
     * This is a convenience method that delegates to {@link #withAttributeStyle(String)}
     * using the provider's style value.
     * </p>
     *
     * @param styleProvider the provider of CSS inline styles
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     * @see StyleAttributeProvider#getStyle()
     */
    public ResponseWriterBase withAttributeStyle(final StyleAttributeProvider styleProvider) throws IOException {
        return withAttributeStyle(styleProvider.getStyle());
    }

    /**
     * Writes all pass-through attributes from a map to the current element.
     * <p>
     * This method iterates through the provided map of pass-through attributes
     * and writes each one to the current element. If a value is a ValueExpression,
     * it will be evaluated before writing.
     * </p>
     * <p>
     * If the map is null or empty, nothing will be written.
     * </p>
     *
     * @param facesContext the current faces context, must not be null
     * @param passThroughAttributes the map of attributes to render
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     * @throws NullPointerException if facesContext is null
     */
    public ResponseWriterBase withPassThroughAttributes(final FacesContext facesContext,
            final Map<String, Object> passThroughAttributes) throws IOException {
        if (null != passThroughAttributes && !passThroughAttributes.isEmpty()) {
            for (final Entry<String, Object> entry : passThroughAttributes.entrySet()) {
                getWrapped().writeAttribute(entry.getKey(), resolveValue(facesContext, entry.getValue()), null);
            }
        }
        return this;
    }

    /**
     * Resolves the actual value from a possible ValueExpression.
     * <p>
     * If the value is a ValueExpression, this method evaluates it in the current
     * EL context. Otherwise, it returns the value as-is.
     * </p>
     *
     * @param facesContext the current faces context
     * @param value the value to resolve, may be a ValueExpression
     * @return the resolved value
     */
    private static Object resolveValue(final FacesContext facesContext, final Object value) {
        if (value instanceof ValueExpression expression) {
            return expression.getValue(facesContext.getELContext());
        }
        return value;
    }
}
