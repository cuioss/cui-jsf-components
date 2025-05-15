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
package de.cuioss.jsf.api.components.javascript;

import de.cuioss.jsf.api.components.util.ComponentWrapper;
import jakarta.faces.component.UIComponent;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * <p>Specialized jQuery selector for wrapped JSF components that handles JSF client ID escaping.</p>
 * 
 * <p>This class creates properly escaped jQuery selectors for JSF components by handling
 * the colon (":") character that is commonly found in JSF client IDs. For a given
 * component providing the id "a:b", it returns "jQuery('#a\\\\:b')" with proper escaping.</p>
 * 
 * <p>The implementation is based on a {@link ComponentWrapper} which provides runtime
 * access to the component's client ID. An optional ID extension can be provided to target
 * specific parts of a component (e.g., to select a specific child element).</p>
 * 
 * <p>Usage example:</p>
 * <pre>
 * {@code
 * // Create a jQuery selector for a component
 * String jQuerySelector = ComponentWrapperJQuerySelector.builder()
 *     .withComponentWrapper(componentWrapper)
 *     .withIdExtension("input")
 *     .build()
 *     .getJQueryString();
 * }
 * </pre>
 * 
 * <p>This class is immutable and thread-safe once constructed.</p>
 *
 * @author Oliver Wolff
 * @see JQuerySelector
 * @see ComponentWrapper
 * @since 1.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ComponentWrapperJQuerySelector extends JQuerySelector {

    /**
     * Runtime access on component specific attributes.
     */
    @NonNull
    private final ComponentWrapper<? extends UIComponent> componentWrapper;

    /**
     * if not null it will be appended to the derived ClientId. In addition there
     * will be an underscore appended: The result will be component.getClientId() +
     * "_" + idExtension
     */
    private final String idExtension;

    @Override
    protected String getIdString() {
        return componentWrapper.getSuffixedClientId(idExtension);
    }

    /**
     * <p>Builder for creating instances of {@link ComponentWrapperJQuerySelector}.</p>
     * 
     * <p>This builder provides a fluent API for constructing jQuery selectors for
     * component wrappers, with optional ID extensions.</p>
     *
     * @author Oliver Wolff
     * @since 1.0
     */
    public static class ComponentWrapperJQuerySelectorBuilder {

        private ComponentWrapper<? extends UIComponent> componentWrapper;
        private String idExtension;

        /**
         * Sets the component wrapper for which to create a jQuery selector.
         *
         * @param componentWrapper the {@link ComponentWrapper} providing access to the JSF component
         * @return this builder instance for method chaining
         */
        public ComponentWrapperJQuerySelectorBuilder withComponentWrapper(
            final ComponentWrapper<? extends UIComponent> componentWrapper) {
            this.componentWrapper = componentWrapper;
            return this;
        }

        /**
         * Sets an optional ID extension to be appended to the component's client ID.
         *
         * @param idExtension if not null, it will be appended to the derived ClientId.
         *                    In addition, there will be an underscore appended: The
         *                    result will be component.getClientId() + "_" + idExtension
         * @return this builder instance for method chaining
         */
        public ComponentWrapperJQuerySelectorBuilder withIdExtension(final String idExtension) {
            this.idExtension = idExtension;
            return this;
        }

        /**
         * Builds and returns a new {@link ComponentWrapperJQuerySelector} instance with the
         * configured properties.
         *
         * @return a new {@link ComponentWrapperJQuerySelector} instance
         * @throws NullPointerException if componentWrapper is null
         */
        public ComponentWrapperJQuerySelector build() {
            return new ComponentWrapperJQuerySelector(componentWrapper, idExtension);
        }
    }

    /**
     * Creates a new builder for {@link ComponentWrapperJQuerySelector} instances.
     *
     * @return a new {@link ComponentWrapperJQuerySelectorBuilder} instance
     */
    public static ComponentWrapperJQuerySelectorBuilder builder() {
        return new ComponentWrapperJQuerySelectorBuilder();
    }

}
