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
package de.cuioss.jsf.components.selection;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;

import de.cuioss.jsf.api.converter.AbstractConverter;
import lombok.Setter;
import lombok.ToString;

/**
 * Converter is based on {@link AbstractConverter} and is utilized in most cased
 * by drop-down elements.
 * <p>
 * The #instanceMap is utilized for the mapping between the {@link Serializable}
 * keys and the corresponding model classes. The keys must be
 * {@link Serializable} because they are sent to the client. The corresponding
 * {@link #toString()} method must return a String representation that can be
 * used for key lookup within the map.
 * </p>
 * <p>
 * The lookup is implemented to fail fast, saying for both ways,
 * {@link #getAsObject(FacesContext, UIComponent, String)} and
 * {@link #getAsString(FacesContext, UIComponent, Object)} the references will
 * be checked, whether they are contained within the #instanceMap, throwing a
 * {@link ConverterException} if it doesn't.
 * </p>
 *
 * @author Oliver Wolff
 * @author Eugen Fischer
 * @param <K> key type must be {@link Serializable}
 * @param <T> object type must be {@link Serializable}
 */
@ToString
public class MapInstanceConverter<K extends Serializable, T extends Serializable> extends AbstractConverter<T>
        implements Serializable {

    private static final long serialVersionUID = 2920782351086654176L;

    private static final String ERROR_MESSAGE_CANNOTMAP = "message.error.converter.mapinstance.cannotmap";

    /** utilized for the two way mapping. */
    private HashMap<K, T> instanceMap;

    /**
     * If rescrictModeActive is active the converter throws
     * {@link IllegalStateException} if on converting the value is not known.
     */
    @Setter
    private boolean rescrictModeActive = true;

    /**
     * @throws {@link IllegalStateException}
     */
    @Override
    protected T convertToObject(final FacesContext context, final UIComponent component, final String value) {

        final var isInMap = getInstanceMap().containsKey(value);

        if (!isInMap) {
            if (!rescrictModeActive) {
                return null;
            }
            checkState(isInMap, ERROR_MESSAGE_CANNOTMAP, value, instanceMap);
        }

        return instanceMap.get(value);
    }

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final T value) {

        final var isInMap = getInstanceMap().containsValue(value);

        if (!isInMap) {
            if (!rescrictModeActive) {
                return "";
            }
            checkState(isInMap, ERROR_MESSAGE_CANNOTMAP, value, instanceMap);
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
     * @param instanceMap the instanceMap to set. Must not be null
     */
    public void setInstanceMap(final Map<K, T> instanceMap) {
        this.instanceMap = new HashMap<>(requireNonNull(instanceMap, "instanceMap must not be null."));
    }

    /**
     * usage of {@link #setInstanceMap(Map)} is not enforced, so instanceMap could
     * be null
     *
     * @return verified instanceMap
     * @throws NullPointerException if instanceMap is null
     */
    private Map<K, T> getInstanceMap() {
        return requireNonNull(instanceMap, "instanceMap must not be null.");
    }

}
