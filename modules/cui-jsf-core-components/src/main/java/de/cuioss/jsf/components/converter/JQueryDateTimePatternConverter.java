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
 * <p>Utility class that converts Java date/time format patterns into jQuery UI Datepicker
 * compatible format patterns. Java's SimpleDateFormat and jQuery UI use different format
 * specifiers for date components, requiring conversion between the two systems.</p>
 * 
 * <p>This converter specifically focuses on removing or replacing elements that are 
 * incompatible with jQuery UI Datepicker, such as:</p>
 * <ul>
 *   <li>Parentheses - not supported in jQuery date formats</li>
 *   <li>Apostrophes - can cause issues in JavaScript</li>
 *   <li>Timezone indicators (Z, z) - not supported by jQuery Datepicker</li>
 *   <li>Millisecond format specifier (S) - not supported by jQuery Datepicker</li>
 * </ul>
 * 
 * <p>The converter uses lazy transformation, performing the actual conversion only
 * when {@link #getJQueryDateConformTimePattern()} is called, not during instantiation.</p>
 * 
 * <h2>Usage Example</h2>
 * 
 * <pre>
 * // Java pattern: "yyyy-MM-dd HH:mm:ss.SSS Z"
 * String javaPattern = "yyyy-MM-dd HH:mm:ss.SSS Z";
 * JQueryDateTimePatternConverter converter = new JQueryDateTimePatternConverter(javaPattern);
 * String jQueryPattern = converter.getJQueryDateConformTimePattern();
 * // Result: "yyyy-MM-dd HH:mm:ss"
 * </pre>
 * 
 * <p>This class is immutable and thread-safe. The transformation result is cached
 * after the first call to {@link #getJQueryDateConformTimePattern()}.</p>
 *
 * @author Eugen Fischer
 * @see <a href="http://api.jqueryui.com/datepicker/#utility-formatDate">jQuery UI Datepicker formatDate</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html">Java SimpleDateFormat</a>
 * @since 1.0
 */
@ToString
@EqualsAndHashCode
public class JQueryDateTimePatternConverter implements Serializable {

    @Serial
    private static final long serialVersionUID = -3403131960195316545L;

    /**
     * List of format elements that are not supported by jQuery UI Datepicker
     * and should be removed from the pattern.
     */
    private static final List<String> IGNORE = Arrays.asList("(", ")", "Z", "S", "z");

    /**
     * The original Java date/time pattern to be converted.
     */
    private final String pattern;

    /**
     * Stores the converted pattern (lazy initialized).
     */
    private String transformed;

    /**
     * Creates a new instance with the specified Java date/time pattern.
     * The actual conversion is performed lazily when {@link #getJQueryDateConformTimePattern()}
     * is called.
     *
     * @param patternValue The Java date/time pattern to be converted, may be null
     */
    public JQueryDateTimePatternConverter(final String patternValue) {
        pattern = patternValue;
    }

    /**
     * Returns the jQuery UI Datepicker compatible version of the pattern.
     * The conversion result is cached after the first call to this method.
     *
     * @return The converted pattern, or {@code null} if the original pattern was null
     */
    public String getJQueryDateConformTimePattern() {
        if (null == transformed) {
            transformed = convert();
        }
        return transformed;
    }

    /**
     * Performs the actual conversion from Java date/time pattern to jQuery UI compatible pattern.
     * This method:
     * <ol>
     *   <li>Handles null input (returns null)</li>
     *   <li>Splits the pattern by separators</li>
     *   <li>Filters out unsupported format elements</li>
     *   <li>Joins the remaining elements with spaces</li>
     * </ol>
     *
     * @return The converted pattern, or null if input pattern was null
     */
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

    /**
     * Splits the pattern by spaces after cleaning up special characters like apostrophes
     * and millisecond format specifiers that are not supported by jQuery UI Datepicker.
     *
     * @return A list of pattern components after splitting and cleaning
     */
    private List<String> splitByKnownSeparators() {

        final var cleaned = pattern
                // javascript doesn't like apostrophe
                .replace('\'', ' ')
                // javascript pattern doesn't support milliseconds
                .replace(".S", " ");

        return Splitter.on(" ").trimResults().splitToList(cleaned);
    }

    /**
     * Determines if a pattern element is supported by jQuery UI Datepicker.
     * Elements that contain any of the characters in the {@link #IGNORE} list
     * are considered unsupported.
     *
     * @param value String value to be checked
     * @return true if supported, false if the element contains any unsupported characters
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
