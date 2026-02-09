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

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.io.Serializable;

/**
 * Enhanced response writer that provides convenience methods for rendering JSF components.
 * <p>
 * This class extends the basic {@link ResponseWriterBase} with additional functionality
 * specifically designed for component rendering. It uses a fluent API style similar to
 * {@link HtmlTreeBuilder} for building HTML output.
 * </p>
 * <p>
 * The key feature of this class is that it maintains a reference to the component being rendered,
 * eliminating the need to pass the component to each method call. This simplifies renderer
 * implementations significantly.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * protected void doEncodeBegin(FacesContext context, DecoratingResponseWriter&lt;MyComponent&gt; writer,
 *                              MyComponent component) throws IOException {
 *     writer.withStartElement(Node.DIV)
 *           .withStyleClass(component.getStyleClass())
 *           .withClientIdIfNecessary()
 *           .withAttributeTitle(component.getTitle())
 *           .withPassThroughAttributes()
 *           .withTextContent("Content of my component", true);
 * }
 * </pre>
 * <p>
 * This class is typically used in conjunction with {@code BaseDecoratorRenderer} implementations
 * to streamline the component rendering process.
 * </p>
 * <p>
 * Note: This class is not thread-safe and should be used within a single request context.
 * </p>
 *
 * @author Oliver Wolff
 * @param <T> The type of the wrapped component, must be at least {@link UIComponent}
 * @since 1.0
 * @see ResponseWriterBase
 * @see HtmlTreeBuilder
 */
@SuppressWarnings({"GrazieInspection", "resource", "UnusedReturnValue"})
public class DecoratingResponseWriter<T extends UIComponent> extends ResponseWriterBase {

    /**
     * The faces context associated with this response writer.
     */
    private final FacesContext facesContext;

    /**
     * The UIComponent being rendered by this response writer.
     */
    @Getter
    @NonNull
    private final T component;

    /**
     * A wrapper around the component that provides additional utility methods.
     */
    @Getter
    private final ComponentWrapper<T> componentWrapper;

