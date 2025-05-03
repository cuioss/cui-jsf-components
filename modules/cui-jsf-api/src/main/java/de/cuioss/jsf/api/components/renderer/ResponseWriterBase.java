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
 * Base class for providing convenience methods on {@link ResponseWriter}
 * without {@link UIComponent} context, compared to
 * {@link DecoratingResponseWriter}. It defines a fluent api.
 */
@RequiredArgsConstructor
public class ResponseWriterBase extends ResponseWriterWrapper {

    @Getter
    @NonNull
    private final ResponseWriter wrapped;

    /**
     * @param node identifying the html-Element
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException from the underlying {@link jakarta.faces.context.ResponseWriter}
     */
    public ResponseWriterBase withStartElement(final Node node) throws IOException {
        startElement(node.getContent(), null);
        return this;
    }

    /**
     * @param node identifying the html-Element
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException from the underlying {@link jakarta.faces.context.ResponseWriter}
     */
    public ResponseWriterBase withEndElement(final Node node) throws IOException {
        endElement(node.getContent());
        return this;
    }

    /**
     * Adds the "class" attribute to the current dom-element.
     *
     * @param styleClass to be set
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException from the underlying {@link jakarta.faces.context.ResponseWriter}
     */
    public ResponseWriterBase withStyleClass(final String styleClass) throws IOException {
        if (styleClass != null && !styleClass.isEmpty()) {
            writeAttribute(AttributeName.CLASS.getContent(), styleClass, AttributeName.CLASS.getContent());
        }
        return this;
    }

    /**
     * Adds the "class" attribute to the current dom-element.
     *
     * @param styleClass to be set
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException from the underlying {@link jakarta.faces.context.ResponseWriter}
     */
    public ResponseWriterBase withStyleClass(final StyleClassProvider styleClass) throws IOException {
        return withStyleClass(styleClass.getStyleClass());
    }

    /**
     * Adds the "class" attribute to the current dom-element.
     *
     * @param styleClass to be set
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException from the underlying {@link jakarta.faces.context.ResponseWriter}
     */
    public ResponseWriterBase withStyleClass(final StyleClassBuilder styleClass) throws IOException {
        return withStyleClass(styleClass.getStyleClass());
    }

    /**
     * Adds an attribute to the current dom-element.
     *
     * @param attributeName  must not be null
     * @param attributeValue to be written
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException from the underlying {@link jakarta.faces.context.ResponseWriter}
     */
    public ResponseWriterBase withAttribute(final AttributeName attributeName, final String attributeValue)
            throws IOException {
        writeAttribute(attributeName.getContent(), attributeValue, attributeName.getContent());
        return this;
    }

    /**
     * Adds an attribute to the current dom-element.
     *
     * @param attributeName  must not be null
     * @param attributeValue to be written
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException from the underlying {@link jakarta.faces.context.ResponseWriter}
     */
    public ResponseWriterBase withAttribute(final AttributeName attributeName, final AttributeValue attributeValue)
            throws IOException {
        return withAttribute(attributeName, attributeValue.getContent());
    }

    /**
     * Adds the title attribute to the current dom-element.
     *
     * @param title if it is null or empty, not title will be written
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException from the underlying {@link jakarta.faces.context.ResponseWriter}
     */
    public ResponseWriterBase withAttributeTitle(final String title) throws IOException {
        if (!MoreStrings.isEmpty(title)) {
            return withAttribute(AttributeName.TITLE, title);
        }
        return this;
    }

    /**
     * Adds the title attribute to the current dom-element.
     *
     * @param title if it is null or empty, not title will be written
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException from the underlying {@link jakarta.faces.context.ResponseWriter}
     */
    public ResponseWriterBase withAttributeTitle(final TitleProvider title) throws IOException {
        return withAttributeTitle(title.resolveTitle());
    }

    /**
     * Adds the styleProvider attribute to the current dom-element.
     *
     * @param style if it is null or empty, not styleProvider-attribute will be written
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException from the underlying {@link jakarta.faces.context.ResponseWriter}
     */
    public ResponseWriterBase withAttributeStyle(final String style) throws IOException {
        if (!MoreStrings.isEmpty(style)) {
            return withAttribute(AttributeName.STYLE, style);
        }
        return this;
    }

    /**
     * Adds the styleProvider attribute to the current dom-element.
     *
     * @param styleProvider if it is null or empty, not styleProvider-attribute will be written
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException from the underlying {@link jakarta.faces.context.ResponseWriter}
     */
    public ResponseWriterBase withAttributeStyle(final StyleAttributeProvider styleProvider) throws IOException {
        return withAttributeStyle(styleProvider.getStyle());
    }

    /**
     * Writes the given pass-through-attributes. In case there are no
     * pass-through-attributes, nothing will happen.
     *
     * @param facesContext must not be null
     * @param passThroughAttributes the attributes to render
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException from the underlying {@link jakarta.faces.context.ResponseWriter}
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

    private static Object resolveValue(final FacesContext facesContext, final Object value) {
        if (value instanceof ValueExpression expression) {
            return expression.getValue(facesContext.getELContext());
        }
        return value;
    }

}
