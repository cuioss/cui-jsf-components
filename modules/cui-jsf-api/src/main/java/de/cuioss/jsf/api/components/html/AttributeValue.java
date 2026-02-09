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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing commonly used HTML attribute values for use with {@link HtmlTreeBuilder}.
 * <p>
 * This enum provides a type-safe way to work with HTML attribute values when constructing
 * HTML markup programmatically. Each constant represents a specific value with its
 * corresponding string representation, ensuring consistency and reducing errors
 * from manual string typing.
 * 
 * <p>
 * The enum includes common values for various attributes, organized in these categories:
 * <ul>
 *   <li>ARIA accessibility values (polite, close)</li>
 *   <li>Boolean values (true, false)</li>
 *   <li>Role values (dialog, tablist)</li>
 *   <li>Input type values (text, checkbox, button, submit)</li>
 *   <li>Data attribute values and prefixes</li>
 *   <li>Common style values (display:none)</li>
 * </ul>
 * 
 * <p>
 * These constants are designed to be used with {@link AttributeName} to create complete
 * HTML attributes when building HTML structures with {@link HtmlTreeBuilder}.
 * 
 * <p>
 * Usage example:
 * <pre>
 * HtmlTreeBuilder builder = new HtmlTreeBuilder();
 * 
 * // Create a hidden input checkbox
 * builder.withNode(Node.INPUT)
 *        .withAttributeNameAndValue(AttributeName.TYPE, AttributeValue.INPUT_CHECKBOX)
 *        .withAttributeNameAndValue(AttributeName.ARIA_HIDDEN, AttributeValue.TRUE);
 *        
 * // Renders: &lt;input type="checkbox" aria-hidden="true"&gt;
 * String html = builder.getHtml();
 * </pre>
 * 
 * <p>
 * This enum is thread-safe and immutable, making it safe to use in concurrent environments.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see HtmlTreeBuilder
 * @see AttributeName
 * @see Node
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AttributeValue {

    /**
     * Value for aria close label, "Close".
     * <p>
     * Typically used with ARIA_LABEL attribute to provide accessibility
     * for close buttons in dialogs and other widgets.
     * </p>
     */
    ARIA_CLOSE("Close"),

    /**
     * Value for aria-live attribute, "polite".
     * <p>
     * Indicates that updates to a live region should be announced to screen readers
     * when the user is idle, avoiding interrupting the current task.
     * </p>
     */
    ARIA_POLITE("polite"),

    /**
     * Value for cui-specific click binding attribute, "data-cui-click-binding".
     * <p>
     * Used to bind click event handlers in the CUI framework.
     * </p>
     */
    CUI_CLICK_BINDING("data-cui-click-binding"),

    /**
     * Standard prefix for HTML5 custom data attributes, "data-".
     * <p>
     * This prefix is used to create custom attributes that store private
     * application data for which there are no appropriate attributes or elements.
     * </p>
     */
    DATA_PREFIX("data-"),

    /**
     * Value for data-toggle attribute, "data-toggle".
     * <p>
     * Commonly used in UI frameworks to enable toggling functionality on elements.
     * </p>
     */
    DATA_TOGGLE("data-toggle"),

    /**
     * String representation of boolean false value, "false".
     * <p>
     * Used for boolean HTML attributes and ARIA states.
     * </p>
     */
    FALSE("false"),

    /**
     * HTML "hidden" attribute value.
     * <p>
     * When present on an element, it indicates that the element is not yet, or is no longer, relevant.
     * Browsers should not display elements with the hidden attribute.
     * </p>
     *
     * @see <a href="http://www.w3schools.com/tags/att_global_hidden.asp">HTML hidden Attribute</a>
     */
    HIDDEN("hidden"),

    /**
     * Value for input type attribute indicating a button, "button".
     * <p>
     * Creates a button input control that can be programmed to control custom functionality.
     * </p>
     */
    INPUT_BUTTON("button"),

    /**
     * Value for input type attribute indicating a checkbox, "checkbox".
     * <p>
     * Creates a checkbox input control that can be toggled on or off.
     * </p>
     */
    INPUT_CHECKBOX("checkbox"),

    /**
     * Value for input type attribute indicating a text field, "text".
     * <p>
     * Creates a basic single-line text input control.
     * </p>
     */
    INPUT_TEXT("text"),

    /**
     * Value for role attribute indicating a dialog, "dialog".
     * <p>
     * Identifies the element as a dialog, a window overlaid on the page content.
     * </p>
     */
    ROLE_DIALOG("dialog"),

    /**
     * CSS style value to hide an element, "display:none;".
     * <p>
     * When applied to an element's style attribute, the element will not be displayed
     * and will not take up space in the layout.
     * </p>
     */
    STYLE_DISPLAY_NONE("display:none;"),

    /**
     * Value for role attribute indicating a tab list, "tablist".
     * <p>
     * Identifies the element as a list of tab elements, which are references to tabpanel elements.
     * </p>
     */
    TABLIST("tablist"),

    /**
     * Value for data-toggle attribute to create a tooltip, "tooltip".
     * <p>
     * Used in various UI frameworks to enable tooltip functionality.
     * </p>
     */
    TOOLTIP("tooltip"),

    /**
     * String representation of boolean true value, "true".
     * <p>
     * Used for boolean HTML attributes and ARIA states.
     * </p>
     */
    TRUE("true"),

    /**
     * Value for input type attribute indicating a submit button, "submit".
     * <p>
     * Creates a button that submits the form when clicked.
     * </p>
     */
    TYPE_SUBMIT("submit");

    /**
     * The string representation of this attribute value.
     * <p>
     * This field contains the actual value to be used in HTML attributes.
     * </p>
     */
    @Getter
    private final String content;

}
