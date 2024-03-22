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
 * Models the supported IconLibraries.
 * This is necessary because icons are
 * usually referenced as two classes, e.g. "cui-icon cui-icon-warning".
 * This separation is because of css performance, but complicates the usage.
 * <p>
 * In order to work this class returns the corresponding base class, e.g if the
 * provided class is "cui-icon-warning" it returns "cui-icon".
 * </p>
 * <p>
 * In order to prevent improper usage, the matching is restricted to the
 * prefixes: "cui-icon, cui-mime-type, ui-icon-".
 * If none of them is matched the
 * access throws an {@link IllegalArgumentException}.
 *
 * @author Oliver Wolff (Oliver Wolff)
 */
@Getter
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

    private final String libraryPrefix;

    /**
     * Strips and matches the corresponding library name.
     *
     * @param iconClass must not be null
     * @return the resolved library, if there is a match, otherwise it throws an
     * {@link IllegalArgumentException}
     * @throws IllegalArgumentException if iconClass is {@code null} or
     *                                  {@code empty}.
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
     * Strip the corresponding library class and creates the combined css String,
     * e.g. "pxs-icon pxs-icon-warning" for iconClass=pxs-icon-warning.
     *
     * @param iconClass must not be null
     * @return the computed css String.
     */
    public static String resolveCssString(String iconClass) {
        var libraryName = resolveLibraryFromIconClass(iconClass);
        return libraryName + ' ' + iconClass;
    }

    /**
     * Provide possibility to verify icon name belongs to known namespaces
     *
     * @param iconName target which should be verified. Must not be {@code null} or
     *                 {@code empty}.
     * @return {@code true} if icon name belongs to known namespaces, {@code false}
     * otherwise
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
