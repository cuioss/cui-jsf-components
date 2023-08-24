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
 * Default implementation for dealing with the styleClass attribute.
 *
 * @author Oliver Wolff
 *
 */
public class StyleAttributeProviderImpl implements StyleAttributeProvider {

    /** The key for the {@link StateHelper} */
    public static final String KEY = "style";

    private final CuiState state;

    /**
     * @param bridge
     */
    public StyleAttributeProviderImpl(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    @Override
    public void setStyle(String style) {
        state.put(KEY, style);
    }

    @Override
    public String getStyle() {
        return state.get(KEY);
    }

}
