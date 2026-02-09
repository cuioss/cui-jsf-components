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
package de.cuioss.jsf.api.components.partial;

import de.cuioss.jsf.api.components.util.CuiState;
import jakarta.faces.component.StateHelper;
import lombok.NonNull;

import java.io.Serializable;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of any
 * {@link Serializable} model class. The implementation relies on the correct
 * use of attribute names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>model</h2>
 * <p>
 * The model to be attached to the component. This can be any object that
 * implements {@link Serializable}.
 * </p>
 *
 * @author Oliver Wolff
 */
public class ModelProvider {

    /** The key for the {@link StateHelper} */
    public static final String KEY = "model";

    private final CuiState state;

    /**
     * @param bridge
     */
    public ModelProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    /**
     * @param model to be set.
     */
    public void setModel(Serializable model) {
        state.put(KEY, model);

    }

    /**
     * @return the previously set model or null if none has been set.
     */
    public Serializable getModel() {
        return state.get(KEY);
    }

}
