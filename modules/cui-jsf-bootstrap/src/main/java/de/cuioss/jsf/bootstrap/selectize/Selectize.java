/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.selectize;

import de.cuioss.jsf.api.components.javascript.ComponentWrapperJQuerySelector;
import de.cuioss.jsf.api.components.javascript.ComponentWrapperJQuerySelector.ComponentWrapperJQuerySelectorBuilder;
import de.cuioss.jsf.api.components.javascript.JavaScriptOptions;
import de.cuioss.jsf.api.components.javascript.JavaScriptOptions.JavaScriptOptionsBuilder;
import de.cuioss.jsf.api.components.javascript.NotQuotableWrapper;
import de.cuioss.jsf.api.components.javascript.ScriptProvider;
import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.uimodel.model.code.CodeType;
import jakarta.faces.component.UIComponent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * Helper class for integrating Selectize.js library with JSF components.
 * Provides a fluent API for creating JavaScript configuration for customizable 
 * select boxes and tag inputs.
 * 
 * <h3>Features:</h3>
 * <ul>
 *   <li>Fluent builder API for configuration</li>
 *   <li>Support for tag creation, sorting, and plugins</li>
 *   <li>Handling of user-created items</li>
 * </ul>
 * 
 * <h3>Usage Example:</h3>
 * <pre>
 * Selectize selectize = Selectize.builder()
 *     .withComponentWrapper(componentWrapper)
 *     .withOption(Selectize.OPTION_KEY_MAX_ITEMS, 5)
 *     .withOption(Selectize.OPTION_KEY_CREATE, Selectize.DEFAULT_TAG_CREATE_METHOD_WRAPPER)
 *     .build();
 * 
 * String script = selectize.script();
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Selectize implements ScriptProvider {

    /**
     * The javaScript template to be called for enabling the selectize plugin.
     * The format parameters are:
     * <ol>
     *   <li>The jQuery selector for the target component</li>
     *   <li>The JSON configuration options object</li>
     * </ol>
     */
    public static final String JS_TEMPLATE = "%s.selectize(%s);";

    /**
     * "create": Allows the user to create new items that aren't in the list of options.
     * <p>
     * This option can be any of the following:
     * <ul>
     *   <li>"true" - Enable item creation</li>
     *   <li>"false" - Disable item creation</li>
     *   <li>A function that accepts two arguments: "input" and "callback". 
     *       The callback should be invoked with the final data for the option</li>
     * </ul>
     */
    public static final String OPTION_KEY_CREATE = "create";

    /**
     * "options": An array of the initial available options. By default this is
     * populated from the original input element.
     */
    public static final String OPTION_KEY_OPTIONS = "options";

    /**
     * "labelField": The name of the property to render as an option / item label
     * (not needed when custom rendering functions are defined).
     */
    public static final String OPTION_KEY_LABEL_FIELD = "labelField";

    /**
     * "valueField": The name of the property to use as the "value" when an item is
     * selected.
     */
    public static final String OPTION_KEY_VALUE_FIELD = "valueField";

    /**
     * "sortField": A single field or an array of fields to sort by. Each item in
     * the array should be an object containing at least a "field" property.
     * Optionally, "direction" can be set to "asc" or "desc". The order of the array
     * defines the sort precedence.
     * <p>
     * Unless present, a special "$score" field will be automatically added to the
     * beginning of the sort list. This will make results sorted primarily by match
     * quality (descending).
     * <p>
     * You can override the "$score" function. For more information, see the sifter
     * documentation.
     */
    public static final String OPTION_KEY_SORT_FIELD = "sortField";

    /** "maxItems": The max number of items the user can select. */
    public static final String OPTION_KEY_MAX_ITEMS = "maxItems";

    /**
     * "field": Used in conjunction with {@link #OPTION_KEY_SORT_FIELD}, identifies
     * the field being sorted.
     */
    public static final String OPTION_KEY_FIELD = "field";

    /** "direction" */
    public static final String OPTION_KEY_DIRECTION = "direction";

    /**
     * "persist": If false, items created by the user will not show up as available
     * options once they are unselected.
     */
    public static final String OPTION_KEY_PERSIST = "persist";

    /**
     * "delimiter": The string to separate items by. This option is only used when
     * Selectize is instantiated from a {@code <input type="text">} element.
     */
    public static final String OPTION_KEY_DELIMITER = "delimiter";

    /**
     * The class for the wrapperDiv;
     */
    public static final String OPTION_KEY_WRAPPER_CLASS = "wrapperClass";

    /** "The default css class for the {@link #OPTION_KEY_WRAPPER_CLASS} */
    public static final String OPTION_VALUE_DEFAULT_WRAPPER = "selectize-control";

    /** "plugins" */
    public static final String OPTION_KEY_PLUGINS = "plugins";

    /** "searchField" */
    public static final String OPTION_KEY_SEARCH_FIELD = "searchField";

    /** "label" */
    public static final String OPTION_VALUE_LABEL_KEY = "label";

    /** "remove_button" */
    public static final String OPTION_VALUE_PLUGIN_REMOVE_BUTTON = "remove_button";

    /** "value" */
    public static final String OPTION_VALUE_VALUE_KEY = "value";

    /** "asc" */
    public static final String OPTION_VALUE_SORT_ASC = "asc";

    /** "desc" */
    public static final String OPTION_VALUE_SORT_DESC = "desc";

    /** "{field:'label',direction:'asc'}" */
    public static final String OPTION_VALUE_SORT_BY_LABEL = "{field:'label',direction:'asc'}";

    /**
     * String identifier used for identifying {@link CodeType} instances that are user
     * created, and must therefore be handled separately. The user-input will be prefixed
     * with this String: "_client_created_"
     * <p>
     * This is particularly useful when implementing client-side item creation while maintaining
     * the ability to distinguish between predefined and user-created items on the server side.
     */
    public static final String CLIENT_CREATED_SUFFIX = "_client_created_";

    /**
     * Default create method for tag creation that adds the {@link #CLIENT_CREATED_SUFFIX} 
     * to user-created values to mark them as client-created.
     * <p>
     * The function creates an object with:
     * <ul>
     *   <li>value: The input prefixed with {@link #CLIENT_CREATED_SUFFIX}</li>
     *   <li>label: The original input text</li>
     * </ul>
     */
    public static final String DEFAULT_TAG_CREATE_METHOD = "function(input) {return {value: '" + CLIENT_CREATED_SUFFIX
            + "' + input,label: input}}";

    /**
     * NotQuotableWrapper variant of {@link #DEFAULT_TAG_CREATE_METHOD} ready to be used
     * directly as an option value without additional quotes.
     * <p>
     * This wrapper ensures the JavaScript function is not enclosed in quotes when used as an option value.
     */
    public static final NotQuotableWrapper DEFAULT_TAG_CREATE_METHOD_WRAPPER = new NotQuotableWrapper(
            DEFAULT_TAG_CREATE_METHOD);

    private final ComponentWrapperJQuerySelector jQuerySelector;
    private final JavaScriptOptions options;

    /**
     * {@inheritDoc}
     * <p>
     * Generates the complete Selectize initialization JavaScript by combining the jQuery 
     * selector with the configuration options.
     * 
     * @return The JavaScript code to initialize Selectize on the target element
     */
    @Override
    public String script() {
        return JS_TEMPLATE.formatted(jQuerySelector.script(), options.script());
    }

    /**
     * Builder class for creating {@link Selectize} instances with a fluent API.
     * <p>
     * This builder provides methods to configure all aspects of the Selectize initialization,
     * including the target component and all supported options.
     *
     * @author Oliver Wolff
     * @since 1.0
     */
    public static class SelectizeBuilder {

        private final JavaScriptOptionsBuilder optionsBuilder;

        private final ComponentWrapperJQuerySelectorBuilder querySelectorBuilder;

        SelectizeBuilder() {
            querySelectorBuilder = ComponentWrapperJQuerySelector.builder();
            optionsBuilder = JavaScriptOptions.builder();
        }

        /**
         * Sets the component wrapper to identify the target JSF component.
         *
         * @param componentWrapper The wrapper containing the JSF component to apply Selectize to.
         *                         Must not be null.
         * @return The current builder instance for method chaining
         */
        public SelectizeBuilder withComponentWrapper(ComponentWrapper<? extends UIComponent> componentWrapper) {
            querySelectorBuilder.withComponentWrapper(componentWrapper);
            return this;
        }

        /**
         * Sets an ID extension to be appended to the component's client ID.
         *
         * @param idExtension If not null, it will be appended to the derived ClientId.
         *                    An underscore will be automatically added between the client ID and extension.
         *                    The resulting selector will target component.getClientId() + "_" + idExtension
         * @return The current builder instance for method chaining
         */
        public SelectizeBuilder withIdExtension(String idExtension) {
            querySelectorBuilder.withIdExtension(idExtension);
            return this;
        }

        /**
         * Adds multiple Selectize options at once using a Map.
         *
         * @param options A map containing option key-value pairs to be added to the Selectize configuration.
         *                Keys should be one of the predefined OPTION_KEY_* constants.
         * @return The current builder instance for method chaining
         */
        public SelectizeBuilder withOptions(Map<String, Serializable> options) {
            optionsBuilder.withOptions(options);
            return this;
        }

        /**
         * Adds a single Selectize option to the configuration.
         *
         * @param key   The option key, typically one of the predefined OPTION_KEY_* constants. Must not be null.
         * @param value The option value, which may be null, a primitive, a String, or a {@link NotQuotableWrapper}
         *              for JavaScript functions or other non-quotable values.
         * @return The current builder instance for method chaining
         */
        public SelectizeBuilder withOption(String key, Serializable value) {
            optionsBuilder.withOption(key, value);
            return this;
        }

        /**
         * Builds and returns the configured {@link Selectize} instance.
         *
         * @return A fully configured {@link Selectize} instance ready to generate JavaScript
         */
        public Selectize build() {
            return new Selectize(querySelectorBuilder.build(), optionsBuilder.build());
        }
    }

    /**
     * Creates a new {@link SelectizeBuilder} to begin constructing a Selectize configuration.
     *
     * @return A new builder instance for creating Selectize configurations
     */
    public static SelectizeBuilder builder() {
        return new SelectizeBuilder();
    }
}
