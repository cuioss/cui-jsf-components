package de.cuioss.jsf.bootstrap.layout;

import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static java.util.Objects.requireNonNull;

import java.util.Map;

import de.cuioss.tools.collect.MapBuilder;

/**
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
     *         {@linkplain LayoutMode#COLUMN}
     *         otherwise
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
