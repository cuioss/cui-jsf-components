package com.icw.ehf.cui.core.api.components.util.styleclass;

import javax.faces.component.UIComponent;

import com.icw.ehf.cui.core.api.components.util.modifier.ComponentModifierFactory;

/**
 * Factory for creating wrapper classes dealing with modification of styleClass
 * attributes.
 *
 * @author Oliver Wolff (Oliver Wolff)
 */
public enum StyleClassModifierFactory {

    ;

    /**
     * Factory method to wrap {@link UIComponent} with {@link CombinedComponentModifier}
     * functionality.
     *
     * @param toBeWrapped {@linkplain UIComponent} target to be wrapped
     * @return {@link CombinedComponentModifier} if component provide styleClass attribute
     * @throws IllegalArgumentException if toBeWrapped component doesn't provide styleClass
     *             attribute
     */
    public static CombinedComponentModifier findFittingWrapper(final UIComponent toBeWrapped) {
        final var componentModifier =
            ComponentModifierFactory.findFittingWrapper(toBeWrapped);
        if (componentModifier.isSupportsStyleClass()) {
            return new DelegateStyleClassModifier(componentModifier);
        }
        throw new IllegalArgumentException("No wrapper providing styleClass implemented for "
                + toBeWrapped.getClass());
    }
}
