package com.icw.ehf.cui.core.api.components.util;

import com.icw.ehf.cui.core.api.components.base.CuiComponentBase;

@SuppressWarnings("javadoc")
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
