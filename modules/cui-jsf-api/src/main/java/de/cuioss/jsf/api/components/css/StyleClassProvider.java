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

import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;

/**
 * Interface for components that can provide CSS style classes.
 * <p>
 * This interface defines a contract for objects that need to expose CSS style class
 * information, such as UI components, style enumerations, or CSS framework wrappers.
 * Implementers of this interface can be used as style sources in various styling
 * contexts throughout the application.
 * </p>
 * <p>
 * The interface also provides a default method to obtain a {@link StyleClassBuilder}
 * initialized with the provider's style class, which enables fluent style class
 * manipulation.
 * </p>
 * <p>
 * Implementation note: Any implementation of this interface should consider
 * thread-safety if the provider might be accessed from multiple threads.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * // Using a StyleClassProvider enum
 * StyleClassProvider provider = ContextState.DANGER;
 * String alertClass = provider.getStyleClass();
 * 
 * // Getting a builder from a provider
 * StyleClassBuilder builder = provider.getStyleClassBuilder();
 * builder.append("additional-class");
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see StyleClassBuilder
 * @see de.cuioss.jsf.api.components.css.ContextState
 */
public interface StyleClassProvider {

    /**
     * Retrieves the CSS style class(es) provided by this object.
     * <p>
     * The returned value represents one or more space-separated CSS classes
     * that can be applied to HTML elements. The returned style class string
     * should be properly formatted according to CSS class naming conventions.
     * </p>
     *
     * @return the CSS style class string, which may be empty but should never be {@code null}
     */
    String getStyleClass();

    /**
     * Creates a new {@link StyleClassBuilder} initialized with this provider's style class.
     * <p>
     * This convenience method allows for easy creation of a builder that can be used
     * to further manipulate the style classes using the fluent builder API. The returned
     * builder is a new instance containing this provider's style class as its initial value.
     * </p>
     *
     * @return a new StyleClassBuilder instance initialized with this provider's style class
     * @see StyleClassBuilder
     */
    default StyleClassBuilder getStyleClassBuilder() {
        return new StyleClassBuilderImpl(getStyleClass());
    }
}
