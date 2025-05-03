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
package de.cuioss.jsf.jqplot.portal.theme;

import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;
import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;

import de.cuioss.tools.string.Splitter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class represent a single Css rule and provide a simplified api to interact
 * with
 *
 * @author i000576
 */
@ToString
@EqualsAndHashCode(of = "selector")
@RequiredArgsConstructor
public class CssRule implements Serializable {

    @Serial
    private static final long serialVersionUID = -4809127537364135508L;

    @Getter
    private final String selector;

    private final Map<String, String> declarations;

    /**
     * @return properties
     */
    public Set<String> getProperties() {
        return mutableSet(declarations.keySet());
    }

    /**
     * @param propertyName
     * @return value for property name
     */
    public String getPropertyValue(final String propertyName) {
        final var key = emptyToNull(propertyName).toLowerCase();
        return declarations.get(key);
    }

    /**
     * @return CssRule declared property names
     */
    public Set<String> getPropertyNames() {
        return declarations.keySet();
    }

    /**
     * Factory Method create {@linkplain CssRule} from CssText
     *
     * @param cssText {@linkplain String} must not be {@code null}
     * @return {@linkplain CssRule} if possible
     */
    public static CssRule createBy(final String cssText) {

        final var checked = requireNotEmpty(cssText, "CssText");

        // TODO add check for syntax > name { prop1 : val1; ... propN : valN } <

        final var splitToList = Splitter.on("{").trimResults().omitEmptyStrings().splitToList(checked);

        final var cssSelector = splitToList.get(0).trim();

        return new CssRule(cssSelector, parseDeclarations(splitToList.get(1)));
    }

    private static Map<String, String> parseDeclarations(final String tail) {

        final var plain = tail.replace("}", "").trim();

        final var pairs = Splitter.on(";").trimResults().omitEmptyStrings().splitToList(plain);

        final Map<String, String> result = new HashMap<>(pairs.size());

        for (final String pair : pairs) {

            final var splittedPair = Splitter.on(":").trimResults().omitEmptyStrings().splitToList(pair);

            if (splittedPair.size() == 2) {

                final var key = splittedPair.get(0).toLowerCase();
                result.putIfAbsent(key, splittedPair.get(1));

            }
        }

        return result;
    }
}
