package de.cuioss.jsf.api.components.partial;

import java.io.Serializable;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.tools.string.MoreStrings;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the label string of a component. The
 * implementation relies on the correct use of attribute names, saying they must exactly match the
 * accessor methods.
 * </p>
 * <h2>labelKey</h2>
 * <p>
 * The key for looking up the text to be displayed as the text label. Although this attribute is not
 * required you must provide either this or #labelValue if you want a label to be displayed.
 * </p>
 * <h2>labelValue</h2>
 * <p>
 * The Object representing the text to be displayed. This is a replacement for #labelKey. If both
 * are present labelValue takes precedence. The object is usually a string. If not, the developer
 * must ensure that a corresponding converter is either registered for the type or must provide a
 * converter using #labelConverter.
 * </p>
 * <h2>labelConverter</h2>
 * <p>
 * The optional converterId to be used in case of labelValue is set and needs conversion.
 * </p>
 * <h2>labelEscape</h2>
 * <p>
 * Indicates whether the label is to be escaped on output or not. Default is <code>true</code>
 * </p>
 *
 * @author Oliver Wolff
 */
public class LabelProvider {

    /** The key for the {@link StateHelper} */
    private static final String LABEL_KEY_KEY = "labelKey";

    /** The key for the {@link StateHelper} */
    private static final String LABEL_VALUE_KEY = "labelValue";

    /** The key for the {@link StateHelper} */
    private static final String LABEL_CONVERTER_KEY = "labelConverter";

    /** The key for the {@link StateHelper} */
    private static final String LABEL_ESCAPE_KEY = "labelEscape";

    private final ComponentBridge componentBridge;

    private final CuiState state;

    /**
     * @param bridge must not be null
     */
    public LabelProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
        componentBridge = bridge;
    }

    /**
     * @return the labelKey
     */
    public String getLabelKey() {
        return state.get(LABEL_KEY_KEY);
    }

    /**
     * @param labelKey the labelKey to set
     */
    public void setLabelKey(final String labelKey) {
        state.put(LABEL_KEY_KEY, labelKey);
    }

    /**
     * @return the labelValue
     */
    public Serializable getLabelValue() {
        return state.get(LABEL_VALUE_KEY);
    }

    /**
     * @param labelValue the labelValue to set
     */
    public void setLabelValue(final Serializable labelValue) {
        state.put(LABEL_VALUE_KEY, labelValue);
    }

    /**
     * @return the labelConverter
     */
    public Object getLabelConverter() {
        return state.get(LABEL_CONVERTER_KEY);
    }

    /**
     * @param labelConverter the labelConverter to set
     */
    public void setLabelConverter(final Object labelConverter) {
        state.put(LABEL_CONVERTER_KEY, labelConverter);
    }

    /**
     * @return the escape
     */
    public boolean isLabelEscape() {
        return state.getBoolean(LABEL_ESCAPE_KEY, true);
    }

    /**
     * @param labelEscape the escape to set
     */
    public void setLabelEscape(final boolean labelEscape) {
        state.put(LABEL_ESCAPE_KEY, labelEscape);
    }

    /**
     * @return the resolved label if available, otherwise it will return null.
     */
    public String resolveLabel() {
        final var labelValue = getLabelValue();
        final var labelKey = getLabelKey();
        if (labelValue == null && MoreStrings.isEmpty(labelKey)) {
            return null;
        }
        return LabelResolver.builder().withConverter(getLabelConverter())
                .withLabelKey(labelKey).withEscape(isLabelEscape())
                .withLabelValue(labelValue).build().resolve(componentBridge.facesContext());
    }

}
