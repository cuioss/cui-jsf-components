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
package de.cuioss.jsf.api.components.util;

import jakarta.faces.component.EditableValueHolder;
import jakarta.faces.component.UIComponent;

import java.util.Map;

/**
 * <p>This interface provides a unified abstraction layer for manipulating common JSF component 
 * properties and attributes across different component types. It offers a consistent API for 
 * operations like setting style classes, managing disabled state, and working with component 
 * attributes that may be implemented differently across the JSF component hierarchy.</p>
 * 
 * <p>The main purpose of this interface is to abstract away the differences between various 
 * JSF component implementations, allowing client code to work with components in a consistent 
 * way without needing to handle specific component types directly.</p>
 * 
 * <p>This interface handles common attributes like:</p>
 * <ul>
 *   <li>Style class management</li>
 *   <li>Inline style manipulation</li>
 *   <li>Disabled state control</li>
 *   <li>WAI-ARIA role assignment</li>
 *   <li>Title attribute management</li>
 *   <li>Label property access</li>
 * </ul>
 * 
 * <p>It also provides capability inspection methods that allow clients to check if a particular
 * feature is supported by the underlying component before attempting to use it.</p>
 * 
 * <p>Usage example:</p>
 * <pre>
 * // Access a component through the modifier interface
 * ComponentModifier modifier = ComponentModifierFactory.findModifier(component);
 * 
 * // Check if component supports style class manipulation
 * if (modifier.isSupportsStyleClass()) {
 *     // Add Bootstrap classes
 *     modifier.setStyleClass("btn btn-primary");
 * }
 * 
 * // Disable the component if supported
 * if (modifier.isSupportsDisabled()) {
 *     modifier.setDisabled(true);
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @see de.cuioss.jsf.api.components.util.modifier.ComponentModifierFactory
 */
public interface ComponentModifier {

    /**
     * <p>Determines whether the underlying component supports style class attributes.</p>
     * 
     * <p>This capability check allows client code to determine if style class manipulation
     * operations like {@link #setStyleClass(String)} will have any effect on the component.</p>
     *
     * @return {@code true} if the component supports the styleClass attribute,
     *         {@code false} otherwise
     */
    boolean isSupportsStyleClass();

    /**
     * <p>Sets the CSS style class for the component.</p>
     * 
     * <p>This method assigns CSS classes to the component's rendered output.
     * Multiple classes can be provided as a space-separated list.</p>
     *
     * @param styleClass The CSS class or classes to apply to the component.
     *                   May be null to clear existing classes.
     */
    void setStyleClass(String styleClass);

    /**
     * <p>Retrieves the currently assigned CSS style class for the component.</p>
     *
     * @return The current CSS class(es) assigned to the component, or null if none are assigned
     */
    String getStyleClass();

    /**
     * <p>Determines whether the underlying component supports inline style attributes.</p>
     * 
     * <p>This capability check allows client code to determine if inline style manipulation
     * operations like {@link #setStyle(String)} will have any effect on the component.</p>
     *
     * @return {@code true} if the component supports the style attribute,
     *         {@code false} otherwise
     */
    boolean isSupportsStyle();

    /**
     * <p>Sets the inline CSS style for the component.</p>
     * 
     * <p>This method assigns inline CSS styles to the component's rendered output.
     * Format should follow standard inline style syntax (e.g., "color: red; font-weight: bold").</p>
     *
     * @param style The inline CSS style to apply to the component.
     *              May be null to clear existing inline styles.
     */
    void setStyle(String style);

    /**
     * <p>Retrieves the currently assigned inline CSS style for the component.</p>
     *
     * @return The current inline CSS style assigned to the component, or null if none is assigned
     */
    String getStyle();

    /**
     * <p>Determines whether the component is currently in a disabled state.</p>
     * 
     * <p>Disabled components typically don't respond to user interaction and may
     * be visually styled to indicate their inactive state.</p>
     *
     * @return {@code true} if the component is disabled, {@code false} otherwise
     */
    boolean isDisabled();

