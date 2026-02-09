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
package de.cuioss.jsf.api.components.html;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.api.components.renderer.ConditionalResponseWriter;
import de.cuioss.tools.io.IOStreams;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.context.ResponseWriter;
import lombok.Getter;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import javax.xml.XMLConstants;

/**
 * A fluent builder for generating HTML markup programmatically.
 * <p>
 * This builder provides type-safe methods to create HTML structures with proper element nesting, 
 * attribute handling, and content management. It's particularly useful for JSF component renderers
 * that need to create complex HTML structures.
 * 
 * <p>
 * Key features:
 * <ul>
 *   <li>Fluent API for natural coding style</li>
 *   <li>Type-safe element and attribute creation using {@link Node}, {@link AttributeName},
 *       and {@link AttributeValue} enums</li>
 *   <li>Tree navigation with {@link #currentHierarchyUp()} for nested structures</li>
 *   <li>Special handling for CSS classes with {@link StyleClassBuilder} and {@link StyleClassProvider} support</li>
 *   <li>Integration with JSF's {@link ResponseWriter}</li>
 *   <li>Support for partial rendering with child breakpoints</li>
 * </ul>
 * 
 * <p>
 * The builder maintains a reference to the current element in the tree, allowing navigation and
 * manipulation of the tree structure. Methods like {@link #withNode(Node)} create and select new elements,
 * while {@link #currentHierarchyUp()} moves back up the tree.
 * 
 * <p>
 * Basic usage example:
 * <pre>
 * // Create a div containing a heading and a paragraph
 * HtmlTreeBuilder builder = new HtmlTreeBuilder();
 * builder.withNode(Node.DIV)
 *        .withAttributeNameAndValue(AttributeName.CLASS, "container")
 *        .withNode(Node.H2)
 *        .withTextContent("Hello World")
 *        .currentHierarchyUp()
 *        .withNode(Node.P)
 *        .withTextContent("This is a paragraph.");
 *        
 * // Renders: &lt;div class="container"&gt;&lt;h2&gt;Hello World&lt;/h2&gt;&lt;p&gt;This is a paragraph.&lt;/p&gt;&lt;/div&gt;
 * String html = builder.getHtml();
 * </pre>
 * 
 * <p>
 * For JSF component rendering:
 * <pre>
 * HtmlTreeBuilder builder = new HtmlTreeBuilder();
 * builder.withNode(Node.DIV)
 *        .withAttributeNameAndValue(AttributeName.ID, clientId)
 *        .withAttributeNameAndValue(AttributeName.CLASS, "panel");
 *        
 * // Add child components here
 * builder.withNodeChildBreakpoint(); // Mark where to render child components
 * 
 * // Finish structure
 * builder.withNode(Node.SPAN)
 *        .withAttributeNameAndValue(AttributeName.CLASS, "footer")
 *        .withTextContent("Panel Footer");
 *        
 * // Render parts before child components
 * builder.writeToResponseWriterUntilChildBreakpoint(writer);
 * 
 * // Child components are rendered here by JSF
 * 
 * // Render parts after child components
 * builder.writeToResponseWriterFromChildBreakpointOn(writer);
 * </pre>
 * 
 * <p>
 * This class is not thread-safe and instances should not be shared between threads.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see Node
 * @see AttributeName
 * @see AttributeValue
 */
public class HtmlTreeBuilder {

    /**
     * Name of the root element in the internal document.
     */
    public static final String ROOT = "root";

    /**
     * The identifier for the child breakpoint element used for partial rendering.
     * <p>
     * This special element marks where child content should be inserted during rendering.
     * </p>
     * 
     * @see #withNodeChildBreakpoint()
     * @see #writeToResponseWriterUntilChildBreakpoint(ResponseWriter)
     * @see #writeToResponseWriterFromChildBreakpointOn(ResponseWriter)
     */
    public static final String CHILD_BREAKPOINT_ELEMENT = "CHILD_BREAKPOINT";

    /**
     * Template string for wrapping HTML content with a root element.
     */
    private static final String ROOT_TEMPLATE = "<root>%s</root>";

    /**
     * The internal JDOM document that holds the entire HTML tree structure.
     */
    @Getter
    private final Document document;

    /**
     * The currently selected/focused element within the tree.
     * <p>
     * Operations like adding attributes or child nodes affect this element.
     * </p>
     */
    @Getter
    private Element current;

    /**
     * Default constructor that creates an empty HTML tree structure.
     * <p>
     * Creates a new document with a root element named {@value #ROOT} as the current element.
     * The root element serves as a container for the actual HTML elements but is not rendered
     * in the final output.
     * </p>
     */
    public HtmlTreeBuilder() {
        current = new Element(ROOT);
        document = new Document(current);
    }

