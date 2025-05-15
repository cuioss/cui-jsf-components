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
package de.cuioss.jsf.api.components.css;

import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <h2>Icon Library Support</h2>
 * <p>
 * Models the supported icon libraries in the CUI framework. This enum provides abstraction
 * for working with different icon libraries through a consistent API.
 * 
 * <p>
 * Icons are typically referenced as two CSS classes - a base library class and a specific icon class,
 * e.g., "cui-icon cui-icon-warning". This separation improves CSS performance but complicates usage.
 * This class simplifies icon handling by managing the library prefixes and combining them with 
 * specific icon identifiers.
 * 
 * <p>
 * The class supports a restricted set of icon library prefixes:
 * <ul>
 *   <li>cui-icon - Standard CUI icon set</li>
 *   <li>cui-mime-type - MIME type icons</li>
 *   <li>ui-icon - jQuery UI icons</li>
 * </ul>
 * 
 * <p>
 * Usage examples:
 * <pre>
 * // Resolve the library prefix for a full icon class name
 * String libraryPrefix = IconLibrary.resolveLibraryFromIconClass("cui-icon-warning");
 * // Returns: "cui-icon"
 * 
 * // Get the complete CSS class string for an icon
 * String cssClasses = IconLibrary.resolveCssString("cui-icon-warning"); 
 * // Returns: "cui-icon cui-icon-warning"
 * 
 * // Check if an icon name belongs to a supported library
 * boolean isValid = IconLibrary.isIconUsagePossible("cui-icon-warning");
 * // Returns: true
 * </pre>
 * 
 * <p>
 * This enum is thread-safe and can be safely used in concurrent environments.
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum IconLibrary {

    /**
     * Default CUI icon namespace for the standard icon set
     */
    CUI("cui-icon"),

    /**
     * MimeType icon namespace for file type icons
     */
    CUI_MIME_TYPE("cui-mime-type"),

    /**
     * jQuery UI icon namespace for compatibility with jQuery UI components
     */
    JQUERY_UI("ui-icon");

    private final String libraryPrefix;

    /**
     * Resolves the corresponding library prefix from a full icon class name.
     * <p>
     * This method extracts the library prefix from an icon class by matching it against 
     * the known prefixes in this enum.
     * </p>
     *
     * @param iconClass the full icon class name, must not be null or empty
     * @return the resolved library prefix if there is a match
     * @throws IllegalArgumentException if iconClass is {@code null}, {@code empty}, or
     *                                  doesn't match any supported library prefix
     */
    public static String resolveLibraryFromIconClass(String iconClass) {

        final var checked = requireNotEmpty(iconClass, "iconClass");

        for (IconLibrary library : IconLibrary.values()) {
            if (checked.startsWith(library.libraryPrefix)) {
                return library.libraryPrefix;
            }
        }
        throw new IllegalArgumentException("No valid library found for " + checked);
    }

    /**
     * Creates the combined CSS class string for an icon.
     * <p>
     * This method resolves the library prefix and combines it with the specific icon class
     * to create a properly formatted CSS class string that can be directly used in HTML elements.
     * </p>
     * <p>
     * For example, with iconClass="cui-icon-warning", it returns "cui-icon cui-icon-warning".
     * </p>
     *
     * @param iconClass the full icon class name, must not be null or empty
     * @return the combined CSS class string with both library prefix and specific icon class
     * @throws IllegalArgumentException if iconClass is {@code null}, {@code empty}, or
     *                                  doesn't match any supported library prefix
     */
    public static String resolveCssString(String iconClass) {
        var libraryName = resolveLibraryFromIconClass(iconClass);
        return libraryName + ' ' + iconClass;
    }

    /**
     * Verifies whether an icon name belongs to one of the known library namespaces.
     * <p>
     * This method checks if the given icon name starts with any of the supported library
     * prefixes without throwing an exception if no match is found.
     * </p>
     *
     * @param iconName the icon name to verify, must not be {@code null} or {@code empty}
     * @return {@code true} if the icon name belongs to a known namespace, {@code false} otherwise
     * @throws IllegalArgumentException if iconName is {@code null} or {@code empty}
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
