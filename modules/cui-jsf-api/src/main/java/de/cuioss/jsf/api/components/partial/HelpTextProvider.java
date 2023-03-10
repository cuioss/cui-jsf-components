package de.cuioss.jsf.api.components.partial;

import java.io.Serializable;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.layout.input.HelpTextComponent;
import de.cuioss.tools.string.MoreStrings;
import lombok.NonNull;

/**
 * @Deprecated replaced by {@link HelpTextComponent}
 *
 *             <h2>Summary</h2>
 *             <p>
 *             Implementors of this class manage the state and resolving of the help-message string
 *             of a
 *             component. The implementation relies on the correct use of attribute names, saying
 *             they must
 *             exactly match the accessor methods.
 *             </p>
 *             <h2>helpTextKey</h2>
 *             <p>
 *             The key for looking up the text to be displayed as the help-text. Although this
 *             attribute
 *             is not required the developer must provide either this or #helpTextValue if you want
 *             a content to
 *             be displayed.
 *             </p>
 *             <h2>helpTextValue</h2>
 *             <p>
 *             The Object representing the help-text to be displayed. This is a replacement for
 *             #helpTextKey. If
 *             both are present helpTextValue takes precedence.
 *             </p>
 *             <h2>helpTextConverter</h2>
 *             <p>
 *             The optional converterId to be used in case of helpTextValue is set and needs
 *             conversion.
 *             </p>
 *             <h2>helpTextEscape</h2>
 *             <p>
 *             Indicates whether the help-text is to be escaped on output or not. Default is
 *             <code>true</code>
 *             </p>
 *
 * @author Oliver Wolff
 */
@Deprecated
public class HelpTextProvider {

    /** The key for the {@link StateHelper} */
    private static final String HELP_KEY_KEY = "helpTextKey";

    /** The key for the {@link StateHelper} */
    private static final String HELP_VALUE_KEY = "helpTextValue";

    /** The key for the {@link StateHelper} */
    private static final String HELP_CONVERTER_KEY = "helpTextConverter";

    /** The key for the {@link StateHelper} */
    private static final String HELP_ESACPE_KEY = "helpTextEscape";

    private final ComponentBridge componentBridge;

    private final CuiState state;

    /**
     * @param componentBridge must not be null
     */
    public HelpTextProvider(@NonNull ComponentBridge componentBridge) {
        this.componentBridge = componentBridge;
        state = new CuiState(componentBridge.stateHelper());
    }

    /**
     * @return the helpTextKey
     */
    public String getHelpTextKey() {
        return state.get(HELP_KEY_KEY);
    }

    /**
     * @param helpTextKey the helpTextKey to set
     */
    public void setHelpTextKey(String helpTextKey) {
        state.put(HELP_KEY_KEY, helpTextKey);
    }

    /**
     * @return the helpTextValue
     */
    public Serializable getHelpTextValue() {
        return state.get(HELP_VALUE_KEY);
    }

    /**
     * @param helpTextValue the helpTextValue to set
     */
    public void setHelpTextValue(Serializable helpTextValue) {
        state.put(HELP_VALUE_KEY, helpTextValue);
    }

    /**
     * @return the helpTextConverter
     */
    public Object getHelpTextConverter() {
        return state.get(HELP_CONVERTER_KEY);
    }

    /**
     * @param helpTextConverter the helpTextConverter to set
     */
    public void setHelpTextConverter(Object helpTextConverter) {
        state.put(HELP_CONVERTER_KEY, helpTextConverter);
    }

    /**
     * @return the escape
     */
    public boolean getHelpTextEscape() {
        return state.getBoolean(HELP_ESACPE_KEY, true);
    }

    /**
     * @param helpTextEscape the escape to set
     */
    public void setHelpTextEscape(boolean helpTextEscape) {
        state.put(HELP_ESACPE_KEY, helpTextEscape);
    }

    /**
     * @return boolean indicating whether a helpText is there (set)
     */
    public boolean isHelpTextSet() {
        return null != resolveHelpText();
    }

    /**
     * @return the resolved content if available, otherwise it will return null.
     */
    public String resolveHelpText() {
        final var helpTextValue = getHelpTextValue();
        final var helpTextKey = getHelpTextKey();
        if (helpTextValue == null && MoreStrings.isEmpty(helpTextKey)) {
            return null;
        }
        return LabelResolver.builder().withConverter(getHelpTextConverter())
                .withLabelKey(helpTextKey)
                .withLabelValue(helpTextValue).build().resolve(componentBridge.facesContext());
    }

}
