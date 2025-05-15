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
 * Enum representing HTML attribute names for use with {@link HtmlTreeBuilder}.
 * <p>
 * This enum provides a type-safe way to work with HTML attribute names when constructing 
 * HTML markup programmatically. Each constant represents a specific HTML attribute with its 
 * corresponding string representation, making it easy to create valid HTML attributes without 
 * string literals.
 * 
 * <p>
 * Common HTML attributes are represented as enum constants (e.g., ID, CLASS, STYLE) with 
 * their corresponding attribute names accessible via the {@code getContent()} method. 
 * This approach helps prevent typos and provides better IDE support than using string 
 * literals directly.
 * 
 * <p>
 * The enum contains three main categories of attributes:
 * <ul>
 *   <li>Standard HTML attributes (id, class, style, etc.)</li>
 *   <li>ARIA accessibility attributes (aria-label, aria-hidden, etc.)</li>
 *   <li>Data attributes for custom functionality (data-toggle, data-target, etc.)</li>
 * </ul>
 * 
 * <p>
 * Usage example:
 * <pre>
 * HtmlTreeBuilder builder = new HtmlTreeBuilder();
 * 
 * // Create a div with id and class attributes
 * builder.withNode(Node.DIV)
 *        .withAttributeNameAndValue(AttributeName.ID, "myDiv")
 *        .withAttributeNameAndValue(AttributeName.CLASS, "container highlight")
 *        .withAttributeNameAndValue(AttributeName.ARIA_LABEL, "Content section")
 *        .withTextContent("Hello World");
 *        
 * // Renders: &lt;div id="myDiv" class="container highlight" aria-label="Content section"&gt;Hello World&lt;/div&gt;
 * String html = builder.getHtml();
 * </pre>
 * 
 * <p>
 * This enum is thread-safe and immutable, making it safe to use in concurrent environments.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see HtmlTreeBuilder
 * @see Node
 * @see AttributeValue
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AttributeName {

    /**
     * HTML "aria-controls" attribute.
     * <p>
     * Identifies the element (or elements) whose contents or presence are controlled by
     * the current element. Used for establishing relationships between controls and 
     * the elements they control.
     * </p>
     */
    ARIA_CONTROLS("aria-controls"),

    /**
     * HTML "aria-expanded" attribute.
     * <p>
     * Indicates whether the element, or another grouping element it controls, 
     * is currently expanded or collapsed. Typically used with dropdown menus, 
     * accordions, and other expandable UI components.
     * </p>
     */
    ARIA_EXPANDED("aria-expanded"),

    /**
     * HTML "aria-hidden" attribute.
     * <p>
     * Indicates whether the element is exposed to an accessibility API. 
     * Elements with aria-hidden="true" are effectively invisible to screen readers.
     * </p>
     */
    ARIA_HIDDEN("aria-hidden"),

    /**
     * HTML "aria-label" attribute.
     * <p>
     * Defines a string value that labels the current element. Used in cases where 
     * a text label is not visible on screen. Provides an accessible name for screen readers.
     * </p>
     */
    ARIA_LABEL("aria-label"),

    /**
     * HTML "aria-labelledby" attribute.
     * <p>
     * Identifies the element (or elements) that labels the current element.
     * References the ID of another element that serves as a label.
     * </p>
     */
    ARIA_LABELLEDBY("aria-labelledby"),

    /**
     * HTML "aria-live" attribute.
     * <p>
     * Indicates that an element will be updated, and describes the types of updates
     * that user agents should expect. This helps screen readers announce dynamic content changes.
     * </p>
     */
    ARIA_LIVE("aria-live"),

    /**
     * HTML "aria-multiselectable" attribute.
     * <p>
     * Indicates that the user may select more than one item from the current selectable descendants.
     * Used with components like multi-select listboxes or tree controls.
     * </p>
     */
    ARIA_MULTISELECTABLE("aria-multiselectable"),

    /**
     * HTML "aria-selected" attribute.
     * <p>
     * Indicates the current selection state of an element. Works in conjunction with 
     * aria-multiselectable to indicate selected items in a multi-select component.
     * </p>
     */
    ARIA_SELECTED("aria-selected"),

    /**
     * HTML "checked" attribute.
     * <p>
     * Indicates whether a checkbox or radio button is checked/selected.
     * </p>
     */
    CHECKED("checked"),

    /**
     * HTML "class" attribute.
     * <p>
     * Specifies one or more CSS class names for an element. Used to apply styling or
     * to identify elements for JavaScript operations.
     * </p>
     * 
     * @see <a href="http://www.w3schools.com/html/html_classes.asp">HTML Classes</a>
     * @see <a href="http://www.w3schools.com/cssref/sel_class.asp">Definition and Usage</a>
     */
    CLASS("class"),

    /**
     * HTML "data-asyncupdate" custom attribute.
     * <p>
     * Custom data attribute used for components that need asynchronous updates.
     * </p>
     */
    DATA_ASYNCUPDATE("data-asyncupdate"),

    /**
     * HTML "data-deferred" custom attribute.
     * <p>
     * Custom data attribute used for components with deferred loading behavior.
     * </p>
     */
    DATA_DEFERRED("data-deferred"),

    /**
     * HTML "data-content-loaded" custom attribute.
     * <p>
     * Custom data attribute indicating whether content has been loaded.
     * </p>
     */
    DATA_CONTENT_LOADED("data-content-loaded"),

    /**
     * HTML "data-backdrop" custom attribute.
     * <p>
     * Special attribute for modal dialogs, controlling the backdrop behavior.
     * </p>
     */
    DATA_BACKDROP("data-backdrop"),

    /**
     * HTML "data-not-collapsed" custom attribute.
     * <p>
     * Custom data attribute indicating an element that should not be collapsed.
     * </p>
     */
    DATA_NOT_COLLAPSED("data-not-collapsed"),

    /**
     * HTML "data-dismiss" custom attribute.
     * <p>
     * Custom data attribute used to dismiss/close components like alerts or modals.
     * </p>
     */
    DATA_DISMISS("data-dismiss"),

    /**
     * HTML "data-dismiss-listener" custom attribute.
     * <p>
     * Custom data attribute for attaching dismissal event listeners.
     * </p>
     */
    DATA_DISMISS_LISTENER("data-dismiss-listener"),

    /**
     * HTML "data-item-active" custom attribute.
     * <p>
     * Custom data attribute indicating an active item in a collection.
     * </p>
     */
    DATA_ITEM_ACTIVE("data-item-active"),

    /**
     * HTML "data-modal-dialog-id" custom attribute.
     * <p>
     * Custom data attribute for selecting/accessing a dialog with JavaScript.
     * </p>
     */
    DATA_MODAL_ID("data-modal-dialog-id"),

    /**
     * HTML "data-parent" custom attribute.
     * <p>
     * Custom data attribute indicating the parent element, often used with
     * collapsible components to specify the parent container.
     * </p>
     */
    DATA_PARENT("data-parent"),

    /**
     * HTML "data-target" custom attribute.
     * <p>
     * Custom data attribute specifying the target element to be controlled
     * or affected by the current element.
     * </p>
     */
    DATA_TARGET("data-target"),

    /**
     * HTML "data-toggle" custom attribute.
     * <p>
     * Custom data attribute indicating the type of toggling behavior,
     * such as dropdown, collapse, or modal.
     * </p>
     */
    DATA_TOGGLE("data-toggle"),

    /**
     * HTML "disabled" attribute.
     * <p>
     * Specifies that an input element should be disabled. Disabled elements
     * are unusable and unclickable.
     * </p>
     */
    DISABLED("disabled"),
    
    /**
     * HTML "for" attribute.
     * <p>
     * Specifies which form element a label is bound to. The value must be the id of a form element.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/att_label_for.asp">HTML &lt;for&gt;</a>
     */
    FOR("for"),

    /**
     * HTML "href" attribute.
     * <p>
     * Specifies the URL of the page the link goes to. Used with anchor elements.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_href.asp">Definition and Usage</a>
     */
    HREF("href"),

    /**
     * HTML "id" attribute.
     * <p>
     * Specifies a unique ID for an HTML element. Used for CSS styling, JavaScript DOM manipulation,
     * and establishing relationships between elements (like label-for).
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/att_global_id.asp">HTML id Attribute</a>
     */
    ID("id"),

    /**
     * HTML "onclick" attribute.
     * <p>
     * JavaScript event handler that executes when an element is clicked.
     * </p>
     */
    JS_ON_CLICK("onclick"),

    /**
     * HTML "name" attribute.
     * <p>
     * Specifies a name for an element, particularly useful for form elements
     * when submitting form data.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/att_a_name.asp">HTML name Attribute</a>
     */
    NAME("name"),

    /**
     * HTML "role" attribute.
     * <p>
     * Specifies the role of an element in terms of accessibility.
     * Helps assistive technologies understand the purpose of elements.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_role.asp">HTML &lt;role&gt;</a>
     */
    ROLE("role"),

    /**
     * HTML "style" attribute.
     * <p>
     * Specifies an inline style for an element. Contains CSS property-value pairs.
     * </p>
     *
     * @see <a href="//www.w3schools.com/tags/att_global_style.asp">Definition and Usage</a>
     */
    STYLE("style"),

    /**
     * HTML "tabindex" attribute.
     * <p>
     * Specifies the tabbing order of an element. Controls which elements
     * can receive focus when navigating with the Tab key.
     * </p>
     */
    TABINDEX("tabindex"),

    /**
     * HTML "title" attribute.
     * <p>
     * Specifies extra information about an element, typically displayed as a tooltip
     * when hovering over the element.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/tag_title.asp">HTML &lt;title&gt;</a>
     */
    TITLE("title"),

    /**
     * HTML "type" attribute.
     * <p>
     * Specifies the type of an element, such as the type of input field
     * or the type of button.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/att_input_type.asp">Definition and Usage</a>
     */
    TYPE("type"),

    /**
     * HTML "value" attribute.
     * <p>
     * Specifies the value of an input element or the initial value
     * of a parameter passed to an applet, object, or input field.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/att_input_value.asp">HTML &lt;input&gt; value Attribute</a>
     */
    VALUE("value");

    /**
     * The string representation of this HTML attribute name.
     * <p>
     * This field contains the actual HTML attribute name as it would appear in HTML markup
     * (e.g., "class" for the CLASS constant).
     * </p>
     */
    @Getter
    private final String content;
}
