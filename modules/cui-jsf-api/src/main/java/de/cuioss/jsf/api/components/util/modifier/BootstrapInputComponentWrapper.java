package de.cuioss.jsf.api.components.util.modifier;

import de.cuioss.jsf.api.components.base.BaseCuiInputComponent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Oliver Wolff
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class BootstrapInputComponentWrapper extends ReflectionBasedModifier {

    private final BaseCuiInputComponent component;

    /**
     * @param component
     */
    public BootstrapInputComponentWrapper(final BaseCuiInputComponent component) {
        super(component);
        this.component = component;
    }

    @Override
    public boolean isSupportsStyleClass() {
        return true;
    }

    @Override
    public void setStyleClass(final String styleClass) {
        this.component.setStyleClass(styleClass);

    }

    @Override
    public String getStyleClass() {
        return this.component.getStyleClass();
    }

    @Override
    public boolean isSupportsStyle() {
        return true;
    }

    @Override
    public String getStyle() {
        return this.component.getStyle();
    }

    @Override
    public void setStyle(final String style) {
        this.component.setStyle(style);
    }

    @Override
    public boolean isEditableValueHolder() {
        return true;
    }

    @Override
    public boolean isValid() {
        return this.component.isValid();
    }

    @Override
    public boolean isRequired() {
        return this.component.isRequired();
    }

}
