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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing HTML element nodes for use with {@link HtmlTreeBuilder}.
 * <p>
 * This enum provides a type-safe way to work with HTML elements when constructing HTML markup 
 * programmatically. Each constant represents a specific HTML tag with its corresponding string 
 * representation, making it easy to create consistent HTML structures without string literals.
 * </p>
 * <p>
 * Common HTML elements are represented as enum constants (e.g., DIV, SPAN, INPUT) with their 
 * corresponding tag names accessible via the {@code getContent()} method. This approach helps
 * prevent typos and provides better IDE support than using string literals directly.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * HtmlTreeBuilder builder = new HtmlTreeBuilder();
 * 
 * // Create a div with a nested span
 * builder.withNode(Node.DIV)
 *        .withAttributeNameAndValue(AttributeName.ID, "myDiv")
 *        .withAttributeNameAndValue(AttributeName.CLASS, "container")
 *        .withNode(Node.SPAN)
 *        .withTextContent("Hello World")
 *        .currentHierarchyUp()
 *        .currentHierarchyUp();
 *        
 * // Renders: &lt;div id="myDiv" class="container"&gt;&lt;span&gt;Hello World&lt;/span&gt;&lt;/div&gt;
 * String html = builder.getHtml();
 * </pre>
 * <p>
 * This enum is thread-safe and immutable, making it safe to use in concurrent environments.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see HtmlTreeBuilder
 * @see AttributeName
 * @see AttributeValue
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Node {

    /**
     * HTML &lt;a&gt; tag - Anchor/hyperlink element.
     * <p>
     * The anchor element creates a hyperlink to other web pages, files, locations within
     * the same page, email addresses, or any other URL.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_a.asp">HTML &lt;a&gt; Tag</a>
     */
    A("a"),

    /**
     * HTML &lt;button&gt; tag - Interactive button element.
     * <p>
     * The button element represents a clickable button, used to submit forms or trigger
     * client-side scripts when clicked.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_button.asp">HTML &lt;button&gt; Tag</a>
     */
    BUTTON("button"),

    /**
     * HTML &lt;code&gt; tag - Code fragment element.
     * <p>
     * The code element represents a fragment of computer code, typically displayed in a
     * monospace font.
     * </p>
     * 
     * @see <a href="https://www.w3schools.com/tags/tag_code.asp">HTML &lt;code&gt; Tag</a>
     */
    CODE("code"),

    /**
     * HTML &lt;div&gt; tag - Division/container element.
     * <p>
     * The div element represents a generic container or division of content with no
     * semantic meaning. It's often used for styling or as a container for other elements.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_div.asp">HTML &lt;div&gt; Tag</a>
     */
    DIV("div"),

    /**
     * HTML &lt;fieldset&gt; tag - Form field grouping element.
     * <p>
     * The fieldset element is used to group related elements in a form, drawing a box
     * around the related elements.
     * </p>
     * 
     * @see <a href="https://www.w3schools.com/tags/tag_fieldset.asp">HTML &lt;fieldset&gt; Tag</a>
     */
    FIELDSET("fieldset"),

    /**
     * HTML &lt;form&gt; tag - Form element for user input.
     * <p>
     * The form element represents a document section containing interactive controls for
     * submitting information to a server.
     * </p>
     * 
     * @see <a href="https://www.w3schools.com/tags/tag_form.asp">HTML &lt;form&gt; Tag</a>
     */
    FORM("form"),

    /**
     * HTML &lt;h1&gt; tag - Heading level 1 (highest level).
     * <p>
     * The h1 element represents the most important heading on the page, typically used
     * for page titles or major section headings.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_hn.asp">HTML &lt;h1&gt; to &lt;h6&gt; Tags</a>
     */
    H1("h1"),

    /**
     * HTML &lt;h2&gt; tag - Heading level 2.
     * <p>
     * The h2 element represents the second level heading, typically used for major
     * section headings within the document.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_hn.asp">HTML &lt;h1&gt; to &lt;h6&gt; Tags</a>
     */
    H2("h2"),

    /**
     * HTML &lt;h3&gt; tag - Heading level 3.
     * <p>
     * The h3 element represents the third level heading, typically used for subsection
     * headings within major sections.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_hn.asp">HTML &lt;h1&gt; to &lt;h6&gt; Tags</a>
     */
    H3("h3"),

    /**
     * HTML &lt;h4&gt; tag - Heading level 4.
     * <p>
     * The h4 element represents the fourth level heading, typically used for smaller
     * subsection headings.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_hn.asp">HTML &lt;h1&gt; to &lt;h6&gt; Tags</a>
     */
    H4("h4"),

    /**
     * HTML &lt;input&gt; tag - Form input control.
     * <p>
     * The input element is used to create interactive controls for web-based forms to
     * accept data from the user. The type attribute determines the specific input type.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_input.asp">HTML &lt;input&gt; Tag</a>
     */
    INPUT("input"),

    /**
     * HTML &lt;i&gt; tag - Italic text element.
     * <p>
     * The i element represents text that is set off from the normal prose, such as
     * idiomatic text, technical terms, taxonomic designations, or spoken thoughts.
     * Modern usage typically styles this text in italics.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_i.asp">HTML &lt;i&gt; Tag</a>
     */
    ITALIC("i"),

    /**
     * HTML &lt;label&gt; tag - Form control label element.
     * <p>
     * The label element represents a caption for an item in a user interface,
     * typically associated with form controls to provide accessible descriptions.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_label.asp">HTML &lt;label&gt; Tag</a>
     */
    LABEL("label"),

    /**
     * HTML &lt;legend&gt; tag - Caption for fieldset element.
     * <p>
     * The legend element represents a caption or title for the contents of its parent
     * fieldset element.
     * </p>
     * 
     * @see <a href="https://www.w3schools.com/tags/tag_legend.asp">HTML &lt;legend&gt; Tag</a>
     */
    LEGEND("legend"),

    /**
     * HTML &lt;li&gt; tag - List item element.
     * <p>
     * The li element represents an item in a list and must be contained in a parent
     * element: an ordered list (ol), an unordered list (ul), or a menu (menu).
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_li.asp">HTML &lt;li&gt; Tag</a>
     */
    LI("li"),

    /**
     * HTML &lt;nav&gt; tag - Navigation section element.
     * <p>
     * The nav element represents a section of a page whose purpose is to provide
     * navigation links, either within the current document or to other documents.
     * </p>
     *
     * @see <a href="https://www.w3schools.com/tags/tag_nav.asp">HTML &lt;nav&gt; Tag</a>
     */
    NAV("nav"),

    /**
     * HTML &lt;ol&gt; tag - Ordered list element.
     * <p>
     * The ol element represents an ordered list of items, typically displayed with
     * a numerical or alphabetical numbering system.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_ol.asp">HTML &lt;ol&gt; Tag</a>
     */
    OL("ol"),

    /**
     * HTML &lt;option&gt; tag - Select option element.
     * <p>
     * The option element represents an option in a select element or a datalist element,
     * defining an item that can be selected by the user.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_option.asp">HTML &lt;option&gt; Tag</a>
     */
    OPTION("option"),

    /**
     * HTML &lt;p&gt; tag - Paragraph element.
     * <p>
     * The p element represents a paragraph of content, typically used for blocks of text.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_p.asp">HTML &lt;p&gt; Tag</a>
     */
    P("p"),

    /**
     * HTML &lt;pre&gt; tag - Preformatted text element.
     * <p>
     * The pre element represents preformatted text which is to be presented exactly
     * as written in the HTML file, preserving whitespace and line breaks.
     * </p>
     * 
     * @see <a href="https://www.w3schools.com/tags/tag_pre.asp">HTML &lt;pre&gt; Tag</a>
     */
    PRE("pre"),

    /**
     * HTML &lt;script&gt; tag - Client-side script element.
     * <p>
     * The script element is used to embed or reference executable code, typically
     * JavaScript, within an HTML document.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_script.asp">HTML &lt;script&gt; Tag</a>
     */
    SCRIPT("script"),

    /**
     * HTML &lt;select&gt; tag - Selection dropdown element.
     * <p>
     * The select element represents a control that provides a menu of options for
     * the user to select from.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_select.asp">HTML &lt;select&gt; Tag</a>
     */
    SELECT("select"),

    /**
     * HTML &lt;span&gt; tag - Inline container element.
     * <p>
     * The span element is a generic inline container for phrasing content with no
     * inherent semantic meaning. It's often used for styling or grouping inline elements.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_span.asp">HTML &lt;span&gt; Tag</a>
     */
    SPAN("span"),

    /**
     * HTML &lt;textarea&gt; tag - Multiline text input element.
     * <p>
     * The textarea element represents a multi-line plain-text editing control,
     * useful for collecting longer text input from users.
     * </p>
     * 
     * @see <a href="https://www.w3schools.com/tags/tag_textarea.asp">HTML &lt;textarea&gt; Tag</a>
     */
    TEXT_AREA("textarea"),

    /**
     * HTML &lt;ul&gt; tag - Unordered list element.
     * <p>
     * The ul element represents an unordered list of items, typically displayed with
     * bullets or other markers rather than numbers.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_ul.asp">HTML &lt;ul&gt; Tag</a>
     */
    UL("ul");

    /**
     * The string representation of this HTML node.
     * <p>
     * This field contains the actual HTML tag name without angle brackets
     * (e.g., "div" for the DIV node).
     * </p>
     */
    @Getter
    private final String content;
}