    /**
     * <p>Sets the disabled state of the component.</p>
     * 
     * <p>Disabling a component typically makes it non-interactive and may apply
     * visual styling to indicate the disabled state.</p>
     *
     * @param disabled {@code true} to disable the component, {@code false} to enable it
     */
    void setDisabled(boolean disabled);

    /**
     * <p>Determines whether the underlying component supports being disabled.</p>
     * 
     * <p>This capability check allows client code to determine if operations like
     * {@link #setDisabled(boolean)} will have any effect on the component.</p>
     *
     * @return {@code true} if the component supports being disabled,
     *         {@code false} otherwise
     */
    boolean isSupportsDisabled();

    /**
     * <p>Sets the WAI-ARIA role attribute for the component.</p>
     * 
     * <p>The role attribute helps define the purpose of an element in an
     * accessibility tree, which can help assistive technologies understand the
     * component's function.</p>
     *
     * @param role The WAI-ARIA role to assign to the component.
     *             May be null to clear the role.
     */
    void setRole(String role);

    /**
     * <p>Retrieves the currently assigned WAI-ARIA role for the component.</p>
     *
     * @return The current WAI-ARIA role assigned to the component, or null if none is assigned
     */
    String getRole();

    /**
     * <p>Determines whether the underlying component supports WAI-ARIA role attributes.</p>
     * 
     * <p>This capability check allows client code to determine if role-related operations
     * like {@link #setRole(String)} will have any effect on the component.</p>
     *
     * @return {@code true} if the component supports the role attribute,
     *         {@code false} otherwise
     */
    boolean isSupportsRole();

    /**
     * <p>Sets the title attribute for the component.</p>
     * 
     * <p>The title attribute typically appears as a tooltip when a user hovers
     * over the component and can provide additional context or help information.</p>
     *
     * @param title The title text to assign to the component.
     *              May be null to clear the title.
     */
    void setTitle(String title);

    /**
     * <p>Retrieves the currently assigned title for the component.</p>
     *
     * @return The current title text assigned to the component, or null if none is assigned
     */
    String getTitle();

    /**
     * <p>Determines whether the underlying component supports title attributes.</p>
     * 
     * <p>This capability check allows client code to determine if title-related operations
     * like {@link #setTitle(String)} will have any effect on the component.</p>
     *
     * @return {@code true} if the component supports the title attribute,
     *         {@code false} otherwise
     */
    boolean isSupportsTitle();

    /**
     * <p>Determines whether the wrapped component implements {@link EditableValueHolder}.</p>
     * 
     * <p>Editable value holders are input components that can contain and submit
     * user-modifiable values. This check is useful to determine if methods specific
     * to input components (like {@link #isValid()}, {@link #resetValue()}) can be used.</p>
     *
     * @return {@code true} if the component implements {@link EditableValueHolder},
     *         {@code false} otherwise
     */
    boolean isEditableValueHolder();

    /**
     * <p>Determines whether an editable value holder component is in a valid state.</p>
     * 
     * <p>This method should only be called if {@link #isEditableValueHolder()} returns true.
     * It reflects whether any validation errors exist on the component.</p>
     *
     * @return {@code true} if the component's current value is valid, {@code false} otherwise
     * @throws UnsupportedOperationException if the component is not an {@link EditableValueHolder}
     */
    boolean isValid();

    /**
     * <p>Determines whether an editable value holder component requires a value to be submitted.</p>
     * 
     * <p>This method should only be called if {@link #isEditableValueHolder()} returns true.
     * It reflects whether the component is marked as requiring user input.</p>
     *
     * @return {@code true} if the component requires a value, {@code false} otherwise
     * @throws UnsupportedOperationException if the component is not an {@link EditableValueHolder}
     */
    boolean isRequired();

    /**
     * <p>Determines whether the component supports having a label assigned.</p>
     * 
     * <p>This capability check allows client code to determine if label-related operations
     * like {@link #setLabel(String)} will have any effect on the component.</p>
     *
     * @return {@code true} if the component supports having a label,
     *         {@code false} otherwise
     */
    boolean isSupportsLabel();