    /**
     * Constructor that creates an HTML tree from an existing HTML string.
     * <p>
     * The provided HTML string is parsed into a document structure.
     * If the input is null or empty, an empty tree is created (same as the default constructor).
     * </p>
     *
     * @param htmlString The HTML content to parse, may be null or empty
     * @throws IllegalArgumentException If the HTML cannot be parsed
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
     * Adds a new element with the specified name to the current element and makes it the new current element.
     * <p>
     * This method creates a new element, adds it as a child to the current element,
     * and then sets the new element as the current focus for subsequent operations.
     * </p>
     *
     * @param nodeName The name of the element to create
     * @return This builder instance for method chaining
     */
    public HtmlTreeBuilder withNode(final String nodeName) {
        final var newElement = new Element(nodeName);
        current.getChildren().add(newElement);
        current = newElement;
        return this;
    }

    /**
     * Adds a new element using a predefined {@link Node} type and makes it the new current element.
     * <p>
     * This type-safe method creates a new element based on the provided Node enum,
     * adds it as a child to the current element, and then sets the new element as the
     * current focus for subsequent operations.
     * </p>
     *
     * @param node The {@link Node} type identifying the HTML element to create
     * @return This builder instance for method chaining
     */
    public HtmlTreeBuilder withNode(final Node node) {
        return withNode(node.getContent());
    }

    /**
     * Navigates up one level in the element hierarchy.
     * <p>
     * This method changes the current element focus to its parent element,
     * allowing you to continue adding siblings after completing a child subtree.
     * </p>
     * <p>
     * Example usage:
     * </p>
     * <pre>
     * builder.withNode(Node.DIV)       // Create and focus on DIV
     *        .withNode(Node.SPAN)      // Create and focus on SPAN (child of DIV)
     *        .withTextContent("Text")  // Add text to SPAN
     *        .currentHierarchyUp()     // Move focus back to DIV
     *        .withNode(Node.P);        // Create P as another child of DIV
     * </pre>
     *
     * @return This builder instance for method chaining
     */
    public HtmlTreeBuilder currentHierarchyUp() {
        current = current.getParentElement();
        return this;
    }

    /**
     * Adds text content to the current element.
     * <p>
     * This method adds the specified text as direct content within the current element.
     * The text is not parsed as HTML.
     * </p>
     *
     * @param content The text content to add, may be null or empty
     * @return This builder instance for method chaining
     */
    public HtmlTreeBuilder withTextContent(final String content) {
        current.addContent(content);
        return this;
    }

    /**
     * Adds an attribute with the specified name and value to the current element.
     * <p>
     * This method creates an attribute with the given name and value and adds it
     * to the current element.
     * </p>
     *
     * @param attributeName The name of the attribute to add
     * @param attributeValue The value of the attribute, may be null
     * @return This builder instance for method chaining
     */
    public HtmlTreeBuilder withAttribute(final String attributeName, final String attributeValue) {
        current.getAttributes().add(new Attribute(attributeName, attributeValue));
        return this;
    }

    /**
     * Adds a CSS class attribute to the current element.
     * <p>
     * This is a convenience method for setting the "class" attribute.
     * </p>
     *
     * @param styleClass The CSS class name(s) to set, may be null or empty
     * @return This builder instance for method chaining
     */
    public HtmlTreeBuilder withStyleClass(final String styleClass) {
        return withAttribute(AttributeName.CLASS, styleClass);
    }

    /**
     * Adds a CSS class attribute to the current element using a {@link StyleClassProvider}.
     * <p>
     * This convenience method obtains the CSS class string from the provider
     * and sets it as the "class" attribute.
     * </p>
     *
     * @param styleClass The {@link StyleClassProvider} supplying the CSS class name(s)
     * @return This builder instance for method chaining
     */
    public HtmlTreeBuilder withStyleClass(final StyleClassProvider styleClass) {
        return withStyleClass(styleClass.getStyleClass());
    }

    /**
     * Adds a CSS class attribute to the current element using a {@link StyleClassBuilder}.
     * <p>
     * This convenience method obtains the CSS class string from the builder
     * and sets it as the "class" attribute.
     * </p>
     *
     * @param styleClass The {@link StyleClassBuilder} supplying the CSS class name(s)
     * @return This builder instance for method chaining
     */
    public HtmlTreeBuilder withStyleClass(final StyleClassBuilder styleClass) {
        return withStyleClass(styleClass.getStyleClass());
    }

    /**
     * Adds an attribute to the current element using a predefined {@link AttributeName}.
     * <p>
     * This type-safe method creates an attribute using the specified AttributeName enum
     * and the provided value, then adds it to the current element.
     * </p>
     *
     * @param attributeName The {@link AttributeName} identifying the attribute, must not be null
     * @param attributeValue The value of the attribute, may be null
     * @return This builder instance for method chaining
     */
    public HtmlTreeBuilder withAttribute(final AttributeName attributeName, final String attributeValue) {
        return withAttribute(attributeName.getContent(), attributeValue);
    }

