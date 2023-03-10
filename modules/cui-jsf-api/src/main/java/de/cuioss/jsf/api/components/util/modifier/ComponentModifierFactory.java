package de.cuioss.jsf.api.components.util.modifier;

import static java.util.Objects.requireNonNull;

import javax.faces.component.UIComponent;

import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.api.components.util.modifier.composite.CuiCompositeWrapperFactory;

/**
 * Factory for creating wrapper classes defining an interface like contract for
 * the disabled attribute.
 *
 * @author Oliver Wolff
 */
public enum ComponentModifierFactory {

    ;

    /**
     * Retrieve fitting {@linkplain ComponentModifier}
     *
     * @param toBeWrapped {@linkplain UIComponent} target component. Must not be {@code null}
     * @return {@linkplain ComponentModifier} if mapping is defined. if none is defined it returns
     *         a {@link ReflectionBasedModifier}
     */
    public static ComponentModifier findFittingWrapper(final UIComponent toBeWrapped) {
        requireNonNull(toBeWrapped);

        var wrapper = CuiCompositeWrapperFactory.wrap(toBeWrapped);

        if (!wrapper.isPresent()) {
            wrapper = CuiInterfaceBasedModifier.wrap(toBeWrapped);
        }
        return wrapper.orElse(new ReflectionBasedModifier(toBeWrapped));
    }

}
