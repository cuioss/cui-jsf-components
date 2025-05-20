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
package de.cuioss.jsf.bootstrap.layout;

import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.bootstrap.layout.input.LabeledContainerComponent;
import de.cuioss.tools.collect.MapBuilder;

import java.util.Map;

/**
 * <p>
 * Defines the available layout modes for Bootstrap-based layout components in the CUI JSF framework.
 * These modes control how components are rendered, particularly affecting their structure and styling.
 * </p>
 * 
 * <h2>Available Modes</h2>
 * <ul>
 *   <li>{@link #PLAIN} - Renders with minimal styling, without form-group classes or column structure</li>
 *   <li>{@link #LABEL_SR_ONLY} - Makes labels screen-reader only (visually hidden but accessible)</li>
 *   <li>{@link #FORMGROUP} - Renders with Bootstrap's form-group class for proper form layout</li>
 *   <li>{@link #COLUMN} - Renders with form-group class plus column attributes for grid-based layouts</li>
 * </ul>
 * 
 * <h2>Usage Context</h2>
 * <p>
 * This enum is primarily used by components like {@link LabeledContainerComponent} to control 
 * their rendering behavior. The default mode is typically {@link #COLUMN} for most form components.
 * </p>
 * 
 * <h2>String Conversion</h2>
 * <p>
 * The class provides bidirectional conversion between enum values and their string representations
 * through the {@link #transform(LayoutMode)} and {@link #transform(String)} methods, allowing for
 * case-insensitive string values in component attributes.
 * </p>
 * 
 * @author Oliver Wolff
 */
public enum LayoutMode {

    /**
     * Render style plain
     */
    PLAIN,

    /**
     * Render style label sr-only
     */
    LABEL_SR_ONLY,

    /**
     * Render style form-group
     */
    FORMGROUP,

    /**
     * Render style form-group + column attributes
     */
    COLUMN;

    private static final Map<LayoutMode, String> ENUM_STRING_STORE = initEnumStringMap();

    private static final Map<String, LayoutMode> STRING_ENUM_MAP = initStringEnumMap();

    /**
     * initialize mapping Enum to String on available constants
     */
    private static Map<LayoutMode, String> initEnumStringMap() {
        final var builder = new MapBuilder<LayoutMode, String>();
        for (final LayoutMode item : LayoutMode.values()) {
            builder.put(item, item.toString().toLowerCase());
        }
        return builder.toImmutableMap();
    }

    /**
     * initialize mapping String to Enum on available constants
     */
    private static Map<String, LayoutMode> initStringEnumMap() {
        final var builder = new MapBuilder<String, LayoutMode>();
        for (final LayoutMode item : LayoutMode.values()) {
            builder.put(item.toString().toLowerCase(), item);
        }
        return builder.toImmutableMap();
    }

    /**
     * Transform enum value to String
     *
     * @param value {@linkplain LayoutMode} must not be null
     * @return string representation for enum value
     * @throws NullPointerException if value is null
     */
    public static String transform(final LayoutMode value) {
        final var key = requireNonNull(value, "LayoutMode must not be null");
        if (ENUM_STRING_STORE.containsKey(key)) {
            return ENUM_STRING_STORE.get(key);
        }
        return COLUMN.toString().toLowerCase();
    }

    /**
     * Transform String value for enum
     *
     * @param value must not be empty or null
     * @return LayoutMode fitting to value if allowed, default
     *         {@linkplain LayoutMode#COLUMN} otherwise
     * @throws NullPointerException if value is empty or null
     */
    public static LayoutMode transform(final String value) {
        final var key = requireNonNull(emptyToNull(value)).toLowerCase();
        if (STRING_ENUM_MAP.containsKey(key)) {
            return STRING_ENUM_MAP.get(key);
        }
        return COLUMN;
    }
}
