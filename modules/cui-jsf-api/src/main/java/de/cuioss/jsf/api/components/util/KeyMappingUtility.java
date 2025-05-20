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
 * <p>This utility class provides methods for creating extended key strings by 
 * combining a base key with an optional extension. This is particularly useful in JSF
 * components that need to generate unique identifiers based on a primary key with
 * potential variations.</p>
 * 
 * <p>The primary use case is to create context-specific keys by appending a suffix to a 
 * base key, separated by an underscore. For example, combining a component ID with 
 * a specific state or variant identifier.</p>
 * 
 * <p>Usage example:</p>
 * <pre>
 * String baseKey = "userPanel";
 * String extensionKey = "collapsed";
 * String mappedKey = KeyMappingUtility.mapKeyWithExtension(baseKey, extensionKey);
 * // Result: "userPanel_collapsed"
 * </pre>
 *
 * @author Oliver Wolff
 */
public final class KeyMappingUtility {

    /**
     * <p>Creates a composite key string by combining a base key with an optional extension.
     * If the extension is provided, it will be appended to the base key, separated by an
     * underscore character ('_').</p>
     * 
     * <p>This method is typically used for generating unique identifiers for components
     * that may have multiple states or variants, ensuring each has a distinct key.</p>
     *
     * @param key       The base key to use, must not be null and should implement 
     *                  toString() properly to provide a meaningful string representation.
     * @param extension An optional extension string to append to the key. If null or empty,
     *                  only the base key's string representation will be returned.
     * @return If extension parameter is null or empty, returns key.toString().
     *         Otherwise, returns key.toString() + "_" + extension.
     * @throws NullPointerException if the key parameter is null
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
     * Private constructor to prevent instantiation of this utility class.
     */
    private KeyMappingUtility() {
        // Enforce utility class pattern
    }
}
