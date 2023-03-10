package de.cuioss.jsf.api.components.partial;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import java.io.Serializable;

import javax.faces.component.StateHelper;

import org.omnifaces.util.State;

import de.cuioss.jsf.api.components.support.LabelResolver;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the title attribute of a close
 * button. The implementation relies on the correct user of the attribute names, saying they must
 * exactly match the accessor methods.
 * </p>
 * <h2>closeButtonTitleKey</h2>
 * <p>
 * The key for looking up the text for the title-attribute. Although this attribute is not required
 * you must provide either this or #closeButtonTitleValue if you want a title to be displayed.
 * </p>
 * <h2>closeButtonTitleValue</h2>
 * <p>
 * The Object displayed for the title-attribute. This is a replacement for #closeButtonTitleKey. If
 * both are present titleValue takes precedence. This object is usually a String. If not, the
 * developer must ensure that a corresponding converter is either registered for the type or must
 * provide a converter using #closeButtonTitleConverter.
 * </p>
 * <h2>closeButtonTitleConverter</h2>
 * <p>
 * The optional converterId to be used in case of closeButtonTitleValue is set and needs conversion.
 * </p>
 *
 * @author Matthias Walliczek
 */
public class CloseButtonTitleProvider {

    /** The key for the {@link StateHelper} */
    private static final String CLOSE_BUTTON_TITLE_KEY_KEY = "closeButtonTitleKey";

    /** The key for the {@link StateHelper} */
    private static final String CLOSE_BUTTON_TITLE_VALUE_KEY = "closeButtonTitleValue";

    /** The key for the {@link StateHelper} */
    private static final String CLOSE_BUTTON_TITLE_CONVERTER_KEY = "closeButtonTitleConverter";

    private final State state;
    private final ComponentBridge componentBridge;

    /**
     * @param componentBridge
     */
    public CloseButtonTitleProvider(@NonNull ComponentBridge componentBridge) {
        this.componentBridge = componentBridge;
        state = new State(componentBridge.stateHelper());
    }

    /**
     * @param closeButtonTitleKey to be set.
     */
    public void setCloseButtonTitleKey(final String closeButtonTitleKey) {
        state.put(CLOSE_BUTTON_TITLE_KEY_KEY, closeButtonTitleKey);
    }

    /**
     * @return the resolved closeButtonTitleKey.
     */
    public String getCloseButtonTitleKey() {
        return state.get(CLOSE_BUTTON_TITLE_KEY_KEY);
    }

    /**
     * @param closeButtonTitelValue to be set.
     */
    public void setCloseButtonTitleValue(final Serializable closeButtonTitelValue) {
        state.put(CLOSE_BUTTON_TITLE_VALUE_KEY, closeButtonTitelValue);
    }

    /**
     * @return the closeButtonTitelValue.
     */
    public Serializable getCloseButtonTitleValue() {
        return state.get(CLOSE_BUTTON_TITLE_VALUE_KEY);
    }

    /**
     * @return the closeButtonTitelConverter
     */
    public Object getCloseButtonTitleConverter() {
        return state.get(CLOSE_BUTTON_TITLE_CONVERTER_KEY);
    }

    /**
     * @param closeButtonTitleConverter the closeButtonTitelConverter to set
     */
    public void setCloseButtonTitleConverter(final Object closeButtonTitleConverter) {
        state.put(CLOSE_BUTTON_TITLE_CONVERTER_KEY, closeButtonTitleConverter);
    }

    /**
     * @return the resolved title is available, otherwise it will return null.
     */
    public String resolveCloseButtonTitle() {
        final var labelValue = getCloseButtonTitleValue();
        final var labelKey = getCloseButtonTitleKey();
        if (labelValue == null && isEmpty(labelKey)) {
            return null;
        }
        return LabelResolver.builder().withLabelKey(labelKey).withStrictMode(false)
                .withConverter(getCloseButtonTitleConverter())
                .withLabelValue(labelValue).build().resolve(componentBridge.facesContext());
    }

    /**
     * @return boolean indicating whether a title is there (set)
     */
    public boolean isCloseButtonTitleSet() {
        return !isEmpty(resolveCloseButtonTitle());
    }
}
