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
package de.cuioss.jsf.api.components.util;

import static java.util.Objects.requireNonNull;

import de.cuioss.tools.string.MoreStrings;

import java.io.Serializable;

/**
 * Simple mapping helper to introduce key extension-mapping
 *
 * @author Oliver Wolff
 */
public final class KeyMappingUtility {

    /**
     * Creates a key String depending on its input.
     *
     * @param key       must not be null, must implement toString properly.
     * @param extension may be null or empty
     * @return If extension-parameter is null or empty it returns key.toString().
     *         Otherwise it returns key.toString()_extension
     */
    public static String mapKeyWithExtension(Serializable key, String extension) {
        requireNonNull(key);
        var mappedKey = key.toString();
        if (!MoreStrings.isEmpty(extension)) {
            mappedKey = mappedKey + '_' + extension;
        }
        return mappedKey;
    }

    /**
     * Enforce utility class
     */
    private KeyMappingUtility() {

    }
}
