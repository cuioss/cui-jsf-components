package com.icw.ehf.cui.core.api.components.util.modifier;

import javax.faces.component.UIInput;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represent a generic {@linkplain UIInput} ComponentModifier.
 * Used as fallback for any UIInput components
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@ToString
@EqualsAndHashCode(callSuper = true)
public class GenericUiInputComponentWrapper extends ReflectionBasedModifier {

    /**
     * @param component
     */
    public GenericUiInputComponentWrapper(final UIInput component) {
        super(component);
        this.inputComponent = component;
    }

    @Getter
    private final UIInput inputComponent;

    @Override
    public boolean isEditableValueHolder() {
        return true;
    }

    @Override
    public boolean isValid() {
        return this.inputComponent.isValid();
    }

    @Override
    public boolean isRequired() {
        return this.inputComponent.isRequired();
    }

}
