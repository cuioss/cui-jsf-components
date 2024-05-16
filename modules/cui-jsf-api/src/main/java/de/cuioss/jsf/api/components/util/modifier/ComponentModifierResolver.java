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
package de.cuioss.jsf.api.components.util.modifier;

import java.util.Optional;

import jakarta.faces.component.UIComponent;

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
     * @return {@linkplain ComponentModifier} if toBeWrapped matches. if not it
     *         returns an empty {@link Optional}
     */
    Optional<ComponentModifier> wrap(final UIComponent toBeWrapped);

}
