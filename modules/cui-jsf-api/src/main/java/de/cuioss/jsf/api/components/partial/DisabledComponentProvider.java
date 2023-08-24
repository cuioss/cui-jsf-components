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

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the
 * disabled-attribute. The implementation relies on the correct use of attribute
 * names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>disabled</h2>
 * <p>
 * Defines the disable state of a component. Default is {@code false}
 * </p>
 *
 * @author Eugen Fischer
 */
public class DisabledComponentProvider {

    /** The key for the {@link StateHelper} */
    private static final String KEY = "disabled";

    private final CuiState state;

    /**
     * @param bridge must not be null
     */
    public DisabledComponentProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    /**
     * @return the disabled state
     */
    public boolean isDisabled() {
        return state.getBoolean(KEY, false);
    }

    /**
     * @param disabled
     */
    public void setDisabled(final boolean disabled) {
        state.put(KEY, disabled);
    }

}
