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

import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import lombok.NonNull;

/**
 * The key-binding for a button / control, aka keyboard shortcut. The
 * key will be bound as onClickHandler. Caution: The implementor must ensure
 * that there is only one button for the same type existent per page, otherwise
 * the behavior is non-deterministic.
 */
public class KeyBindingProvider {

    private static final CuiLogger LOGGER = new CuiLogger(KeyBindingProvider.class);

    /**
     * The key for the {@link StateHelper}
     */
    private static final String KEY_BINDING_KEY = "keyBinding";

    private final CuiState state;

    /**
     * @param bridge must not be null
     */
    public KeyBindingProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    /**
     * @param keyBinding to be set
     */
    public void setKeyBinding(final String keyBinding) {
        state.put(KEY_BINDING_KEY, keyBinding);
    }

    /**
     * @return the keyBinding
     */
    public String getKeyBinding() {
        return state.get(KEY_BINDING_KEY);
    }

    /**
     * If there is a key-binding available, This method will write as a data-attribute with the key {@link AttributeValue#CUI_CLICK_BINDING}
     *
     * @param component to be used for deriving the Pass-Through map from.
     */
    public void writeBindingToPassThroughAttributes(UIComponent component) {
        var binding = getKeyBinding();
        if (MoreStrings.isNotBlank(binding)) {
            LOGGER.trace("Using keyBinding '%s'", binding);
            component.getPassThroughAttributes().put(AttributeValue.CUI_CLICK_BINDING.getContent(), getKeyBinding());
        }
    }
}
