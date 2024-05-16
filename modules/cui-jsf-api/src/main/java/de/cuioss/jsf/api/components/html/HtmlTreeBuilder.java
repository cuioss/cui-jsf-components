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
package de.cuioss.jsf.api.components.html;

import java.io.IOException;

import jakarta.faces.context.ResponseWriter;
import javax.xml.XMLConstants;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.api.components.renderer.ConditionalResponseWriter;
import de.cuioss.tools.io.IOStreams;
import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;

/**
 * Represents a partial html tree. It provides a number of fluent builder in
 * order to simplify the creation. methods in order to interact with
 *
 * @author Oliver Wolff
 */
public class HtmlTreeBuilder {

    /** "root" */
    public static final String ROOT = "root";

    /**
     * The identifier for the child breakpoint, see
     * {@link #withNodeChildBreakpoint()}
     */
    public static final String CHILD_BREAKPOINT_ELEMENT = "CHILD_BREAKPOINT";

    /** */
    private static final String ROOT_TEMPLATE = "<root>%s</root>";

    @Getter
    private final Document document;

    /** The currently selected element / Node */
    @Getter
    private Element current;

    /**
     * Default Constructor
     * <p>
     * Creates an {@link Document} and an empty {@link Element} with the name
     * {@value #ROOT} as root-element that is the current element.
     * </p>
     */
    public HtmlTreeBuilder() {
        current = new Element(ROOT);
        document = new Document(current);
    }

    /**
     * Constructor for creating an instance of a given htmlString
     *
     * @param htmlString used for creating the html-tree If it is null or empty it
     *                   creates an {@link Document} and an empty {@link Element}
     *                   with the name {@value #ROOT} as root-element that is the
     *                   current element.
     */
    public HtmlTreeBuilder(final String htmlString) {

        if (MoreStrings.isEmpty(htmlString)) {
            current = new Element(ROOT);
            document = new Document(current);
        } else {
            final var wrappedInput = ROOT_TEMPLATE.formatted(htmlString);
            try (var input = IOStreams.toInputStream(wrappedInput)) {
                final var saxBuilder = new SAXBuilder();
                saxBuilder.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
                saxBuilder.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
                document = saxBuilder.build(input);
                current = document.getRootElement();
            } catch (JDOMException | IOException e) {
                throw new IllegalArgumentException("Unable to parse given String, due to ", e);
            }
        }
    }

    /**
     * Adds a node with the given name to the current tree.
     *
     * @param nodeName
     * @return the {@link HtmlTreeBuilder}
     */
    public HtmlTreeBuilder withNode(final String nodeName) {
        final var newElement = new Element(nodeName);
        current.getChildren().add(newElement);
        current = newElement;
        return this;
    }

    /**
     * Adds a node with the given {@link Node} to the current tree.
     *
     * @param node identifying the concrete Node
     * @return the {@link HtmlTreeBuilder}
     */
    public HtmlTreeBuilder withNode(final Node node) {
        return withNode(node.getContent());
    }

    /**
     * While {@link #withNode(String)} sets the newly created node as current this
     * method provides a way to go up in the tree for adding additional child
     * elements.
     *
     * @return the {@link HtmlTreeBuilder}
     */
    public HtmlTreeBuilder currentHierarchyUp() {
        current = current.getParentElement();
        return this;
    }

    /**
     * Adds the given string as direct content to the current dom-element.
     *
     * @param content
     * @return the {@link HtmlTreeBuilder}
     */
    public HtmlTreeBuilder withTextContent(final String content) {
        current.addContent(content);
        return this;
    }

    /**
     * Adds an attribute to the current dom-element.
     *
     * @param attributeName
     * @param attributeValue
     * @return the {@link HtmlTreeBuilder}
     */
    public HtmlTreeBuilder withAttribute(final String attributeName, final String attributeValue) {
        current.getAttributes().add(new Attribute(attributeName, attributeValue));
        return this;
    }

    /**
     * Adds the "class" attribute to the current dom-element.
     *
     * @param styleClass to be set
     * @return the {@link HtmlTreeBuilder}
     */
    public HtmlTreeBuilder withStyleClass(final String styleClass) {
        return withAttribute(AttributeName.CLASS, styleClass);
    }

    /**
     * Adds the "class" attribute to the current dom-element.
     *
     * @param styleClass to be set
     * @return the {@link HtmlTreeBuilder}
     */
    public HtmlTreeBuilder withStyleClass(final StyleClassProvider styleClass) {
        return withStyleClass(styleClass.getStyleClass());
    }

