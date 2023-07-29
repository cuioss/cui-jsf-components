package de.cuioss.jsf.bootstrap.layout.input;

import static de.cuioss.jsf.api.components.util.ComponentUtility.JAVAX_FACES_SOURCE;
import static de.cuioss.tools.base.BooleanOperations.areAllTrue;
import static de.cuioss.tools.base.BooleanOperations.isAnyFalse;
import static de.cuioss.tools.base.Preconditions.checkState;

import java.util.Optional;

import javax.faces.application.ResourceDependency;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.JsfComponentIdentifier;
import de.cuioss.jsf.api.components.base.BaseCuiHtmlHiddenInputComponent;
import de.cuioss.jsf.api.components.partial.AjaxProvider;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.button.CommandButton;
import de.cuioss.jsf.bootstrap.layout.input.support.GuardButtonAttributes;
import de.cuioss.jsf.bootstrap.layout.input.support.ResetGuardButtonAttributes;
import de.cuioss.tools.logging.CuiLogger;
import lombok.experimental.Delegate;

/**
 * Helper / Decorator component used for guarding input-elements within
 * {@link LabeledContainerComponent}.
 * <p>
 * Renders an unlock button beside its input component. The input component will
 * be disabled per default. It can be unlocked on demand. A warning message is
 * shown right beside if it is unlocked
 * </p>
 *
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link GuardButtonAttributes}</li>
 * <li>{@link ResetGuardButtonAttributes}</li>
 * <li>{@link AjaxProvider}: Default to "@namingcontainer" for update and
 * "@this" for process</li>
 * <li>buttonAlign: The alignment of the button relative to the wrapped input,
 * defaults to 'append'</li>
 * <li>renderButtons: If set to {@code true}, default, the guard/resetGuard
 * buttons are rendered, if set to {@code false} they are not rendered.</li>
 * <li>resetInputValue: If set to {@code true}, default, the clicking of the
 * resestGuard button will result in a {@link EditableValueHolder#resetValue()}
 * on the guarded component</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.GUARDED_INPUT_COMPONENT)
@ResourceDependency(library = "javascript.enabler", name = "enabler.input_guard.js", target = "head")
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class InputGuardComponent extends BaseCuiHtmlHiddenInputComponent implements ContainerPlugin {

    private static final CuiLogger log = new CuiLogger(InputGuardComponent.class);

    /** "data-guarded-input-button". */
    public static final String DATA_GUARDED_BUTTON = "data-input-guard-button";

    /** "data-guarded-input-button". */
    public static final String DATA_GUARDED_INPUT = "data-input-guard-value";

    /** "data-guarded-target-value". */
    public static final String DATA_GUARDED_TARGET = "data-input-guard-target-value";

    /** The fixed id for this component. */
    public static final String FIXED_ID = "guarded_value";

    private static final String BUTTON_ALIGN_KEY = "buttonAlign";

    private static final String RENDER_BUTTONS_KEY = "renderButtons";

    private static final String RESET_VALUE_ON_UNGUARD_KEY = "resetInputValue";

    private static final String CURRENT_VALUE_KEY = "currentValueKey";

    private final CuiState state;

    @Delegate
    private final GuardButtonAttributes guardAttributes;

    @Delegate
    private final ResetGuardButtonAttributes resetGuardButtonAttributes;

    @Delegate
    private final AjaxProvider ajaxProvider;

    /**
     * Constructor.
     */
    public InputGuardComponent() {
        state = new CuiState(getStateHelper());
        guardAttributes = new GuardButtonAttributes(this);
        resetGuardButtonAttributes = new ResetGuardButtonAttributes(this);
        ajaxProvider = new AjaxProvider(this).ajaxDefaultProcess("@this").ajaxDefaultUpdate("@namingcontainer");
    }

    // ContainerPlugin
    @Override
    public void prerender(LabeledContainerComponent parent) {

        if (!isRendered()) {
            return;
        }
        var guarded = resolveValue();
        parent.setDisabled(guarded);
        getPassThroughAttributes().putAll(ajaxProvider.resolveAjaxAttributesAsMap(this));
    }

    @Override
    public Optional<UIComponent> provideFacetContent(ContainerFacets facet) {
        if (isAnyFalse(isRendered(), ContainerFacets.INPUT_DECORATOR.contains(facet), getRenderButtons())) {
            return Optional.empty();
        }
        var align = ContainerFacets.parseButtonAlign(getButtonAlign(), BUTTON_ALIGN_KEY);
        if (align != facet) {
            return Optional.empty();
        }

        var button = CommandButton.create(getFacesContext());
        button.getPassThroughAttributes().put(DATA_GUARDED_BUTTON, DATA_GUARDED_BUTTON);
        return Optional.of(updateGuardButtonContent(button, resolveValue()));

    }

    @Override
    public void postAddToView(LabeledContainerComponent parent) {
        getPassThroughAttributes().put(DATA_GUARDED_INPUT, DATA_GUARDED_INPUT);
    }

    @Override
    public PluginStateInfo provideContainerStateInfo() {
        if (!isRendered() || resolveValue().booleanValue()) {
            return PluginStateInfo.NO_STATE_INFO;
        }
        return PluginStateInfo.WARNING;
    }

    private CommandButton updateGuardButtonContent(CommandButton button, Boolean guarded) {
        if (Boolean.TRUE.equals(guarded)) {
            button.setIcon(guardAttributes.getGuardIcon());
            button.setTitleValue(guardAttributes.resolveGuardButtonTitle());
        } else {
            button.setIcon(resetGuardButtonAttributes.getResetGuardIcon());
            button.setTitleValue(resetGuardButtonAttributes.resolveResetGuardButtonTitle());
        }
        button.getPassThroughAttributes().put(DATA_GUARDED_TARGET, !guarded);
        return button;
    }

    @Override
    public void setValue(Object value) {
        state.put(CURRENT_VALUE_KEY, value);
        super.setValue(value);
    }

    private Boolean resolveValue() {
        var currentValue = state.get(CURRENT_VALUE_KEY);
        if (null != currentValue) {
            return Boolean.valueOf(currentValue.toString());
        }
        var value = getValue();
        if (null == value) {
            log.trace("value not set, defaulting to true");
            state.put(CURRENT_VALUE_KEY, Boolean.TRUE);
            setValue(Boolean.TRUE);
            return Boolean.TRUE;
        }
        var booleanValue = Boolean.valueOf(value.toString());
        state.put(CURRENT_VALUE_KEY, booleanValue);
        return booleanValue;
    }

    /**
     * We need to decode
     */
    @Override
    public void decode(FacesContext context) {

        if (!isRendered()) {
            return;
        }
        var clientId = getClientId(context);

        var parameterMap = context.getExternalContext().getRequestParameterMap();
        if (clientId.equals(parameterMap.get(JAVAX_FACES_SOURCE))) {
            // AjaxRequest from rendered Button
            var newValue = parameterMap.get(clientId);
            if (isEmpty(newValue)) {
                log.debug("No value submitted, ignoring");
                return;
            }
            var newValueBoolean = Boolean.valueOf(newValue);
            var currentValue = resolveValue();
            if (!newValueBoolean.equals(currentValue)) {
                handleStateChange(newValueBoolean);
            }
        } else {
            // Request From other source: Default renderer should take care
            super.decode(context);
        }

    }

    @Override
    public void resetValue() {
        getStateHelper().remove(CURRENT_VALUE_KEY);
        super.resetValue();
    }

    private void handleStateChange(Boolean newValueBoolean) {
        state.put(CURRENT_VALUE_KEY, newValueBoolean);
        setSubmittedValue(newValueBoolean.toString());

        if (areAllTrue(newValueBoolean, getResetInputValue())) {
            var forComponent = getParentContainer(this).findRelatedComponentModifier();
            if (null != forComponent && forComponent.isSupportsResetValue()) {
                forComponent.resetValue();
            }
        }
    }

    private static LabeledContainerComponent getParentContainer(UIComponent component) {
        checkState(null != component, "InputGuard is designed to work as a child of LabeledContainer");
        if (component instanceof LabeledContainerComponent) {
            return (LabeledContainerComponent) component;
        }
        return getParentContainer(component.getParent());
    }

    /**
     * @return the buttonAlign, defaults to {@code true}
     */
    public boolean getRenderButtons() {
        return state.getBoolean(RENDER_BUTTONS_KEY, true);
    }

    /**
     * @param renderButtons to be set
     */
    public void setRenderButtons(boolean renderButtons) {
        state.put(RENDER_BUTTONS_KEY, renderButtons);
    }

    /**
     * @return the buttonAlign, defaults to {@link ContainerFacets#APPEND }
     */
    public String getButtonAlign() {
        return state.get(BUTTON_ALIGN_KEY, ContainerFacets.APPEND.getName());
    }

    /**
     * @param buttonAlign to be set, expected is one of
     *                    {@link ContainerFacets#APPEND} or
     *                    {@link ContainerFacets#PREPEND}
     */
    public void setButtonAlign(String buttonAlign) {
        state.put(BUTTON_ALIGN_KEY, buttonAlign);
    }

    /**
     * @return the resetInputValue, defaults to {@code true}
     */
    public boolean getResetInputValue() {
        return state.getBoolean(RESET_VALUE_ON_UNGUARD_KEY, true);
    }

    /**
     * @param resetInputValue to be set,
     */
    public void setResetInputValue(boolean resetInputValue) {
        state.put(RESET_VALUE_ON_UNGUARD_KEY, resetInputValue);
    }

    @Override
    public String getId() {
        return FIXED_ID;
    }

    @Override
    public String getFamily() {
        return JsfComponentIdentifier.INPUT_FAMILY;
    }

}
