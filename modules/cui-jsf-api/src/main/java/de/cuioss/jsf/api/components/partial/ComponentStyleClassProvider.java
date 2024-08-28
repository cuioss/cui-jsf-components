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
package de.cuioss.jsf.api.components.partial;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the
 * styleClass-attribute. The implementation relies on the correct use of
 * attribute names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>styleClass</h2>
 * <p>
 * Space-separated list of CSS style class(es) to be applied additionally when
 * this element is rendered.
 * </p>
 *
 * @author Oliver Wolff
 */
public interface ComponentStyleClassProvider extends StyleClassProvider {

    /**
     * @param styleClass the styleClass to set.
     *                   See Type-Documentation for details.
     */
    void setStyleClass(String styleClass);

    /**
     * Computes the final / effective styleClass by concatenating the given component-style-class with the one
     * configured by using {@link #getStyleClass()}
     *
     * @param componentSpecificStyleClass The style-classes created by the corresponding renderer,
     *                                    excluding the user-defined style-class,
     */
    String computeAndStoreFinalStyleClass(StyleClassBuilder componentSpecificStyleClass);
}
