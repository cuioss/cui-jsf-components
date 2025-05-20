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
package de.cuioss.jsf.bootstrap.util;

import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.api.components.util.modifier.ComponentModifierResolver;
import de.cuioss.jsf.bootstrap.composite.EditableDataListComponent;
import jakarta.faces.component.UIComponent;

import java.util.Optional;

/**
 * Implementation of {@link ComponentModifierResolver} for Bootstrap-specific
 * components. Currently handles:
 * <ul>
 *   <li>{@link EditableDataListComponent} - Using {@link EditableDataListComponentWrapper}</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 *
 * @see ComponentModifierResolver
 * @see EditableDataListComponentWrapper
 */
public class BootstrapComponentModifierResolver implements ComponentModifierResolver {

    /**
     * {@inheritDoc}
     *
     * <p>This implementation checks if the provided component is a Bootstrap-specific
     * component that can be wrapped with a suitable {@link ComponentModifier}. Currently,
     * it supports wrapping {@link EditableDataListComponent} instances.</p>
     *
     * @param toBeWrapped the component to be wrapped, must not be null
     * @return an {@link Optional} containing the appropriate {@link ComponentModifier}
     *         if the component is supported, or an empty Optional otherwise
     */
    @Override
    public Optional<ComponentModifier> wrap(UIComponent toBeWrapped) {
        if (toBeWrapped instanceof EditableDataListComponent component) {
            return Optional.of(new EditableDataListComponentWrapper(component));
        }
        return Optional.empty();
    }
}
