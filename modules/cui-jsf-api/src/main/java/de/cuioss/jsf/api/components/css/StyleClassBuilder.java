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
package de.cuioss.jsf.api.components.css;

/**
 * Simple interface for objects that can provide styleClasses. It can be used
 * for dynamically modifying styleClass-strings using a fluent-api
 *
 * @author Oliver Wolff
 */
public interface StyleClassBuilder {

    /**
     * @return the styleClass. It will never return null but an empty String if no
     * style-class is set.
     */
    String getStyleClass();

    /**
     * @return boolean indicating whether the builder is not empty.
     */
    boolean isAvailable();

    /**
     * @param styleClass to be appended to the styleClass string. If it is null or
     *                   empty, it will be ignored. The same for duplicate entries.
     * @return the resulting {@link StyleClassBuilder} instance
     */
    StyleClassBuilder append(String styleClass);

    /**
     * @param styleClassBuilder If it is null or empty, it will be ignored.
     *                          The same for duplicate entries.
     * @return the resulting {@link StyleClassBuilder} instance. If it is null or
     * empty, it will be ignored. The same for duplicate entries.
     */
    StyleClassBuilder append(StyleClassBuilder styleClassBuilder);

    /**
     * @param styleClassProvider If it is null or empty, it will be ignored.
     *                           The same for duplicate entries.
     * @return the resulting {@link StyleClassBuilder} instance. If it is null or
     * empty, it will be ignored. The same for duplicate entries.
     */
    StyleClassBuilder append(StyleClassProvider styleClassProvider);

    /**
     * @param styleClassProvider If it is null or empty, it will be ignored.
     *                           The same for duplicate entries.
     * @param condition          Append only if this is true.
     * @return the resulting {@link StyleClassBuilder} instance. If it is null or
     * empty, it will be ignored. The same for duplicate entries.
     */
    StyleClassBuilder appendIfTrue(StyleClassProvider styleClassProvider, boolean condition);

    /**
     * @param styleClass to be removed from the styleClass string. If it is null or
     *                   empty, it will be ignored.
     * @return the resulting {@link StyleClassBuilder} instance
     */
    StyleClassBuilder remove(String styleClass);

    /**
     * @param styleClassBuilder to be removed from the styleClass string. If it is
     *                          null or empty, it will be ignored.
     * @return the resulting {@link StyleClassBuilder} instance. If it is null or
     * empty, it will be ignored. The same for duplicate entries.
     */
    StyleClassBuilder remove(StyleClassBuilder styleClassBuilder);

    /**
     * @param styleClassProvider to be removed from the styleClass string. If it is
     *                           null or empty, it will be ignored.
     * @return the resulting {@link StyleClassBuilder} instance. If it is null or
     * empty, it will be ignored. The same for duplicate entries.
     */
    StyleClassBuilder remove(StyleClassProvider styleClassProvider);

    /**
     * Analogous to jQuery.toggleClass(): If a class is present, remove it.
     * If not, add it.
     *
     * @param styleClass to be toggled within the styleClass string.
     *                   If it is null or empty, it will be ignored.
     * @return the resulting {@link StyleClassBuilder} instance
     */
    StyleClassBuilder toggle(String styleClass);

    /**
     * Analogous to jQuery.toggleClass(): If a class is present, remove it.
     * If not, add it.
     *
     * @param styleClassBuilder to be toggled within the styleClass string.
     *                          If it is null or empty, it will be ignored.
     * @return the resulting {@link StyleClassBuilder} instance. If it is null or
     * empty, it will be ignored. The same for duplicate entries.
     */
    StyleClassBuilder toggle(StyleClassBuilder styleClassBuilder);

    /**
     * Analogous to jQuery.toggleClass(): If a class is present, remove it.
     * If not, add it.
     *
     * @param styleClassProvider to be toggled within the styleClass string.
     *                           If it is null or empty, it will be ignored.
     * @return the resulting {@link StyleClassBuilder} instance. If it is null or
     * empty, it will be ignored. The same for duplicate entries.
     */
    StyleClassBuilder toggle(StyleClassProvider styleClassProvider);
}
