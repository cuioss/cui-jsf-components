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

import de.cuioss.jsf.api.components.util.CuiState;
import jakarta.faces.component.StateHelper;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Defines the deferred loading provider. Implementors of this class manage the
 * state and resolving of the 'deferred' boolean of a component.
 * </p>
 *
 * @author Sven Haag
 */
public class DeferredProvider {

    /** The key for the {@link StateHelper} */
    private static final String KEY = "deferred";

    private final CuiState state;

    /**
     * @param bridge must not be null
     */
    public DeferredProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    /**
     * @return deferred
     */
    public boolean isDeferred() {
        return state.getBoolean(KEY, false);
    }

    /**
     * @param deferred the value to set
     */
    public void setDeferred(final boolean deferred) {
        state.put(KEY, deferred);
    }
}
