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
package de.cuioss.jsf.api.components.javascript;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.Splitter;

/**
 * <p>Abstract base class that provides a simple way to create correctly escaped jQuery selectors
 * for use in JavaScript code generation.</p>
 * 
 * <p>This class handles the escaping of special characters in JSF client IDs (particularly
 * the colon character ':') to ensure they work properly as jQuery selectors. Concrete implementations
 * only need to provide the raw ID string via the {@link #getIdString()} method.</p>
 * 
 * <p>The resulting jQuery selector follows the format {@code jQuery('#id')} where id is properly
 * escaped. For example, a JSF client ID like "form:component" would become "jQuery('#form\\:component')".</p>
 * 
 * <p>Usage example:</p>
 * <pre>
 * {@code
 * JQuerySelector selector = new JQuerySelector() {
 *     @Override
 *     protected String getIdString() {
 *         return "form:inputField";
 *     }
 * };
 * String jqueryCode = selector.script(); // Returns: jQuery('#form\\:inputField')
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @see ComponentWrapperJQuerySelector
 * @see ScriptProvider
 * @since 1.0
 */
public abstract class JQuerySelector implements ScriptProvider {

    /**
     * The javaScript template for creating the jQuery-selector: "jQuery('#%s')".
     */
    public static final String SELECTOR_TEMPLATE = "jQuery('#%s')";

    /**
     * Generates a correctly formatted and escaped jQuery selector string based on
     * the ID provided by {@link #getIdString()}.
     *
     * @return the corresponding jQuery selector string with proper escaping
     * @see ScriptProvider#script()
     */
    @Override
    public String script() {
        return SELECTOR_TEMPLATE.formatted(escapeClientId(getIdString()));
    }

    /**
     * To be implemented by concrete subclasses to provide the raw ID string
     * that will be used in the jQuery selector.
     *
     * @return the unescaped ID string to be used in the jQuery selector
     */
    protected abstract String getIdString();

    /**
     * Utility method that escapes a given ID string to be used within JavaScript.
     * Specifically handles the colon character in JSF client IDs.
     *
     * @param idString the raw ID string to escape, can be null (will be treated as empty string)
     * @return the escaped string with properly escaped colons for use in JavaScript
     */
    public static String escapeClientId(final String idString) {
        final Iterable<String> splitted = Splitter.on(':').splitToList(nullToEmpty(idString));
        return Joiner.on("\\\\:").join(splitted);
    }
}
