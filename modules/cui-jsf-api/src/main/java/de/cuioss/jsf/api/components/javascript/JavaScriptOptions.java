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

import de.cuioss.tools.string.Joiner;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builder for creating JavaScript option objects in JSON-like format.
 * <p>
 * This class helps generate JavaScript options in the format
 * {@code {option1:'value1',option2:'value2'}}. It manages the proper
 * formatting and escaping of values, and handles special cases for values
 * that should not be quoted.
 * 
 * <p>
 * Key features:
 * <ul>
 *   <li>Option values can be strings, numbers, booleans, or raw JavaScript</li>
 *   <li>Support for nested option objects</li>
 *   <li>Automatic handling of quoting and escaping</li>
 *   <li>Fluent builder API for easy construction</li>
 * </ul>
 * 
 * <p>
 * Usage example:
 * <pre>
 * JavaScriptOptions options = new JavaScriptOptions();
 * options.addOption("stringValue", "text")
 *        .addNumericOption("count", 5)
 *        .addBooleanOption("enabled", true)
 *        .addJavaScriptOption("callback", "function() { return true; }");
 * String result = options.asJavaScriptObjectNotation();
 * // Result: {stringValue:'text',count:5,enabled:true,callback:function() { return true; }}
 * </pre>
 * 
 * @author Oliver Wolff
 * @since 1.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class JavaScriptOptions implements ScriptProvider {

    /**
     * Map of option key-value pairs.
     * <p>
     * The keys represent JavaScript property names and the values represent their corresponding values.
     * </p>
     */
    @NonNull
    private final Map<String, Serializable> options;

    /** 
     * Format string for wrapping values in single quotes.
     * <p>
     * Value: {@code "'%s'"}
     * </p>
     */
    public static final String SINGLE_QUOTE_WRAPPER = "'%s'";

    /** 
     * Format string for wrapping content in curly brackets.
     * <p>
     * Value: {@code "{%s}"}
     * </p>
     */
    public static final String CURRLY_BRACKETS_WRAPPER = "{%s}";

    /** 
     * Format string for wrapping content in square brackets.
     * <p>
     * Value: {@code "[%s]"}
     * </p>
     */
    public static final String SQUARE_BRACKETS_WRAPPER = "[%s]";

    /**
     * Flag controlling whether the options are wrapped in curly brackets.
     * <p>
     * When true (the default), the resulting script is wrapped in curly brackets.
     * When false, only the key-value pairs are included without the surrounding brackets.
     * </p>
     */
    private final boolean wrapInBrackets;

    /** 
     * Delimiter for separating option values.
     * <p>
     * Value: {@code ","}
     * </p>
     */
    public static final String OPTION_VALUE_DELIMITER = ",";

    /**
     * Generates the JavaScript options string.
     * <p>
     * The resulting string is formatted as a JavaScript object literal with keys and
     * appropriately quoted values. If the options map is empty, an empty string is returned.
     * </p>
     * <p>
     * Values are automatically wrapped in single quotes unless they are instances of
     * {@link NotQuotableWrapper}, in which case their raw string value is used.
     * </p>
     *
     * @return the JavaScript options string, or an empty string if no options are defined
     */
    @Override
    public String script() {
        if (options.isEmpty()) {
            return "";
        }
        var builder = new StringBuilder();
        if (wrapInBrackets) {
            builder.append('{');
        }
        List<String> entries = new ArrayList<>();
        for (Map.Entry<String, Serializable> entry : options.entrySet()) {
            var entryBuilder = new StringBuilder();
            entryBuilder.append(entry.getKey());
            entryBuilder.append(':');
            if (null != entry.getValue()) {
                var entryValue = entry.getValue();
                if (entryValue instanceof NotQuotableWrapper wrapper) {
                    entryBuilder.append(wrapper.getValue());
                } else {
                    entryBuilder.append(SINGLE_QUOTE_WRAPPER.formatted(entry.getValue()));
                }
            }
            entries.add(entryBuilder.toString());
        }

        builder.append(Joiner.on(',').join(entries));
        if (wrapInBrackets) {
            builder.append('}');
        }
        return builder.toString();
    }

    /**
     * Builder for creating {@link JavaScriptOptions} instances.
     * <p>
     * This builder provides a fluent API for constructing JavaScript option objects
     * with various configuration options.
     * </p>
     *
     * @author Oliver Wolff
     * @since 1.0
     */
    public static class JavaScriptOptionsBuilder {

        /**
         * Map of option key-value pairs being built.
         */
        private final Map<String, Serializable> options;
        
        /**
         * Flag controlling whether the options are wrapped in curly brackets.
         */
        private boolean wrapInBrackets = true;

        /**
         * Default constructor that initializes an empty options map.
         */
        JavaScriptOptionsBuilder() {
            options = new HashMap<>();
        }

        /**
         * Adds a single option key-value pair.
         * <p>
         * The value will be automatically quoted in the resulting JavaScript unless
         * it's wrapped in a {@link NotQuotableWrapper}.
         * </p>
         *
         * @param key identifying the key of an entry, must not be null
         * @param value the concrete value of an option, may be null
         * @return this builder instance for method chaining
         */
        public JavaScriptOptionsBuilder withOption(String key, Serializable value) {
            options.put(key, value);
            return this;
        }

        /**
         * Controls whether the resulting options are wrapped in curly brackets.
         * <p>
         * By default, options are wrapped in curly brackets to form a valid JavaScript object.
         * Setting this to false will produce a comma-separated list of key-value pairs without brackets.
         * </p>
         *
         * @param wrapInBrackets true to wrap the result in curly brackets, false otherwise
         * @return this builder instance for method chaining
         */
        public JavaScriptOptionsBuilder withWrapInBrackets(boolean wrapInBrackets) {
            this.wrapInBrackets = wrapInBrackets;
            return this;
        }

        /**
         * Adds multiple options from a map.
         * <p>
         * All entries from the provided map will be added to the options being built.
         * </p>
         *
         * @param options a map of option key-value pairs to add
         * @return this builder instance for method chaining
         */
        public JavaScriptOptionsBuilder withOptions(Map<String, Serializable> options) {
            this.options.putAll(options);
            return this;
        }

        /**
         * Builds the {@link JavaScriptOptions} instance with the configured options.
         *
         * @return a new immutable JavaScriptOptions instance
         */
        public JavaScriptOptions build() {
            return new JavaScriptOptions(options, wrapInBrackets);
        }
    }

    /**
     * Creates a new builder for constructing {@link JavaScriptOptions} instances.
     *
     * @return a new JavaScriptOptionsBuilder instance
     */
    public static JavaScriptOptionsBuilder builder() {
        return new JavaScriptOptionsBuilder();
    }

    /**
     * Utility method to add an array of quoted string options to an options map.
     * <p>
     * This method creates a JavaScript array of quoted strings and adds it to the options map
     * under the specified key. The array will be formatted as a {@link NotQuotableWrapper} to
     * prevent additional quoting.
     * </p>
     * <p>
     * The resulting option will appear in the format: {@code optionKey:['value1','value2']}
     * </p>
     *
     * @param options the options map to add to, must not be null
     * @param optionKey the key under which to add the array, must not be null
     * @param optionParameter the list of string values to include in the array, must not be null.
     *                        If empty, no option will be added.
     */
    public static void addStringOptions(Map<String, Serializable> options, String optionKey,
            List<String> optionParameter) {
        if (!optionParameter.isEmpty()) {
            List<String> elements = new ArrayList<>();
            for (String name : optionParameter) {
                elements.add(SINGLE_QUOTE_WRAPPER.formatted(name));
            }
            var nameString = SQUARE_BRACKETS_WRAPPER.formatted(Joiner.on(OPTION_VALUE_DELIMITER).join(elements));
            options.put(optionKey, new NotQuotableWrapper(nameString));
        }
    }
}
