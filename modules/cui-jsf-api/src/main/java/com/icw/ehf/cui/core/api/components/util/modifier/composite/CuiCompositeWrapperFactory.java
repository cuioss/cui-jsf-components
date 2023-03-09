package com.icw.ehf.cui.core.api.components.util.modifier.composite;

import java.util.Optional;

import javax.faces.component.UIComponent;

import com.icw.ehf.cui.components.bootstrap.composite.EditableDataListComponent;
import com.icw.ehf.cui.core.api.components.util.ComponentModifier;
import com.icw.ehf.cui.core.api.components.util.ComponentWrapper;

import lombok.experimental.UtilityClass;

/**
 * Factory for creating {@link ComponentWrapper} for {@link CuiCompositeWrapperFactory} composite
 * components
 *
 * @author Oliver Wolff
 *
 */
@UtilityClass
public class CuiCompositeWrapperFactory {

    /**
     * Retrieve fitting {@linkplain ComponentModifier}
     *
     * @param toBeWrapped {@linkplain UIComponent} target component.
     * @return {@linkplain ComponentModifier} if toBeWrapped matches. if not it returns
     *         an empty {@link Optional}
     */
    public static Optional<ComponentModifier> wrap(final UIComponent toBeWrapped) {
        if (toBeWrapped instanceof EditableDataListComponent) {
            return Optional.of(new EditableDataListComponentWrapper((EditableDataListComponent) toBeWrapped));
        }
        return Optional.empty();
    }
}
