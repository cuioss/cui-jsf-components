package de.cuioss.jsf.api.components.util.modifier;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.ServiceLoader;

import javax.faces.component.UIComponent;

import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.tools.logging.CuiLogger;
import lombok.experimental.UtilityClass;

/**
 * Factory for creating wrapper classes defining an interface like contract for
 * the disabled attribute.
 *
 * @author Oliver Wolff
 */
@UtilityClass
public class ComponentModifierFactory {

    private final CuiLogger LOGGER = new CuiLogger(ComponentModifierFactory.class);

    /**
     * Retrieve fitting {@linkplain ComponentModifier}
     *
     * @param toBeWrapped {@linkplain UIComponent} target component. Must not be
     *                    {@code null}
     * @return {@linkplain ComponentModifier} if mapping is defined. if none is
     *         defined it returns a {@link ReflectionBasedModifier}
     */
    public static ComponentModifier findFittingWrapper(final UIComponent toBeWrapped) {
        requireNonNull(toBeWrapped);

        LOGGER.trace("Resolving for %s. First try from SPI", toBeWrapped.getClass());

        var iterator = loadResolver();
        while (iterator.hasNext()) {
            var resolved = iterator.next().wrap(toBeWrapped);
            if (resolved.isPresent()) {
                LOGGER.trace("Resolved %s for %s", resolved.get().getClass(), toBeWrapped.getClass());
                return resolved.get();
            }
        }
        LOGGER.trace("Not Found by SPI, checking interfaces-based");
        var wrapper = CuiInterfaceBasedModifier.wrap(toBeWrapped);
        return wrapper.orElse(new ReflectionBasedModifier(toBeWrapped));
    }

    static Iterator<ComponentModifierResolver> loadResolver() {
        ServiceLoader<ComponentModifierResolver> loader = ServiceLoader.load(ComponentModifierResolver.class);
        return loader.iterator();
    }

}
