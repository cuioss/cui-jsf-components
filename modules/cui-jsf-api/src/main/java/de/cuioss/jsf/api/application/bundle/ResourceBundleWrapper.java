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
package de.cuioss.jsf.api.application.bundle;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Wraps the Bundles managed by cui. It contains a method for accessing the
 * actual message and a technical method for the caching of the underlying
 * {@link ResourceBundle}
 *
 * @author Oliver Wolff
 */
public interface ResourceBundleWrapper extends Serializable {

    /**
     * Returns resolved message by given key.
     *
     * @param key to be looked up
     * @return the resolved message if key is defined, otherwise "??[key value]??"
     *         will be returned and warning will be written
     * @throws MissingResourceException if key is unknown and application is in
     *                                  development mode
     */
    String getMessage(String key);

    /**
     * Technical method for {@link ResourceBundle} caching the resolved keys and
     * values @see {@link ResourceBundle#getKeys()}
     *
     * @return an Enumeration of the keys contained in this ResourceBundle and its
     *         parent bundles.
     */
    default Enumeration<String> getKeys() {
        return Collections.enumeration(keySet());
    }

    /**
     * @return set of all supported keys of this bundle
     */
    Set<String> keySet();

    /**
     * @return a list of all configured bundles to be used for debugging.
     */
    String getBundleContent();

}
