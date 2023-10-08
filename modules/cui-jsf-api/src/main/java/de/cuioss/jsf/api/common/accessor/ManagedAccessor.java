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

import javax.faces.context.FacesContext;

/**
 * <p>
 * Utility used for accessing transient values in a non transient way. The
 * {@link ManagedAccessor} itself implements {@link Serializable} but the
 * wrapped value is to be kept in a transient field. In order to work properly
 * the concrete accessor must take care on this.
 * <p>
 * To sum it up, the purpose of accesors are
 * <ul>
 * <li>Failsafe implementation of {@link Serializable} / transient contract</li>
 * <li>Implicit lazy loading of the Attributes / beans / whatever
 * <li>Hiding of the loading mechanics of the object to be loaded.</li>
 * <li>Unifying usage of common objects like {@link ResourceBundle}, etc.</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @param <T> identifying the concrete type
 */
public interface ManagedAccessor<T> extends Serializable {

    /**
     * Accesses the managed value.
     *
     * @return the managed value.
     * @throws IllegalStateException may occur in corner cases, where
     *                               {@link FacesContext} is not initialized
     *                               properly
     */
    T getValue();
}
