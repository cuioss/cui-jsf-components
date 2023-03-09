package com.icw.ehf.cui.components.bootstrap.layout.input.support;

import java.io.Serializable;

import com.icw.ehf.cui.components.bootstrap.layout.input.InputGuardComponent;
import com.icw.ehf.cui.core.api.components.partial.ComponentBridge;
import com.icw.ehf.cui.core.api.components.support.LabelResolver;
import com.icw.ehf.cui.core.api.components.util.CuiState;

import de.cuioss.tools.string.MoreStrings;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * This class manages the state and resolving of the information needed for creating
 * the guard- / unguard Button in context of {@link InputGuardComponent}
 * </p>
 * <h2>guardButtonTitleKey</h2>
 * <p>
 * The key for looking up the text to be displayed as the title. Although this attribute
 * is not required the developer must provide either this or #guardButtonTitleValue if you want a
 * title to be displayed. Defaults to 'cc.unlockableField.unlock.title', resulting in 'Unlock field
 * for editing'
 * </p>
 * <h2>guardButtonTitleValue</h2>
 * <p>
 * The Object representing the title to be displayed. This is a replacement for
 * #guardButtonTitleKey. If both are present guardButtonTitleValue takes precedence.
 * </p>
 * <h2>guardButtonTitleConverter</h2>
 * <p>
 * The optional converterId to be used in case of guardButtonTitleValue is set and needs conversion.
 * </p>
 * <h2>guardButtonTitleEscape</h2>
 * <p>
 * Indicates whether the title is to be escaped on output or not. Default is <code>true</code>.
 * </p>
 *
 * @author Oliver Wolff
 */
public class GuardButtonAttributes {

    /** "cui-icon-unlock". */
    public static final String ICON_DEFAULT = "cui-icon-unlock";
    private static final String KEY_KEY = "guardButtonTitleKey";
    private static final String VALUE_KEY = "guardButtonTitleValue";
    private static final String CONVERTER_KEY = "guardButtonTitleConverter";
    private static final String ESACPE_KEY = "guardButtonTitleEscape";
    private static final String ICON_KEY = "guardIcon";
    private static final String INPUT_GUARD_UNLOCK_DEFAULT_TITLE = "input_guard.unlock.default.title";

    private final ComponentBridge componentBridge;

    private final CuiState state;

    /**
     * @param componentBridge must not be null
     */
    public GuardButtonAttributes(@NonNull ComponentBridge componentBridge) {
        this.componentBridge = componentBridge;
        state = new CuiState(componentBridge.stateHelper());
    }

    /**
     * The icon to be displayed on the button removing the guard and making it editable
     *
     * @return the guardIcon, defaults to 'cui-icon-unlock'
     */
    public String getGuardIcon() {
        return state.get(ICON_KEY, ICON_DEFAULT);
    }

    /**
     * @param guardIcon to be set
     */
    public void setGuardIcon(String guardIcon) {
        state.put(ICON_KEY, guardIcon);
    }

    /**
     * @return the guardButtonTitleKey
     */
    public String getGuardButtonTitleKey() {
        return state.get(KEY_KEY, INPUT_GUARD_UNLOCK_DEFAULT_TITLE);
    }

    /**
     * @param guardButtonTitleKey the guardButtonTitleKey to set
     */
    public void setGuardButtonTitleKey(String guardButtonTitleKey) {
        state.put(KEY_KEY, guardButtonTitleKey);
    }

    /**
     * @return the guardButtonTitleValue
     */
    public Serializable getGuardButtonTitleValue() {
        return state.get(VALUE_KEY);
    }

    /**
     * @param guardButtonTitleValue the guardButtonTitleValue to set
     */
    public void setGuardButtonTitleValue(Serializable guardButtonTitleValue) {
        state.put(VALUE_KEY, guardButtonTitleValue);
    }

    /**
     * @return the guardButtonTitleConverter
     */
    public Object getGuardButtonTitleConverter() {
        return state.get(CONVERTER_KEY);
    }

    /**
     * @param guardButtonTitleConverter the guardButtonTitleConverter to set
     */
    public void setGuardButtonTitleConverter(Object guardButtonTitleConverter) {
        state.put(CONVERTER_KEY, guardButtonTitleConverter);
    }

    /**
     * @return the escape
     */
    public boolean getGuardButtonTitleEscape() {
        return state.getBoolean(ESACPE_KEY, true);
    }

    /**
     * @param guardButtonTitleEscape the escape to set
     */
    public void setGuardButtonTitleEscape(boolean guardButtonTitleEscape) {
        state.put(ESACPE_KEY, guardButtonTitleEscape);
    }

    /**
     * @return the resolved content if available, otherwise it will return null.
     */
    public String resolveGuardButtonTitle() {
        final var titleValue = getGuardButtonTitleValue();
        final var titleKey = getGuardButtonTitleKey();
        if (titleValue == null && MoreStrings.isEmpty(titleKey)) {
            return null;
        }
        return LabelResolver.builder().withConverter(getGuardButtonTitleConverter())
                .withLabelKey(titleKey)
                .withLabelValue(titleValue).build().resolve(componentBridge.facesContext());
    }

}
