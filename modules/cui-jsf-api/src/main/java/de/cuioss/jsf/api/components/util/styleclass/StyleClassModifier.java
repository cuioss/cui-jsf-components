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
package de.cuioss.jsf.api.components.util.styleclass;

/**
 * This interface is needed to streamline the access of components with the
 * styleClass attribute.
 *
 * @author Oliver Wolff (Oliver Wolff)
 */
public interface StyleClassModifier {

    /**
     * Analogous to jQuery.toggleClass(): If a class is present remove it. If not
     * add it.
     *
     * @param styleClass must not be null or empty.
     * @return the modified String
     */
    String toggleStyleClass(String styleClass);

    /**
     * Add an additional StyleClass to already existing StyleClasses. The method
     * ensures that there are no duplicates of the same name
     *
     * @param styleClass must not be null or empty
     * @return the modified StyleClass String
     */
    String addStyleClass(String styleClass);

    /**
     * Removes a concrete StyleClass. If it is not there, nothing happens.
     *
     * @param styleClass must not be null
     * @return The resulting StyleClass String
     */
    String removeStyleClass(String styleClass);
}
