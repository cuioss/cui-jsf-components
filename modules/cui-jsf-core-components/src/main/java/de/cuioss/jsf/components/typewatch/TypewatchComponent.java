package de.cuioss.jsf.components.typewatch;

import java.util.Map;

import javax.el.MethodExpression;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;

import de.cuioss.jsf.api.components.decorator.AbstractParentDecorator;
import de.cuioss.jsf.api.components.partial.AjaxProvider;
import de.cuioss.jsf.api.components.support.OneTimeCheck;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.components.CuiFamily;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * <p>
 * Decorator to determine when a user has stopped typing in a text field and executing an action.
 * </p>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;cui:typewatch action="doAction();" /&gt;
 * </pre>
 *
 * @author Matthias Walliczek
 */
@ResourceDependency(library = "thirdparty.js", name = "jquery.typewatch.js", target = "head")
@ResourceDependency(library = "javascript.enabler", name = "enabler.typewatch.js", target = "head")
@FacesComponent(CuiFamily.TYPEWATCH_COMPONENT)
@ToString(doNotUseGetters = true)
public class TypewatchComponent extends AbstractParentDecorator {

    private static final String LISTENER_KEY = "listener";
    private static final String ALLOW_SUBMIT_KEY = "allowSubmit";
    private static final String WAIT_KEY = "wait";
    private static final String HIGHLIGHT_KEY = "highlight";
    private static final String CAPTURE_LENGTH_KEY = "captureLength";
    private static final String DATA_TYPEWATCH_PREFIX = "data-typewatch-";

    private final OneTimeCheck oneTimeCheck;

    private final CuiState state;

    @Delegate
    private final AjaxProvider ajaxProvider;

    /**
     * Constructor
     */
    public TypewatchComponent() {
        oneTimeCheck = new OneTimeCheck(this);
        state = new CuiState(getStateHelper());
        ajaxProvider = new AjaxProvider(this).ajaxDataPrefix(DATA_TYPEWATCH_PREFIX);
    }

    @Override
    public void decorate(final ComponentModifier parent) {
        if (oneTimeCheck.readAndSetChecked()) {
            return;
        }

        final var ptAttributes = parent.getComponent().getPassThroughAttributes(true);

        ptAttributes.put("data-typewatch", "data-typewatch");

        addPassThroughAttribute(ptAttributes, "allowsubmit", isAllowSubmit());
        addPassThroughAttribute(ptAttributes, "wait", getWait());
        addPassThroughAttribute(ptAttributes, HIGHLIGHT_KEY, isHighlight());
        addPassThroughAttribute(ptAttributes, "capturelength", getCaptureLength());

        ptAttributes.putAll(ajaxProvider.resolveAjaxAttributesAsMap(parent.getComponent()));

        if (!(parent.getComponent() instanceof ClientBehaviorHolder)) {
            throw new IllegalArgumentException(parent.getComponent() + " does not implement ClientBehaviorHolder");
        }

        var ajaxBehavior = new NoScriptAjaxBehavior();
        ajaxBehavior.addAjaxBehaviorListener(event -> {
            var listener = getListener();
            if (null != listener) {
                listener.invoke(event.getFacesContext().getELContext(), null);
            }
        });

        ((ClientBehaviorHolder) parent.getComponent()).addClientBehavior("valueChange", ajaxBehavior);
    }

    private void addPassThroughAttribute(final Map<String, Object> ptAttributes, final String key, final Object value) {
        if (null != value) {
            ptAttributes.put(DATA_TYPEWATCH_PREFIX + key, value);
        }
    }

    @Override
    public String getFamily() {
        return CuiFamily.COMPONENT_FAMILY;
    }

    /**
     * ATTENTION: Evaluation the MethodExpression may already trigger executing the method!
     *
     * @return the listener
     */
    public MethodExpression getListener() {
        return state.get(LISTENER_KEY);
    }

    /**
     * @param listener the listener to set
     */
    public void setListener(final MethodExpression listener) {
        state.put(LISTENER_KEY, listener);
    }

    /**
     * @return allowSubmit as boolean
     */
    public boolean isAllowSubmit() {
        return state.getBoolean(ALLOW_SUBMIT_KEY, false);
    }

    /**
     * @param allowSubmit to be set
     */
    public void setAllowSubmit(final boolean allowSubmit) {
        state.put(ALLOW_SUBMIT_KEY, Boolean.valueOf(allowSubmit));
    }

    /**
     * @return wait as Integer
     */
    public Integer getWait() {
        return state.get(WAIT_KEY);
    }

    /**
     * @param wait to be set
     */
    public void setWait(final Integer wait) {
        state.put(WAIT_KEY, wait);
    }

    /**
     * @return highlight as boolean
     */
    public boolean isHighlight() {
        return state.getBoolean(HIGHLIGHT_KEY, false);
    }

    /**
     * @param highlight to be set
     */
    public void setHighlight(final boolean highlight) {
        state.put(HIGHLIGHT_KEY, Boolean.valueOf(highlight));
    }

    /**
     * @return captureLength as Integer
     */
    public Integer getCaptureLength() {
        return state.get(CAPTURE_LENGTH_KEY);
    }

    /**
     * @param captureLength to be set
     */
    public void setCaptureLength(final Integer captureLength) {
        state.put(CAPTURE_LENGTH_KEY, captureLength);
    }

}