    /**
     * Creates a new instance of DecoratingResponseWriter.
     * <p>
     * The constructor initializes the writer with the given faces context and component,
     * and sets up the internal component wrapper for convenience methods.
     * </p>
     *
     * @param facesContext the current JSF context, must not be null
     * @param component the component to be rendered, must not be null
     * @throws NullPointerException if either parameter is null
     */
    public DecoratingResponseWriter(final FacesContext facesContext, final T component) {
        super(requireNonNull(facesContext.getResponseWriter()));
        this.component = requireNonNull(component);
        componentWrapper = new ComponentWrapper<>(component);
        this.facesContext = facesContext;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Starts writing an HTML element with the specified node type.
     * </p>
     *
     * @param node the HTML element type to write, must not be null
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public DecoratingResponseWriter<T> withStartElement(final Node node) throws IOException {
        super.withStartElement(node);
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Ends writing an HTML element with the specified node type.
     * </p>
     *
     * @param node the HTML element type to close, must not be null
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public DecoratingResponseWriter<T> withEndElement(final Node node) throws IOException {
        super.withEndElement(node);
        return this;
    }

    /**
     * Writes text content to the response, with optional HTML escaping.
     * <p>
     * If the provided text is null or empty, nothing will be written.
     * </p>
     *
     * @param text the text content to write
     * @param escape whether the text should be HTML-escaped (true) or written as-is (false)
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    public DecoratingResponseWriter<T> withTextContent(final String text, final boolean escape) throws IOException {
        if (!MoreStrings.isEmpty(text)) {
            if (escape) {
                writeText(text, null, null);
            } else {
                write(text);
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds a CSS class attribute to the current element.
     * </p>
     *
     * @param styleClass the CSS class name to add
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public DecoratingResponseWriter<T> withStyleClass(final String styleClass) throws IOException {
        super.withStyleClass(styleClass);
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds a CSS class attribute to the current element using a StyleClassProvider.
     * </p>
     *
     * @param styleClass the provider of CSS class names
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public DecoratingResponseWriter<T> withStyleClass(final StyleClassProvider styleClass) throws IOException {
        super.withStyleClass(styleClass);
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds a CSS class attribute to the current element using a StyleClassBuilder.
     * </p>
     *
     * @param styleClass the builder containing CSS class names
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public DecoratingResponseWriter<T> withStyleClass(final StyleClassBuilder styleClass) throws IOException {
        super.withStyleClass(styleClass);
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds an attribute with the specified name and value to the current element.
     * </p>
     *
     * @param attributeName the name of the attribute, must not be null
     * @param attributeValue the value of the attribute
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public DecoratingResponseWriter<T> withAttribute(final AttributeName attributeName, final String attributeValue)
            throws IOException {
        super.withAttribute(attributeName, attributeValue);
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds an attribute with the specified name and predefined value to the current element.
     * </p>
     *
     * @param attributeName the name of the attribute, must not be null
     * @param attributeValue the predefined value of the attribute
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public DecoratingResponseWriter<T> withAttribute(final AttributeName attributeName,
            final AttributeValue attributeValue) throws IOException {
        super.withAttribute(attributeName, attributeValue);
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds a title attribute to the current element.
     * </p>
     *
     * @param title the title text to add (if null or empty, no attribute is written)
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public DecoratingResponseWriter<T> withAttributeTitle(final String title) throws IOException {
        super.withAttributeTitle(title);
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds a title attribute to the current element using a TitleProvider.
     * </p>
     *
     * @param titleProvider the provider of the title text
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public DecoratingResponseWriter<T> withAttributeTitle(final TitleProvider titleProvider) throws IOException {
        super.withAttributeTitle(titleProvider);
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds a style attribute to the current element.
     * </p>
     *
     * @param style the CSS inline style to add (if null or empty, no attribute is written)
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public DecoratingResponseWriter<T> withAttributeStyle(final String style) throws IOException {
        super.withAttributeStyle(style);
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds a style attribute to the current element using a StyleAttributeProvider.
     * </p>
     *
     * @param style the provider of CSS inline styles
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public DecoratingResponseWriter<T> withAttributeStyle(final StyleAttributeProvider style) throws IOException {
        super.withAttributeStyle(style);
        return this;
    }

    /**
     * Writes both ID and NAME attributes for the current element using the component's client ID
     * with an optional suffix.
     * <p>
     * The resulting ID will be the component's client ID followed by an underscore and the
     * provided extension, e.g., "componentId_extension".
     * </p>
     *
     * @param idExtension the suffix to append to the client ID (null is allowed)
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    public DecoratingResponseWriter<T> withClientId(final String idExtension) throws IOException {
        final var idString = componentWrapper.getSuffixedClientId(idExtension);
        withAttribute(AttributeName.ID, idString);
        return withAttribute(AttributeName.NAME, idString);
    }

    /**
     * Writes both ID and NAME attributes for the current element using the component's client ID.
     *
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    public DecoratingResponseWriter<T> withClientId() throws IOException {
        withAttribute(AttributeName.ID, componentWrapper.getClientId());
        return withAttribute(AttributeName.NAME, componentWrapper.getClientId());
    }

    /**
     * Escapes special characters in a JavaScript identifier.
     * <p>
     * This method escapes the following characters with a backslash: :.[],=@
     * to make them safe for use in jQuery selectors.
     * </p>
     *
     * @param id the identifier to escape
     * @return the escaped identifier
     * @see <a href="http://api.jquery.com/category/selectors/">jQuery Selectors</a>
     * @see <a href="https://learn.jquery.com/using-jquery-core/faq/how-do-i-select-an-element-by-an-id-that-has-characters-used-in-css-notation/">jQuery FAQ</a>
     */
    public static String escapeJavaScriptIdentifier(final String id) {
        return id.replaceAll("(:|\\.|\\[|\\]|,|=|@)", "\\\\$1");
    }

    /**
     * Writes both ID and NAME attributes for the current element, but only if necessary
     * according to the component's requirements.
     * <p>
     * This method uses {@link ComponentWrapper#shouldRenderClientId()} to determine
     * if the client ID should be rendered.
     * </p>
     *
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     * @see ComponentWrapper#shouldRenderClientId()
     */
    public DecoratingResponseWriter<T> withClientIdIfNecessary() throws IOException {
        if (componentWrapper.shouldRenderClientId()) {
            return withClientId();
        }
        return this;
    }

    /**
     * Writes all pass-through attributes from the component to the current element.
     * <p>
     * If the component has no pass-through attributes, this method does nothing.
     * </p>
     *
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    public DecoratingResponseWriter<T> withPassThroughAttributes() throws IOException {
        super.withPassThroughAttributes(facesContext, component.getPassThroughAttributes(false));
        return this;
    }

    /**
     * Writes a hidden input field with a generated ID and specified value.
     * <p>
     * The ID will be the component's client ID followed by an underscore and the provided
     * extension, e.g., "componentId_extension".
     * </p>
     *
     * @param idExtension the suffix to append to the client ID (null is allowed)
     * @param value the value to set for the hidden field (if null, an empty value is used)
     * @return this instance for method chaining
     * @throws IOException if an I/O error occurs during writing
     */
    public DecoratingResponseWriter<T> withHiddenField(final String idExtension, final Serializable value)
            throws IOException {

        final var valueString = String.valueOf(value);

        withStartElement(Node.INPUT);
        withAttribute(AttributeName.TYPE, AttributeValue.HIDDEN);
        withClientId(idExtension);
        if (!MoreStrings.isEmpty(valueString)) {
            withAttribute(AttributeName.VALUE, valueString);
        }
        return withEndElement(Node.INPUT);
    }

    /**
     * Retrieves a transient rendering attribute stored on the component.
     * <p>
     * This is useful for storing temporary data during the rendering process
     * that should not be persisted in the component state.
     * </p>
     *
     * @param key the key for the attribute to retrieve
     * @return the value of the attribute, or null if not found
     */
    public Object getRenderAttribute(final Serializable key) {
        return component.getTransientStateHelper().getTransient(key);
    }

    /**
     * Stores a transient rendering attribute on the component.
     * <p>
     * This is useful for storing temporary data during the rendering process
     * that should not be persisted in the component state.
     * </p>
     *
     * @param key the key for the attribute to store
     * @param value the value to store
     */
    public void putRenderAttribute(final Serializable key, final Serializable value) {
        component.getTransientStateHelper().putTransient(key, value);
    }
}
