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

import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;

/**
 * <p>
 * Simple interface defining a way to resolve-style-class(es).
 * <p>
 *
 * @author Oliver Wolff
 */
public interface StyleClassResolver {

    /**
     * In contrast to {@link ComponentStyleClassProvider#getStyleClass()} that
     * returns the previously set / configured styleClasss-attribute this method
     * computes a style-class from different sources.
     *
     * @return the resolved (combined) styleclass for a component.
     */
    StyleClassBuilder resolveStyleClass();
}