    /**
     * Adds both 'name' and 'id' attributes with the same value to the current element.
     * <p>
     * This convenience method sets both the 'id' and 'name' attributes to the same value,
     * which is a common pattern in HTML forms.
     * </p>
     *
     * @param attributeValue The value to set for both name and id attributes
     * @return This builder instance for method chaining
     */
    public HtmlTreeBuilder withAttributeNameAndId(final String attributeValue) {
        return withAttribute(AttributeName.ID, attributeValue).withAttribute(AttributeName.NAME, attributeValue);
    }

    /**
     * Adds an attribute to the current element using predefined {@link AttributeName} and {@link AttributeValue}.
     * <p>
     * This fully type-safe method creates an attribute using the specified AttributeName
     * and AttributeValue enums, then adds it to the current element.
     * </p>
     *
     * @param attributeName The {@link AttributeName} identifying the attribute, must not be null
     * @param attributeValue The {@link AttributeValue} providing the attribute value, must not be null
     * @return This builder instance for method chaining
     */
    public HtmlTreeBuilder withAttribute(final AttributeName attributeName, final AttributeValue attributeValue) {
        return withAttribute(attributeName.getContent(), attributeValue.getContent());
    }

    /**
     * Adds a special child breakpoint element to the current position in the tree.
     * <p>
     * This method inserts a marker element named {@value #CHILD_BREAKPOINT_ELEMENT} that can be
     * used with {@link #writeToResponseWriterUntilChildBreakpoint(ResponseWriter)} and
     * {@link #writeToResponseWriterFromChildBreakpointOn(ResponseWriter)} for partial rendering.
     * </p>
     * <p>
     * This is particularly useful in JSF component renderers where you want to render
     * content before and after child components.
     * </p>
     *
     * @return This builder instance for method chaining
     */
    public HtmlTreeBuilder withNodeChildBreakpoint() {
        return withNode(CHILD_BREAKPOINT_ELEMENT);
    }

    /**
     * Writes the complete HTML tree to a JSF ResponseWriter.
     * <p>
     * This method traverses the entire tree structure (excluding the root element)
     * and writes it to the provided ResponseWriter, handling all start/end tags
     * and attributes appropriately.
     * </p>
     *
     * @param writer The JSF ResponseWriter to write to, must not be null
     * @throws IOException If an I/O error occurs during writing
     */
    public void writeToResponseWriter(final ResponseWriter writer) throws IOException {
        for (final Element element : document.getRootElement().getChildren()) {
            writeElementToResponseWriter(element, writer);
        }
    }

    /**
     * Recursively writes an element and its children to a ResponseWriter.
     * <p>
     * This private helper method handles the details of writing elements, attributes,
     * and recursing into child elements.
     * </p>
     *
     * @param element The element to write
     * @param writer The ResponseWriter to write to
     * @throws IOException If an I/O error occurs during writing
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
     * Writes the portion of the HTML tree that appears before the child breakpoint marker.
     * <p>
     * This method writes only the parts of the tree that come before the
     * {@value #CHILD_BREAKPOINT_ELEMENT} marker, stopping when it encounters the marker.
     * </p>
     * <p>
     * Used primarily in JSF component renderers to write the component's opening structure
     * before children are rendered.
     * </p>
     *
     * @param writer The ResponseWriter to write to, must not be null
     * @throws IOException If an I/O error occurs during writing
     * @see #withNodeChildBreakpoint()
     * @see #writeToResponseWriterFromChildBreakpointOn(ResponseWriter)
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
     * Writes the portion of the HTML tree that appears after the child breakpoint marker.
     * <p>
     * This method writes only the parts of the tree that come after the
     * {@value #CHILD_BREAKPOINT_ELEMENT} marker, omitting the marker itself.
     * </p>
     * <p>
     * Used primarily in JSF component renderers to write the component's closing structure
     * after children are rendered.
     * </p>
     *
     * @param writer The ResponseWriter to write to, must not be null
     * @throws IOException If an I/O error occurs during writing
     * @see #withNodeChildBreakpoint()
     * @see #writeToResponseWriterUntilChildBreakpoint(ResponseWriter)
     */
    public void writeToResponseWriterFromChildBreakpointOn(final ResponseWriter writer) throws IOException {
        writeToResponseWriter(new ConditionalResponseWriter(writer, CHILD_BREAKPOINT_ELEMENT, false));
    }

    /**
     * Recursively writes elements until the child breakpoint marker is encountered.
     * <p>
     * This private helper method handles writing the tree up to the breakpoint.
     * </p>
     *
     * @param writer The ResponseWriter to write to
     * @throws IOException If an I/O error occurs during writing
     */
    private void writeElementsUntilChildBreakpoint(final ResponseWriter writer) throws IOException {
        writeToResponseWriter(new ConditionalResponseWriter(writer, CHILD_BREAKPOINT_ELEMENT, true));
    }

    /**
     * Returns the HTML string representation of the tree.
     * <p>
     * This method converts the entire tree (excluding the root element) to an HTML string.
     * </p>
     *
     * @return The HTML string representation of the tree structure
     */
    public String toHtmlString() {
        return new XMLOutputter().outputString(document.getRootElement().getChildren());
    }

    @Override
    public String toString() {
        return toHtmlString();
    }

}
