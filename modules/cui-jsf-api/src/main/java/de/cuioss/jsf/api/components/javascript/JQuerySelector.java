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

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.Splitter;

/**
 * Provides a simple way to create a correctly escaped JQuery-selector
 *
 * @author Oliver Wolff
 */
public abstract class JQuerySelector implements ScriptProvider {

    /**
     * The javaScript template for creating the jQuery-selector: "jQuery('#%s')".
     */
    public static final String SELECTOR_TEMPLATE = "jQuery('#%s')";

    /**
     * @return the corresponding jQuerySelectorString, e.g for a given component
     *         providing the id "a:b" it returns "jQuery('#a\\\\:b')" saying it
     *         takes care on the proper masking of the clientIds.
     */
    @Override
    public String script() {
        return SELECTOR_TEMPLATE.formatted(escapeClientId(getIdString()));
    }

    protected abstract String getIdString();

    /**
     * Escapes a given id String in order to be used within javascript, e.g for a
     * given component providing the id "a:b" it returns "'a\\\\:b'" saying it takes
     * care on the proper masking of the clientIds.
     *
     * @param idString
     * @return the escaped String
     */
    public static String escapeClientId(final String idString) {
        final Iterable<String> splitted = Splitter.on(':').splitToList(nullToEmpty(idString));
        return Joiner.on("\\\\:").join(splitted);
    }

}
