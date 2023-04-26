package de.cuioss.jsf.api.components.util.modifier;

import java.util.Optional;

import javax.faces.component.UIComponent;

import de.cuioss.jsf.api.components.util.ComponentModifier;

/**
 * Used for the SPI to define Resolver for concrete {@link ComponentModifier}
 * 
 * @author Oliver Wolff
 *
 */
public interface ComponentModifierResolver {

    /**
     * Retrieve fitting {@linkplain ComponentModifier}
     *
     * @param toBeWrapped {@linkplain UIComponent} target component.
     * @return {@linkplain ComponentModifier} if toBeWrapped matches. if not it returns
     *         an empty {@link Optional}
     */
    Optional<ComponentModifier> wrap(final UIComponent toBeWrapped);

}
