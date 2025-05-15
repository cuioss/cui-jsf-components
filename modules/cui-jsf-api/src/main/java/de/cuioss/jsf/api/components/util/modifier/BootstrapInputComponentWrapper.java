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
package de.cuioss.jsf.api.components.util.modifier;

import de.cuioss.jsf.api.components.base.BaseCuiInputComponent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>A specialized implementation of {@link ReflectionBasedModifier} designed specifically for
 * Bootstrap-styled input components that extend {@link BaseCuiInputComponent}.</p>
 * 
 * <p>This wrapper provides direct access to the style-related methods and editable value holder 
 * functionality of Bootstrap input components. It overrides the default reflection-based 
 * implementation with direct method calls, improving performance and ensuring 
 * proper functionality with Bootstrap's styling patterns.</p>
 * 
 * <p>Unlike the generic {@link ReflectionBasedModifier}, this wrapper has built-in knowledge 
 * of the Bootstrap component structure and can directly access properties without reflection.</p>
 * 
 * <p>Usage example:</p>
 * <pre>
 * BaseCuiInputComponent inputComponent = ...;
 * BootstrapInputComponentWrapper wrapper = new BootstrapInputComponentWrapper(inputComponent);
 * 
 * // Add Bootstrap-specific classes
 * wrapper.setStyleClass("form-control form-control-sm");
 * 
 * // Check validation state
 * if (!wrapper.isValid()) {
 *     wrapper.addStyleClass("is-invalid");
 * }
 * </pre>
 * 
 * @author Oliver Wolff
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class BootstrapInputComponentWrapper extends ReflectionBasedModifier {

    private final BaseCuiInputComponent component;

    /**
     * <p>Constructs a new wrapper around the specified Bootstrap input component.</p>
     * 
     * <p>This constructor initializes both the parent class (with the component as a
     * generic UIComponent) and stores a direct reference to access Bootstrap-specific
     * methods without reflection.</p>
     *
     * @param component The Bootstrap input component to wrap. Must not be null.
     */
    public BootstrapInputComponentWrapper(final BaseCuiInputComponent component) {
        super(component);
        this.component = component;
    }

    /**
     * {@inheritDoc}
     * <p>Bootstrap input components always support style classes.</p>
     * 
     * @return {@code true} always, as Bootstrap input components support style classes
     */
    @Override
    public boolean isSupportsStyleClass() {
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>Directly sets the style class on the wrapped Bootstrap component without using reflection.</p>
     */
    @Override
    public void setStyleClass(final String styleClass) {
        component.setStyleClass(styleClass);

    }

    /**
     * {@inheritDoc}
     * <p>Directly retrieves the style class from the wrapped Bootstrap component without using reflection.</p>
     */
    @Override
    public String getStyleClass() {
        return component.getStyleClass();
    }

    /**
     * {@inheritDoc}
     * <p>Bootstrap input components always support inline styles.</p>
     * 
     * @return {@code true} always, as Bootstrap input components support inline styles
     */
    @Override
    public boolean isSupportsStyle() {
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>Directly retrieves the inline style from the wrapped Bootstrap component without using reflection.</p>
     */
    @Override
    public String getStyle() {
        return component.getStyle();
    }

    /**
     * {@inheritDoc}
     * <p>Directly sets the inline style on the wrapped Bootstrap component without using reflection.</p>
     */
    @Override
    public void setStyle(final String style) {
        component.setStyle(style);
    }

    /**
     * {@inheritDoc}
     * <p>Bootstrap input components are always editable value holders.</p>
     * 
     * @return {@code true} always, as Bootstrap input components implement {@link jakarta.faces.component.EditableValueHolder}
     */
    @Override
    public boolean isEditableValueHolder() {
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>Directly checks the validation state of the wrapped Bootstrap component without using reflection.</p>
     */
    @Override
    public boolean isValid() {
        return component.isValid();
    }

    /**
     * {@inheritDoc}
     * <p>Directly checks the required state of the wrapped Bootstrap component without using reflection.</p>
     */
    @Override
    public boolean isRequired() {
        return component.isRequired();
    }

}
