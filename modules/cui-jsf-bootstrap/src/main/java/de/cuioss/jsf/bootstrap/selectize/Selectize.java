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
package de.cuioss.jsf.bootstrap.selectize;

import java.io.Serializable;
import java.util.Map;

import jakarta.faces.component.UIComponent;

import de.cuioss.jsf.api.components.javascript.ComponentWrapperJQuerySelector;
import de.cuioss.jsf.api.components.javascript.ComponentWrapperJQuerySelector.ComponentWrapperJQuerySelectorBuilder;
import de.cuioss.jsf.api.components.javascript.JavaScriptOptions;
import de.cuioss.jsf.api.components.javascript.JavaScriptOptions.JavaScriptOptionsBuilder;
import de.cuioss.jsf.api.components.javascript.NotQuotableWrapper;
import de.cuioss.jsf.api.components.javascript.ScriptProvider;
import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.uimodel.model.code.CodeType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Helper class for creating java-script configuration for the selectize
 * JavaScript
 *
 * @author Oliver Wolff
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Selectize implements ScriptProvider {

    /**
     * The javaScript to be called for enabling the selectize plugin.
     */
    public static final String JS_TEMPLATE = "%s.selectize(%s);";

    /**
     * "create": Allows the user to create a new items that aren't in the list of
     * options. This option can be any of the following: "true", "false" (disabled),
     * or a function that accepts two arguments: "input" and "callback". The
     * callback should be invoked with the final data for the option.
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
     * String identifier used for identifying {@link CodeType} that are user
     * created, and must therefore handled separately. The user-input will prefixed
     * with that String: "_client_created_"
     */
    public static final String CLIENT_CREATED_SUFFIX = "_client_created_";

    /** "function(input) {return {value: input,label: input}" */
    public static final String DEFAULT_TAG_CREATE_METHOD = "function(input) {return {value: '" + CLIENT_CREATED_SUFFIX
            + "' + input,label: input}}";

    /**
     * NotQuotableWrapper variant of {@link #DEFAULT_TAG_CREATE_METHOD} to be used
     * directly.
     */
    public static final NotQuotableWrapper DEFAULT_TAG_CREATE_METHOD_WRAPPER = new NotQuotableWrapper(
            DEFAULT_TAG_CREATE_METHOD);

    private final ComponentWrapperJQuerySelector jQuerySelector;
    private final JavaScriptOptions options;

    @Override
    public String script() {
        return JS_TEMPLATE.formatted(jQuerySelector.script(), options.script());
    }

    /**
     * @author Oliver Wolff
     *
     */
    public static class SelectizeBuilder {

        private final JavaScriptOptionsBuilder optionsBuilder;

        private final ComponentWrapperJQuerySelectorBuilder querySelectorBuilder;

        SelectizeBuilder() {
            querySelectorBuilder = ComponentWrapperJQuerySelector.builder();
            optionsBuilder = JavaScriptOptions.builder();
        }

        /**
         * @param componentWrapper
         * @return an instance of {@link SelectizeBuilder}
         */
        public SelectizeBuilder withComponentWrapper(ComponentWrapper<? extends UIComponent> componentWrapper) {
            querySelectorBuilder.withComponentWrapper(componentWrapper);
            return this;
        }

        /**
         * @param idExtension if not null it will be appended to the derived ClientId.
         *                    In addition there will be an underscore appended: The
         *                    result will be component.getClientId() + "_" + idExtension
         * @return an instance of {@link SelectizeBuilder}
         */
        public SelectizeBuilder withIdExtension(String idExtension) {
            querySelectorBuilder.withIdExtension(idExtension);
            return this;
        }

        /**
         * @param options
         * @return instance of {@link SelectizeBuilder}
         */
        public SelectizeBuilder withOptions(Map<String, Serializable> options) {
            optionsBuilder.withOptions(options);
            return this;
        }

        /**
         * @param key   identifying the key of an entry, must no be null
         * @param value the concrete value of an option, may be null.
         * @return instance of {@link SelectizeBuilder}
         */
        public SelectizeBuilder withOption(String key, Serializable value) {
            optionsBuilder.withOption(key, value);
            return this;
        }

        /**
         * @return a build {@link Selectize}
         */
        public Selectize build() {
            return new Selectize(querySelectorBuilder.build(), optionsBuilder.build());
        }
    }

    /**
     * @return a new instance of {@link SelectizeBuilder}
     */
    public static SelectizeBuilder builder() {
        return new SelectizeBuilder();
    }

}
