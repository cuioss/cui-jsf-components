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

import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;

/**
 * Helper class that defines an interface over some generic methods of
 * JSF-Components like is/set Disable and set/getStyleclass.
 *
 * @author Oliver Wolff
 */
public interface ComponentModifier {

    /**
     * @return flag indicating whether the wrapper supports the styleClassAttribute;
     */
    boolean isSupportsStyleClass();

    /**
     * Sets the styleClass attribute.
     *
     * @param styleClass may be null
     */
    void setStyleClass(String styleClass);

    /**
     * @return the currently set StyleClass
     */
    String getStyleClass();

    /**
     * @return flag indicating whether the wrapper supports the style-attribute;
     */
    boolean isSupportsStyle();

    /**
     * Sets the style-attribute.
     *
     * @param style may be null
     */
    void setStyle(String style);

    /**
     * @return the currently set style-attribute
     */
    String getStyle();

    /**
     * @return boolean indicating whether the current component is disabled.
     */
    boolean isDisabled();

    /**
     * @param disabled attribute to be set.
     */
    void setDisabled(boolean disabled);

    /**
     * @return flag indicating whether the wrapper supports the disabled attribute;
     */
    boolean isSupportsDisabled();

    /**
     * Sets the role attribute.
     *
     * @param role may be null
     */
    void setRole(String role);

    /**
     * @return the currently set role
     */
    String getRole();

    /**
     * @return flag indicating whether the wrapper supports the role attribute;
     */
    boolean isSupportsRole();

    /**
     * Sets the title attribute.
     *
     * @param title may be null
     */
    void setTitle(String title);

    /**
     * @return the currently set title
     */
    String getTitle();

    /**
     * @return flag indicating whether the wrapper supports the Title attribute;
     */
    boolean isSupportsTitle();

    /**
     * @return flag indicating whether wrapped component is of type
     *         {@link EditableValueHolder}
     */
    boolean isEditableValueHolder();

    /**
     * @return in case of {@link #isEditableValueHolder()} this flag indicates
     *         whether the component is valid. throws
     *         {@link UnsupportedOperationException} if not
     *         {@link #isEditableValueHolder()}.
     */
    boolean isValid();

    /**
     * @return in case of {@link #isEditableValueHolder()} this flag indicates
     *         whether the component is required. throws
     *         {@link UnsupportedOperationException} if not
     *         {@link #isEditableValueHolder()}.
     */
    boolean isRequired();

    /**
     * @return boolean indicating whether a label can be set, usually as value
     *         attribute
     */
    boolean isSupportsLabel();

    /**
     * @return boolean indicating whether the component supports
     *         {@link EditableValueHolder#resetValue()}
     */
    default boolean isSupportsResetValue() {
        return isEditableValueHolder();
    }

    /**
     * Calls underlying {@link EditableValueHolder#resetValue()}
     */
    void resetValue();

    /**
     * @param label change the label of component
     */
    void setLabel(String label);

    /**
     * @param klazz
     * @return boolean indicating whether the wrapped component is exact from one
     *         type
     */
    boolean wrapsComponentClass(Class<? extends UIComponent> klazz);

    /**
     * @return the wrapped component
     */
    UIComponent getComponent();

    /**
     * @return label for the component
     */
    String getLabel();

    /**
     * @return flag indicating whether the concrete component consists of more than
     *         one input element, requiring more than one message element to be
     *         rendered and attached. This flag works together with
     *         #getForIndentifiers that in consequence returns the corresponding
     *         multiple ids identifying the components.
     */
    boolean isCompositeInput();

    /**
     * Shorthand for call {@link #getComponent()} -&gt;
     * {@link UIComponent#getPassThroughAttributes()}
     * {@link Map#put(Object, Object)}
     *
     * @param key   to be put as Passthrough-attribute
     * @param value to be put as Passthrough-attribute
     * @return the {@link ComponentModifier} itself in order to be used in a fluent
     *         style
     */
    default ComponentModifier addPassThrough(String key, Object value) {
        var map = getComponent().getPassThroughAttributes();
        map.put(key, value);
        return this;
    }

    /**
     * @see #isCompositeInput()
     * @return id of corresponding component which this one belongs to
     */
    String getForIndentifiers();

    /**
     * @return true if the component should be rendered
     */
    boolean isRendered();
}
