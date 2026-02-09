/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.components.selection;

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.converter.AbstractConverter;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>A JSF converter implementation that uses a map-based approach for converting between
 * object models and their string representations. This converter is particularly useful
 * for dropdown elements and other selection components where a fixed set of values
 * needs to be converted between client and server.</p>
 * 
 * <p>The converter maintains an internal map (instanceMap) that associates serializable
 * keys with their corresponding model objects. The keys must be {@link Serializable}
 * because they are transmitted to the client. The {@link #toString()} method of the key
 * must return a consistent string representation that can be used for lookups within the map.</p>
 * 
 * <p>By default, the converter operates in "restricted mode" where it throws a
 * {@link ConverterException} if asked to convert a value that is not found in the instanceMap.
 * This behavior can be changed by setting the {@code restrictedModeActive} property to false,
 * in which case the converter will return null for object conversion or an empty string
 * for string conversion when the value is not found.</p>
 * 
 * <h2>Usage Example</h2>
 * 
 * <pre>
 * MapInstanceConverter&lt;String, User&gt; converter = new MapInstanceConverter&lt;&gt;();
 * Map&lt;String, User&gt; userMap = new HashMap&lt;&gt;();
 * userMap.put("user1", new User("John Doe"));
 * userMap.put("user2", new User("Jane Smith"));
 * converter.setInstanceMap(userMap);
 * 
 * // In JSF component
 * &lt;h:selectOneMenu value="#{bean.selectedUser}" converter="#{converter}"&gt;
 *   &lt;f:selectItems value="#{bean.users}" /&gt;
 * &lt;/h:selectOneMenu&gt;
 * </pre>
 *
 * @param <K> The key type used in the instance map, must be {@link Serializable}
 * @param <T> The object type to convert to/from, must be {@link Serializable}
 * @author Oliver Wolff
 * @author Eugen Fischer
 * @since 1.0
 */
@ToString
public class MapInstanceConverter<K extends Serializable, T extends Serializable> extends AbstractConverter<T>
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 2920782351086654176L;

    private static final String ERROR_MESSAGE_CANNOT_MAP = "message.error.converter.mapinstance.cannotmap";

    /**
     * The map used for the two-way conversion between keys and object values.
     * Keys are sent to the client, values are kept on the server.
     */
    private HashMap<K, T> instanceMap;

    /**
     * Controls the converter's behavior when a value is not found in the instance map.
     * If true (default), the converter throws a {@link ConverterException} when a value
     * cannot be mapped. If false, the converter returns null for getAsObject and an
     * empty string for getAsString.
     */
    @Setter
    private boolean restrictedModeActive = true;

    /**
     * {@inheritDoc}
     * 
     * <p>Converts a string value from the client to its corresponding object representation
     * by looking up the key in the instance map.</p>
     * 
     * @param context The FacesContext for the current request
     * @param component The UIComponent this converter is being used with
     * @param value The string value to be converted, representing a key in the instance map
     * @return The object associated with the key, or null if not found and restricted mode is disabled
     * @throws ConverterException if the value cannot be found in the instance map and restricted mode is enabled
     * @throws NullPointerException if the instance map has not been initialized
     */
    @Override
    protected T convertToObject(final FacesContext context, final UIComponent component, final String value) {

        final var isInMap = getInstanceMap().containsKey(value);

        if (!isInMap) {
            if (!restrictedModeActive) {
                return null;
            }
            throwConverterException(ERROR_MESSAGE_CANNOT_MAP, value, instanceMap);
        }

        return instanceMap.get(value);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Converts an object value to its string representation by finding the key
     * associated with the object in the instance map.</p>
     * 
     * @param context The FacesContext for the current request
     * @param component The UIComponent this converter is being used with
     * @param value The object value to be converted
     * @return The string representation (key) of the object, or empty string if not found and restricted mode is disabled
     * @throws ConverterException if the object cannot be found in the instance map and restricted mode is enabled
     * @throws NullPointerException if the instance map has not been initialized
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final T value) {

        final var isInMap = getInstanceMap().containsValue(value);

        if (!isInMap) {
            if (!restrictedModeActive) {
                return "";
            }
            throwConverterException(ERROR_MESSAGE_CANNOT_MAP, value, instanceMap);
        }

        String found = null;

        for (final Entry<K, T> entry : instanceMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                found = entry.getKey().toString();
                break;
            }
        }

        return found;
    }

    /**
     * Sets the instance map used for conversion between keys and object values.
     * The map is copied to an internal HashMap to avoid external modifications.
     *
     * @param instanceMap The map containing the key-value pairs for conversion.
     *                   Must not be null.
     * @throws NullPointerException if instanceMap is null
     */
    public void setInstanceMap(final Map<K, T> instanceMap) {
        this.instanceMap = new HashMap<>(requireNonNull(instanceMap, "instanceMap must not be null."));
    }

    /**
     * Returns the instance map used for conversion.
     * This is an internal method that verifies the map is not null before use.
     *
     * @return The instance map used for conversion
     * @throws NullPointerException if instanceMap has not been initialized
     */
    private Map<K, T> getInstanceMap() {
        return requireNonNull(instanceMap, "instanceMap must not be null.");
    }

}
