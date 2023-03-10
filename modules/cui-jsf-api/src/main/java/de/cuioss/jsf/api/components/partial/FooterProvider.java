package de.cuioss.jsf.api.components.partial;

import java.io.Serializable;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;

import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.tools.string.MoreStrings;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the footer string of a component.
 * The implementation relies on the correct use of attribute names, saying they must exactly match
 * the accessor methods.
 * </p>
 * <h2>footerKey</h2>
 * <p>
 * The key for looking up the text to be displayed as the text footer. Although this attribute is
 * not required you must provide either this or #footerValue if you want a label to be displayed.
 * </p>
 * <h2>footerValue</h2>
 * <p>
 * The Object representing the text to be displayed. This is a replacement for #footerKey. If both
 * are present footerValue takes precedence. The object is usually a string. If not, the developer
 * must ensure that a corresponding converter is either registered for the type or must provide a
 * converter using #footerConverter.
 * </p>
 * <h2>footerConverter</h2>
 * <p>
 * The optional converterId to be used in case of footerValue is set and needs conversion.
 * </p>
 * <h2>footerEscape</h2>
 * <p>
 * Indicates whether the footer is to be escaped on output or not. Default is <code>true</code>
 * </p>
 *
 * @author Sven Haag, Sven Haag
 */
public class FooterProvider {

    /** The key for the {@link StateHelper} */
    private static final String FOOTER_KEY_KEY = "footerKey";

    /** The key for the {@link StateHelper} */
    private static final String FOOTER_VALUE_KEY = "footerValue";

    /** The key for the {@link StateHelper} */
    private static final String FOOTER_CONVERTER_KEY = "footerConverter";

    /** The key for the {@link StateHelper} */
    private static final String FOOTER_ESCAPE_KEY = "footerEscape";

    /** The footer facet name */
    private static final String FOOTER_FACET_NAME = "footer";

    private final ComponentBridge componentBridge;

    private final CuiState state;

    /**
     * @param bridge must not be null
     */
    public FooterProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
        componentBridge = bridge;
    }

    /**
     * @return the footerKey
     */
    public String getFooterKey() {
        return state.get(FOOTER_KEY_KEY);
    }

    /**
     * @param footerKey the footerKey to set
     */
    public void setFooterKey(final String footerKey) {
        state.put(FOOTER_KEY_KEY, footerKey);
    }

    /**
     * @return the footerValue
     */
    public Serializable getFooterValue() {
        return state.get(FOOTER_VALUE_KEY);
    }

    /**
     * @param footerValue the footerValue to set
     */
    public void setFooterValue(final Serializable footerValue) {
        state.put(FOOTER_VALUE_KEY, footerValue);
    }

    /**
     * @return the footerConverter
     */
    public Object getFooterConverter() {
        return state.get(FOOTER_CONVERTER_KEY);
    }

    /**
     * @param footerConverter the footerConverter to set
     */
    public void setFooterConverter(final Object footerConverter) {
        state.put(FOOTER_CONVERTER_KEY, footerConverter);
    }

    /**
     * @return the escape
     */
    public boolean isFooterEscape() {
        return state.getBoolean(FOOTER_ESCAPE_KEY, true);
    }

    /**
     * @param footerEscape the escape to set
     */
    public void setFooterEscape(final boolean footerEscape) {
        state.put(FOOTER_ESCAPE_KEY, footerEscape);
    }

    /**
     * @return the footer facet or null.
     */
    public UIComponent getFooterFacet() {
        return componentBridge.facet(FOOTER_FACET_NAME);
    }

    /**
     * @return the resolved footer if available, otherwise it will return null.
     */
    public String resolveFooter() {
        final var footerValue = getFooterValue();
        final var footerKey = getFooterKey();
        if (footerValue == null && MoreStrings.isEmpty(footerKey)) {
            return null;
        }
        return LabelResolver.builder().withConverter(getFooterConverter())
                .withLabelKey(footerKey).withEscape(isFooterEscape())
                .withLabelValue(footerValue).build().resolve(componentBridge.facesContext());
    }

    /**
     * @return true if a footerValue or footerKey is set.
     */
    public boolean hasFooterTitleSet() {
        return getFooterValue() != null || !MoreStrings.isEmpty(getFooterKey());
    }

    /**
     * @return boolean indicating whether there is a 'footer' facet available <em>and</em> whether
     *         this facet is rendered.
     */
    public boolean shouldRenderFooterFacet() {
        var facet = getFooterFacet();
        return null != facet && facet.isRendered();
    }

    /**
     * @return true, if a footer facet is present.
     */
    public boolean hasFooterFacet() {
        return null != getFooterFacet();
    }

    /**
     * @return true if a footerKey, footerValue or footer facet is present.
     */
    public boolean shouldRenderFooter() {
        return hasFooterTitleSet() || shouldRenderFooterFacet();
    }
}
