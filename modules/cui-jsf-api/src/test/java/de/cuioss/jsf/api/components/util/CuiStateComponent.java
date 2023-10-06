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
package de.cuioss.jsf.api.components.util;

import de.cuioss.jsf.api.components.base.CuiComponentBase;

public class CuiStateComponent extends CuiComponentBase {

    private static final String BOOLEAN_KEY = "booleanKey";
    private static final String INT_KEY = "intKey";
    private final CuiState cuiState;

    public CuiStateComponent() {
        cuiState = new CuiState(getStateHelper());
    }

    public void setBooleanValue(final boolean value) {
        cuiState.put(BOOLEAN_KEY, value);
    }

    public boolean getBooleanValue() {
        return cuiState.getBoolean(BOOLEAN_KEY);
    }

    public boolean getBooleanValue(final boolean defaultValue) {
        return cuiState.getBoolean(BOOLEAN_KEY, defaultValue);
    }

    public void setIntValue(final int value) {
        cuiState.put(INT_KEY, value);
    }

    public int getIntValue() {
        return cuiState.getInt(INT_KEY);
    }

    public int getIntValue(final int defaultValue) {
        return cuiState.getInt(INT_KEY, defaultValue);
    }
}