    /**
     * Adds the "class" attribute to the current dom-element.
     *
     * @param styleClass to be set
     * @return the {@link HtmlTreeBuilder}
     */
    public HtmlTreeBuilder withStyleClass(final StyleClassBuilder styleClass) {
        return withStyleClass(styleClass.getStyleClass());
    }

    /**
     * Adds an attribute to the current dom-element.
     *
     * @param attributeName  must not be null
     * @param attributeValue
     * @return the {@link HtmlTreeBuilder}
     */
    public HtmlTreeBuilder withAttribute(final AttributeName attributeName, final String attributeValue) {
        return withAttribute(attributeName.getContent(), attributeValue);
    }

    /**
     * Adds the 'name' and 'id' attributes attribute to the current dom-element.
     *
     * @param attributeValue to be set as 'name' and 'id'
     * @return the {@link HtmlTreeBuilder}
     */
    public HtmlTreeBuilder withAttributeNameAndId(final String attributeValue) {
        return withAttribute(AttributeName.ID, attributeValue).withAttribute(AttributeName.NAME, attributeValue);
    }

    /**
     * Adds an attribute to the current dom-element.
     *
     * @param attributeName  must not be null
     * @param attributeValue
     * @return the {@link HtmlTreeBuilder}
     */
    public HtmlTreeBuilder withAttribute(final AttributeName attributeName, final AttributeValue attributeValue) {
        return withAttribute(attributeName.getContent(), attributeValue.getContent());
    }

    /**
     * Adds a {@value #CHILD_BREAKPOINT_ELEMENT} node to the current tree and sets
     * it as current. This special marking node is for partial tree rendering within
     * JSF-Renderer.
     *
     * @return the {@link HtmlTreeBuilder}
     */
    public HtmlTreeBuilder withNodeChildBreakpoint() {
        return withNode(CHILD_BREAKPOINT_ELEMENT);
    }

    /**
     * Writes the complete tree to the response writer. This includes closing tags
     * as well
     *
     * @param writer
     * @throws IOException
     */
    public void writeToResponseWriter(final ResponseWriter writer) throws IOException {
        for (final Element element : document.getRootElement().getChildren()) {
            writeElementToResponseWriter(element, writer);
        }
    }

    /**
     * Writes recursively the elements given.
     *
     * @param element to be written
     * @param writer  to be written to, must not be null
     * @throws IOException usually thrown by given {@link ResponseWriter}
     */
    private void writeElementToResponseWriter(final Element element, final ResponseWriter writer) throws IOException {
        writer.startElement(element.getName(), null);
        for (final Attribute attribute : element.getAttributes()) {
            writer.writeAttribute(attribute.getName(), attribute.getValue(), null);
        }
        for (final Element child : element.getChildren()) {
            writeElementToResponseWriter(child, writer);
        }
        writer.endElement(element.getName());
    }

    /**
     * <p>
     * Writes that part of the tree to the given {@link ResponseWriter} that is
     * <em>before</em> {@link #CHILD_BREAKPOINT_ELEMENT}.
     * </p>
     *
     * @param writer to be used
     * @throws IOException
     */
    public void writeToResponseWriterUntilChildBreakpoint(final ResponseWriter writer) throws IOException {
        for (final Element element : document.getRootElement().getChildren()) {
            if (CHILD_BREAKPOINT_ELEMENT.equals(element.getName())) {
                return;
            }
            writeElementsUntilChildBreakpoint(writer);
        }
    }

    /**
     * <p>
     * Writes that part of the tree to the given {@link ResponseWriter} that is
     * <em>after</em> {@link #CHILD_BREAKPOINT_ELEMENT}.
     * </p>
     *
     * @param writer to be used
     * @throws IOException
     */
    public void writeToResponseWriterFromChildBreakpointOn(final ResponseWriter writer) throws IOException {
        writeToResponseWriter(new ConditionalResponseWriter(writer, CHILD_BREAKPOINT_ELEMENT, false));
    }

    /**
     * Writes recursively the elements given until
     * {@value #CHILD_BREAKPOINT_ELEMENT} is reached.
     *
     * @param writer to be written to, must not be null
     * @throws IOException usually thrown by given {@link ResponseWriter}
     */
    private void writeElementsUntilChildBreakpoint(final ResponseWriter writer) throws IOException {
        writeToResponseWriter(new ConditionalResponseWriter(writer, CHILD_BREAKPOINT_ELEMENT, true));
    }

    /**
     * @return a String representation of the current dom tree.
     */
    public String toHtmlString() {
        return new XMLOutputter().outputString(document.getRootElement().getChildren());
    }

    @Override
    public String toString() {
        return toHtmlString();
    }

}
