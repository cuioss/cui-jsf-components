package com.icw.ehf.cui.core.api.components.renderer;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.css.StyleClassProvider;
import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.AttributeValue;
import com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.util.ComponentWrapper;

import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;
import lombok.NonNull;

/**
 * Used for providing additional convenience methods for response writer. The
 * api is similar to {@link HtmlTreeBuilder}. The component to be rendered is in
 * a field of the {@link DecoratingResponseWriter} and must therefore not be
 * passed to the corresponding calls.
 *
 * @author Oliver Wolff
 * @param <T>
 *            identifying the wrapped component, must be at least
 *            {@link UIComponent}
 */
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class DecoratingResponseWriter<T extends UIComponent> extends ResponseWriterBase {

    private final FacesContext facesContext;

    @Getter
    @NonNull
    private final T component;

    @Getter
    private final ComponentWrapper<T> componentWrapper;

    /**
     * @param facesContext must not be null
     * @param component must not be null
     */
    public DecoratingResponseWriter(final FacesContext facesContext, final T component) {
        super(requireNonNull(facesContext.getResponseWriter()));
        this.component = requireNonNull(component);
        this.componentWrapper = new ComponentWrapper<>(component);
        this.facesContext = facesContext;
    }

    /**
     * @param node
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    @Override
    public DecoratingResponseWriter<T> withStartElement(final Node node) throws IOException {
        super.withStartElement(node);
        return this;
    }

    /**
     * @param node
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    @Override
    public DecoratingResponseWriter<T> withEndElement(final Node node) throws IOException {
        super.withEndElement(node);
        return this;
    }

    /**
     * Write the given text either HTML-escaped or unescaped.
     *
     * @param text
     *            to be written. If it is null or empty, nothing will be written
     * @param escape
     *            Whether to HTML-escape the given text or not.
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     *             When on I/O error.
     */
    public DecoratingResponseWriter<T> withTextContent(final String text, final boolean escape)
        throws IOException {
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
     * Adds the "class" attribute to the current dom-element.
     *
     * @param styleClass
     *            to be set
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    @Override
    public DecoratingResponseWriter<T> withStyleClass(final String styleClass) throws IOException {
        super.withStyleClass(styleClass);
        return this;
    }

    /**
     * Adds the "class" attribute to the current dom-element.
     *
     * @param styleClass
     *            to be set
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    @Override
    public DecoratingResponseWriter<T> withStyleClass(final StyleClassProvider styleClass)
        throws IOException {
        super.withStyleClass(styleClass);
        return this;
    }

    /**
     * Adds the "class" attribute to the current dom-element.
     *
     * @param styleClass
     *            to be set
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    @Override
    public DecoratingResponseWriter<T> withStyleClass(final StyleClassBuilder styleClass)
        throws IOException {
        super.withStyleClass(styleClass);
        return this;
    }

    /**
     * Adds an attribute to the current dom-element.
     *
     * @param attributeName
     *            must not be null
     * @param attributeValue
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    @Override
    public DecoratingResponseWriter<T> withAttribute(final AttributeName attributeName,
            final String attributeValue)
        throws IOException {
        super.withAttribute(attributeName, attributeValue);
        return this;
    }

    /**
     * Adds an attribute to the current dom-element.
     *
     * @param attributeName
     *            must not be null
     * @param attributeValue
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    @Override
    public DecoratingResponseWriter<T> withAttribute(final AttributeName attributeName,
            final AttributeValue attributeValue)
        throws IOException {
        super.withAttribute(attributeName, attributeValue);
        return this;
    }

    /**
     * Adds the title attribute to the current dom-element.
     *
     * @param title
     *            if it is null or empty, not title will be written
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    @Override
    public DecoratingResponseWriter<T> withAttributeTitle(final String title) throws IOException {
        super.withAttributeTitle(title);
        return this;
    }

    /**
     * Adds the style attribute to the current dom-element.
     *
     * @param style
     *            if it is null or empty, no style-attribute will be written
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    @Override
    public DecoratingResponseWriter<T> withAttributeStyle(final String style) throws IOException {
        super.withAttributeStyle(style);
        return this;
    }

    /**
     * Writes the id AND the name attribute for a given element (represented by
     * the state of the given ResponseWriter)
     *
     * @param idExtension
     *            if not null it will be appended to the derived ClientId. In
     *            addition there will be an underscore appended: The result will
     *            be component.getClientId() + "_" + idExtension
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public DecoratingResponseWriter<T> withClientId(final String idExtension) throws IOException {
        final var idString = this.componentWrapper.getSuffixedClientId(idExtension);
        withAttribute(AttributeName.ID, idString);
        return withAttribute(AttributeName.NAME, idString);
    }

    /**
     * Writes the id AND the name attribute for a given element (represented by
     * the state of the given ResponseWriter)
     *
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public DecoratingResponseWriter<T> withClientId() throws IOException {
        withAttribute(AttributeName.ID, this.componentWrapper.getClientId());
        return withAttribute(AttributeName.NAME, this.componentWrapper.getClientId());
    }

    /**
     * Escape id for JavaScript selector.
     * Escapes the following chars with a single backslash: :.[],=@
     *
     * @see <a href="http://api.jquery.com/category/selectors/">jQuery Selectors</a>
     * @see <a href=
     *      "https://learn.jquery.com/using-jquery-core/faq/how-do-i-select-an-element-by-an-id-that-has-characters-used-in-css-notation/">jQuery
     *      FAQ</a>
     *
     * @param id
     * @return escaped id
     */
    public static String escapeJavaScriptIdentifier(final String id) {
        return id.replaceAll("(:|\\.|\\[|\\]|,|=|@)", "\\\\$1");
    }

    /**
     * Writes the id AND the name attribute for a given element (represented by
     * the state of the given ResponseWriter) if the client id is to be
     * rendered, see {@link ComponentWrapper#shouldRenderClientId()} on the
     * algorithm.
     *
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public DecoratingResponseWriter<T> withClientIdIfNecessary() throws IOException {
        if (this.componentWrapper.shouldRenderClientId()) {
            return withClientId();
        }
        return this;
    }

    /**
     * Writes the pass-through-attributes to the given element (represented by
     * the state of the given ResponseWriter). In case there are no
     * pass-through-attributes nothing will happen.
     *
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public DecoratingResponseWriter<T> withPassThroughAttributes() throws IOException {
        super.withPassThroughAttributes(facesContext, component.getPassThroughAttributes(false));
        return this;
    }

    /**
     * Write an input with type=hidden a generated id and the given value
     *
     * @param idExtension
     *            if not null it will be appended to the derived ClientId. In
     *            addition there will be an underscore appended: The result will
     *            be component.getClientId() + "_" + idExtension
     * @param value
     *            to be written if not null.
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public DecoratingResponseWriter<T> withHiddenField(final String idExtension,
            final Serializable value)
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
     * Retrieve a attribute for the renderer
     *
     * @param key the key
     * @return the value if present, otherwise null
     */
    public Object getRenderAttribute(final Serializable key) {
        return this.component.getTransientStateHelper().getTransient(key);
    }

    /**
     * Store a attribute for the renderer
     *
     * @param key the key
     * @param value the value
     */
    public void putRenderAttribute(final Serializable key, final Serializable value) {
        this.component.getTransientStateHelper().putTransient(key, value);
    }
}
