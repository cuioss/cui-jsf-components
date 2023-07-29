package de.cuioss.jsf.api.components.css;

import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Models the supported IconLibraries. This is necessary, because icons are
 * usually referenced as two classes, e.g. "cui-icon cui-icon-warning". This
 * separation is because css performance, but complicates the usage.
 * <p>
 * In order to work this class returns the corresponding base class, e.g if the
 * provided class is "cui-icon-warning" it returns "cui-icon".
 * </p>
 * <p>
 * In order to prevent improper usage the matching is restricted to the
 * prefixes: "cui-icon, cui-mime-type, ui-icon-". If none of them is matched the
 * access throws an {@link IllegalArgumentException}.
 *
 * @author Oliver Wolff (Oliver Wolff)
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum IconLibrary {

    /**
     * Default cui icon namespace
     */
    CUI("cui-icon"),

    /**
     * MimeType icon namespace
     */
    CUI_MIME_TYPE("cui-mime-type"),

    /**
     * JQuery icon namespace
     */
    JQUERY_UI("ui-icon");

    @Getter
    private final String libraryPrefix;

    /**
     * Strips and matches the corresponding library name.
     *
     * @param iconClass must not be null
     * @return the resolved library, if there is a match, otherwise it throws an
     *         {@link IllegalArgumentException}
     * @throws IllegalArgumentException if iconClass is {@code null} or
     *                                  {@code empty}.
     */
    public static final String resolveLibraryFromIconClass(String iconClass) {

        final var checked = requireNotEmpty(iconClass, "iconClass");

        for (IconLibrary library : IconLibrary.values()) {
            if (checked.startsWith(library.libraryPrefix)) {
                return library.libraryPrefix;
            }
        }
        throw new IllegalArgumentException("No valid library found for " + checked);
    }

    /**
     * Strip the corresponding library class and creates the combined css String,
     * e.g. "pxs-icon pxs-icon-warning" for for iconClass=pxs-icon-warning.
     *
     * @param iconClass must not be null
     * @return the computed css String.
     */
    public static final String resolveCssString(String iconClass) {
        var libraryName = resolveLibraryFromIconClass(iconClass);
        return new StringBuilder(libraryName).append(' ').append(iconClass).toString();
    }

    /**
     * Provide possibility to verify icon name belongs to known namespaces
     *
     * @param iconName target which should be verified. Must not be {@code null} or
     *                 {@code empty}.
     * @return {@code true} if icon name belongs to known namespaces, {@code false}
     *         otherwise
     * @throws IllegalArgumentException if iconName is {@code null} or
     *                                  {@code empty}.
     */
    public static boolean isIconUsagePossible(final String iconName) {

        final var checked = requireNotEmpty(iconName, "iconClass");

        for (IconLibrary library : IconLibrary.values()) {
            if (checked.startsWith(library.libraryPrefix)) {
                return true;
            }
        }
        return false;
    }
}
