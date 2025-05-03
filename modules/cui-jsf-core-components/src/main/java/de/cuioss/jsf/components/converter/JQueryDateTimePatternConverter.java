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
package de.cuioss.jsf.components.converter;

import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.Splitter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Converter provides converting java date time format to jQuery specific one.
 * Currently just remove illegal characters to prevent side effects.
 *
 * @author Eugen Fischer
 * @see <a href="http://api.jqueryui.com/datepicker/#method-option"></a>
 */
@ToString
@EqualsAndHashCode
public class JQueryDateTimePatternConverter implements Serializable {

    @Serial
    private static final long serialVersionUID = -3403131960195316545L;

    private static final List<String> IGNORE = Arrays.asList("(", ")", "Z", "S", "z");

    private final String pattern;

    /**
     * Constructor store pattern which will be used to converted lazy
     *
     * @param patternValue
     */
    public JQueryDateTimePatternConverter(final String patternValue) {
        pattern = patternValue;
    }

    private String transformed;

    /**
     * Convert pattern
     *
     * @return {@code null} if pattern was null, otherwise converted value
     */
    public String getJQueryDateConformTimePattern() {
        if (null == transformed) {
            transformed = convert();
        }
        return transformed;
    }

    private String convert() {

        if (null == pattern) {
            return null;
        }

        final var splitToList = splitByKnownSeparators();
        final List<String> allowed = new ArrayList<>();
        for (final String part : splitToList) {
            if (isSupported(part)) {
                allowed.add(part);
            }
        }
        return Joiner.on(" ").skipNulls().join(allowed).trim();
    }

    private List<String> splitByKnownSeparators() {

        final var cleaned = pattern
                // javascript doesn't like apostrophe
                .replace('\'', ' ')
                // javascript pattern doesn't support milliseconds
                .replace(".S", " ");

        return Splitter.on(" ").trimResults().splitToList(cleaned);
    }

    /**
     * Method is used to filter unsupported single characters and sequences
     *
     * @param value String value to be checked
     * @return true if supported, false if in "black" list
     */
    private static boolean isSupported(final String value) {
        var result = true;
        if (IGNORE.contains(value)) {
            result = false;
        } else {
            for (final String ignoreItem : IGNORE) {
                if (value.contains(ignoreItem)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

}
