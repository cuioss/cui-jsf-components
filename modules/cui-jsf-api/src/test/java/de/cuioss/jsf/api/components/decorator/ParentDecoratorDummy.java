package de.cuioss.jsf.api.components.decorator;

import de.cuioss.jsf.api.components.util.ComponentModifier;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("javadoc")
public class ParentDecoratorDummy extends AbstractParentDecorator {

    @Getter
    @Setter
    private boolean decorateCalled = false;

    @Override
    public void decorate(final ComponentModifier parent) {
        decorateCalled = true;

    }

    @Override
    public String getFamily() {
        return "de.cuioss.jsf.api.components.decorator.ParentDecoratorDummy";
    }

}
