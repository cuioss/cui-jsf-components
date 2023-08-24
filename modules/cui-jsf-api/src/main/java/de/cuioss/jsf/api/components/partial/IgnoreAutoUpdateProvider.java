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
 * Sets the ignoreAutoUpdate flag to ajax calls. When set to true,
 * {@code <p:autoUpdate/>} elements will not be updated.
 */
public class IgnoreAutoUpdateProvider {

    /** The key for the {@link StateHelper} */
    private static final String KEY = "ignoreAutoUpdate";

    private final CuiState state;

    /**
     * @param bridge must not be null
     */
    public IgnoreAutoUpdateProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    /**
     * @return ignoreAutoUpdate
     */
    public boolean isIgnoreAutoUpdate() {
        return state.getBoolean(KEY, false);
    }

    /**
     * @param ignoreAutoUpdate the value to set
     */
    public void setIgnoreAutoUpdate(final boolean ignoreAutoUpdate) {
        state.put(KEY, ignoreAutoUpdate);
    }
}
