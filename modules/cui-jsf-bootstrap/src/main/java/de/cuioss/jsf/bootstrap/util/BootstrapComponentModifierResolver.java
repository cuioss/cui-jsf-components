package de.cuioss.jsf.bootstrap.util;

import java.util.Optional;

import javax.faces.component.UIComponent;

import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.api.components.util.modifier.ComponentModifierResolver;
import de.cuioss.jsf.bootstrap.composite.EditableDataListComponent;

/**
 * Resolves bootstrap-specific components, currently the
 * {@link EditableDataListComponent}
 *
 * @author Oliver Wolff
 *
 */
public class BootstrapComponentModifierResolver implements ComponentModifierResolver {

    @Override
    public Optional<ComponentModifier> wrap(UIComponent toBeWrapped) {
        if (toBeWrapped instanceof EditableDataListComponent) {
            return Optional.of(new EditableDataListComponentWrapper((EditableDataListComponent) toBeWrapped));
        }
        return Optional.empty();
    }

}
