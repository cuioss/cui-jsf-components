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
package de.cuioss.jsf.api.common.accessor;

import java.io.Serializable;
import java.util.ResourceBundle;

import jakarta.faces.context.FacesContext;

/**
 * Defines a pattern for accessing transient values in a serialization-safe manner within JSF.
 * <p>
 * The {@code ManagedAccessor} pattern addresses common challenges in JSF applications
 * related to serialization and lazy loading of objects. This interface implements
 * {@link Serializable}, but implementations should store their wrapped values in transient
 * fields. When the accessor is deserialized, it can reconstruct or reload the transient
 * value as needed.
 * 
 * <p>
 * Core benefits of the accessor pattern include:
 * <ul>
 *   <li>Safe implementation of {@link Serializable} contract with transient objects</li>
 *   <li>Implicit lazy loading of attributes, beans, or other resources</li>
 *   <li>Encapsulation of the loading mechanism for the accessed object</li>
 *   <li>Unified approach for accessing common objects like {@link ResourceBundle}</li>
 *   <li>Simplified recovery after deserialization in JSF view state</li>
 * </ul>
 * 
 * <p>
 * Implementing classes should follow these guidelines:
 * <ul>
 *   <li>The wrapped value should be stored in a transient field</li>
 *   <li>The {@link #getValue()} method should handle lazy initialization</li>
 *   <li>All serializable state should be properly managed</li>
 * </ul>
 * 
 * <p>
 * Thread-safety requirements depend on the specific implementation and use case.
 *
 * @author Oliver Wolff
 * @param <T> The type of the value being accessed
 * @since 1.0
 * @see ConverterAccessor
 * @see LocaleAccessor
 * @see CuiProjectStageAccessor
 */
public interface ManagedAccessor<T> extends Serializable {

    /**
     * Accesses the managed value, loading or initializing it if necessary.
     * <p>
     * This method should handle lazy initialization of the value if it hasn't been
     * loaded yet or if it was cleared due to serialization/deserialization.
     * 
     * <p>
     * Implementations might depend on JSF-specific context information like
     * {@link FacesContext} to load or initialize the value.
     *
     * @return The managed value
     * @throws IllegalStateException may occur in corner cases, where
     *                               {@link FacesContext} is not initialized
     *                               properly or other required resources are unavailable
     */
    T getValue();
}
