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
package de.cuioss.jsf.api.components.partial;

import java.io.Serializable;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.tools.string.MoreStrings;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the placeholder
 * attribute. The implementation relies on the correct user of the attribute
 * names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>placeholderKey</h2>
 * <p>
 * The key for looking up the text for the placeholder pass through attribute.
 * Although this attribute is not required you must provide either this or
 * #placeholderValue if you want a placeholder to be displayed.
 * </p>
 * <h2>placeholderValue</h2>
 * <p>
 * The Object displayed for the placeholder pass through attribute. This is a
 * replacement for #placeholderKey. If both are present placeholderValue takes
 * precedence. This object is usually a String. If not, the developer must
 * ensure that a corresponding converter is either registered for the type or
 * must provide a converter using #placeholderConverter.
 * </p>
 * <h2>placeholderConverter</h2>
 * <p>
 * The optional converterId to be used in case of placeholderValue is set and
 * needs conversion.
 * </p>
 *
 * @author Matthias Walliczek
 */
public class PlaceholderProvider {

    /** The key for the {@link StateHelper} */
    private static final String PLACEHOLDER_KEY_KEY = "placeholderKey";

    /** The key for the {@link StateHelper} */
    private static final String PLACEHOLDER_VALUE_KEY = "placeholderValue";

    /** The key for the {@link StateHelper} */
    private static final String PLACEHOLDER_CONVERTER_KEY = "placeholderConverter";

    /** placeholder */
    public static final String PT_PLACEHOLDER = "placeholder";

    private final ComponentBridge componentBridge;

    private final CuiState state;

    /**
     * @param bridge must not be null
     */
    public PlaceholderProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
        componentBridge = bridge;
    }

    /**
     * @param placeholderKey to be set.
     */
    public void setPlaceholderKey(final String placeholderKey) {
        state.put(PLACEHOLDER_KEY_KEY, placeholderKey);
    }

    /**
     * @return the resolved placeholderKey.
     */
    public String getPlaceholderKey() {
        return state.get(PLACEHOLDER_KEY_KEY);
    }

    /**
     * @param placeholderValue to be set.
     */
    public void setPlaceholderValue(final Serializable placeholderValue) {
        state.put(PLACEHOLDER_VALUE_KEY, placeholderValue);
    }

    /**
     * @return the placeholderValue.
     */
    public Serializable getPlaceholderValue() {
        return state.get(PLACEHOLDER_VALUE_KEY);
    }

    /**
     * @param placeholderConverter to be set.
     */
    public void setPlaceholderConverter(final Object placeholderConverter) {
        state.put(PLACEHOLDER_CONVERTER_KEY, placeholderConverter);
    }

    /**
     * @return the placeholderConverter.
     */
    public Object getPlaceholderConverter() {
        return state.get(PLACEHOLDER_CONVERTER_KEY);
    }

    /**
     * @return the resolved title is available, otherwise it will return null.
     */
    public String resolvePlaceholder() {
        final var placeholderValue = getPlaceholderValue();
        final var placeholderKey = getPlaceholderKey();
        if (placeholderValue == null && MoreStrings.isEmpty(placeholderKey)) {
            return null;
        }
        return LabelResolver.builder().withLabelKey(placeholderKey).withStrictMode(false)
                .withLabelValue(placeholderValue).withConverter(getPlaceholderConverter()).build()
                .resolve(componentBridge.facesContext());
    }

    /**
     * Set a placeholder text to an input component.
     *
     * @param forComponent The (child-)component to display the text in.
     * @param facesContext The current FacesContext.
     * @param self         The (parent-)component containing the forComponent. Here
     *                     the watermark will be added as child.
     */
    public void setPlaceholder(final UIComponent forComponent, final FacesContext facesContext,
            final UIComponent self) {
        final var passthrough = forComponent.getPassThroughAttributes();
        final var placeholderText = resolvePlaceholder();
        if (null == placeholderText) {
            passthrough.remove(PT_PLACEHOLDER);
        } else {
            passthrough.put(PT_PLACEHOLDER, placeholderText);
        }
    }
}
