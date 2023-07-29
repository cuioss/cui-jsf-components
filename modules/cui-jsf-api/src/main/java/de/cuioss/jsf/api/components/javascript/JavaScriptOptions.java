package de.cuioss.jsf.api.components.javascript;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cuioss.tools.string.Joiner;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Builder for creating arbitrary JavaScript option elements. Options is in
 * essence a map of property and values, usually used for configuring JavaScript
 * methods. The resulting String of {@link #script()} is either empty if the map
 * is empty or in the form {option1:'value1',option2:'value2'}. The quoting of
 * the value is implicitly done by the object. In case you want a certain
 * element not being wrapped into single-quotes Wrap it in an instance of
 * {@link NotQuotableWrapper}. The brackets are part of the method-result. If
 * you do not want the use
 * {@link JavaScriptOptionsBuilder#withWrapInBrackets(boolean)} with
 * <code>false</code>
 *
 * @author Oliver Wolff
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class JavaScriptOptions implements ScriptProvider {

    @NonNull
    private final Map<String, Serializable> options;

    /** Single quote wrapper for values. */
    public static final String SINGLE_QUOTE_WRAPPER = "'%s'";

    /** {%s} */
    public static final String CURRLY_BRACKETS_WRAPPER = "{%s}";

    /** [%s] */
    public static final String SQUARE_BRACKETS_WRAPPER = "[%s]";

    /**
     * Indicates whether to wrap the result in curly brackets, defaults to true
     */
    private final boolean wrapInBrackets;

    /** "," */
    public static final String OPTION_VALUE_DELIMITER = ",";

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
        for (java.util.Map.Entry<String, Serializable> entry : options.entrySet()) {
            var entryBuilder = new StringBuilder();
            entryBuilder.append(entry.getKey());
            entryBuilder.append(':');
            if (null != entry.getValue()) {
                var entryValue = entry.getValue();
                if (entryValue instanceof NotQuotableWrapper) {
                    entryBuilder.append(((NotQuotableWrapper) entryValue).getValue());
                } else {
                    entryBuilder.append(String.format(SINGLE_QUOTE_WRAPPER, entry.getValue()));
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
     * @author Oliver Wolff
     *
     */
    public static class JavaScriptOptionsBuilder {

        private final Map<String, Serializable> options;
        private boolean wrapInBrackets = true;

        JavaScriptOptionsBuilder() {
            options = new HashMap<>();
        }

        /**
         * @param key   identifying the key of an entry, must no be null
         * @param value the concrete value of an option, may be null.
         * @return the instance of {@link JavaScriptOptionsBuilder}
         */
        public JavaScriptOptionsBuilder withOption(String key, Serializable value) {
            options.put(key, value);
            return this;
        }

        /**
         * @param wrapInBrackets Indicates whether to wrap the result in curly brackets,
         *                       defaults to true
         * @return the instance of {@link JavaScriptOptionsBuilder}
         */
        public JavaScriptOptionsBuilder withWrapInBrackets(boolean wrapInBrackets) {
            this.wrapInBrackets = wrapInBrackets;
            return this;
        }

        /**
         * @param options a map of concrete options.
         * @return the instance of {@link JavaScriptOptionsBuilder}
         */
        public JavaScriptOptionsBuilder withOptions(Map<String, Serializable> options) {
            this.options.putAll(options);
            return this;
        }

        /**
         * @return {@link JavaScriptOptions}
         */
        public JavaScriptOptions build() {
            return new JavaScriptOptions(options, wrapInBrackets);
        }
    }

    /**
     * @return a newly created instance of {@link JavaScriptOptionsBuilder}
     */
    public static JavaScriptOptionsBuilder builder() {
        return new JavaScriptOptionsBuilder();
    }

    /**
     * Add a number of quoted String options to the given map with the given key.
     * The option will be wrapped into an instance of {@link NotQuotableWrapper}
     *
     * @param options         must not be null
     * @param optionKey       must not be null or empty
     * @param optionParameter must not be null. If it is empty the element will not
     *                        be added. Each option will be single quoted and
     *                        separated by {@link #OPTION_VALUE_DELIMITER}
     */
    public static void addStringOptions(Map<String, Serializable> options, String optionKey,
            List<String> optionParameter) {
        if (!optionParameter.isEmpty()) {
            List<String> elements = new ArrayList<>();
            for (String name : optionParameter) {
                elements.add(String.format(SINGLE_QUOTE_WRAPPER, name));
            }
            var nameString = String.format(SQUARE_BRACKETS_WRAPPER, Joiner.on(OPTION_VALUE_DELIMITER).join(elements));
            options.put(optionKey, new NotQuotableWrapper(nameString));
        }
    }

}