    /**
     * <p>Determines whether the component supports resetting its value.</p>
     * 
     * <p>By default, this returns true if the component is an {@link EditableValueHolder},
     * but implementations may override this behavior.</p>
     *
     * @return {@code true} if the component supports {@link #resetValue()},
     *         {@code false} otherwise
     */
    default boolean isSupportsResetValue() {
        return isEditableValueHolder();
    }

    /**
     * <p>Resets the component's value to its initial state.</p>
     * 
     * <p>For {@link EditableValueHolder} components, this invokes
     * {@link EditableValueHolder#resetValue()} to clear the submitted value,
     * validation errors, and potentially the component's value.</p>
     * 
     * <p>This method should only be called if {@link #isSupportsResetValue()} returns true.</p>
     *
     * @throws UnsupportedOperationException if the component does not support resetting values
     */
    void resetValue();

    /**
     * <p>Sets the label text for the component.</p>
     * 
     * <p>The label typically identifies the component to users and may be rendered
     * visually or used by assistive technologies.</p>
     *
     * @param label The label text to assign to the component.
     *              May be null to clear the label.
     */
    void setLabel(String label);

    /**
     * <p>Checks whether the wrapped component is of a specific type.</p>
     * 
     * <p>This method is useful for determining if a component can be safely cast
     * to a specific component class.</p>
     *
     * @param klazz The component class to check against
     * @return {@code true} if the wrapped component is an instance of the specified class,
     *         {@code false} otherwise
     */
    boolean wrapsComponentClass(Class<? extends UIComponent> klazz);

    /**
     * <p>Retrieves the underlying wrapped component.</p>
     * 
     * <p>This method provides access to the actual JSF component that this modifier
     * is working with.</p>
     *
     * @return The wrapped {@link UIComponent} instance
     */
    UIComponent getComponent();

    /**
     * <p>Retrieves the label assigned to the component.</p>
     * 
     * <p>The label is typically used to identify the component to users.</p>
     *
     * @return The label text for the component, or null if none is assigned
     */
    String getLabel();

    /**
     * <p>Determines whether the component is a composite input component.</p>
     * 
     * <p>Composite input components are complex components that consist of multiple
     * input elements that are presented as a single logical input. Examples include
     * date pickers (with separate day/month/year inputs) or credit card inputs
     * (with separate number, expiry, CVV inputs).</p>
     * 
     * <p>This information is useful for message handling, as composite inputs may
     * require multiple message components to display all validation errors.</p>
     *
     * @return {@code true} if the component is a composite input with multiple input elements,
     *         {@code false} if it's a simple input or not an input at all
     * @see #getForIndentifiers()
     */
    boolean isCompositeInput();

    /**
     * <p>Adds a pass-through attribute to the component.</p>
     * 
     * <p>Pass-through attributes allow setting arbitrary HTML attributes on the rendered
     * output that don't correspond to defined JSF component properties. This is useful for
     * setting HTML5 data attributes, ARIA attributes, or custom attributes.</p>
     * 
     * <p>This method uses a fluent interface pattern, returning the modifier itself
     * to allow for method chaining.</p>
     *
     * @param key   The name of the HTML attribute to set
     * @param value The value to assign to the attribute
     * @return This {@link ComponentModifier} instance for method chaining
     */
    default ComponentModifier addPassThrough(String key, Object value) {
        var map = getComponent().getPassThroughAttributes();
        map.put(key, value);
        return this;
    }

    /**
     * <p>Retrieves identifiers for related components in a composite input.</p>
     * 
     * <p>For composite input components, this method returns identifiers that can be
     * used to associate elements like labels and messages with the appropriate input
     * elements.</p>
     * 
     * <p>This is typically used in conjunction with {@link #isCompositeInput()}.</p>
     *
     * @return A string containing identifiers for the related components
     * @see #isCompositeInput()
     */
    String getForIndentifiers();

    /**
     * <p>Determines whether the component should be rendered.</p>
     * 
     * <p>Components that are not rendered will not appear in the generated HTML output
     * and will not participate in the processing lifecycle phases like validation and
     * update model.</p>
     *
     * @return {@code true} if the component should be rendered,
     *         {@code false} if it should be skipped
     */
    boolean isRendered();
}
