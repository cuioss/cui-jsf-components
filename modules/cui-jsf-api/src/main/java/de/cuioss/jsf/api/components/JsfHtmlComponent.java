package de.cuioss.jsf.api.components;

import static de.cuioss.jsf.api.components.JsfComponentIdentifier.BUTTON_RENDERER_TYPE;
import static de.cuioss.jsf.api.components.JsfComponentIdentifier.CHECKBOX_RENDERER_TYPE;
import static de.cuioss.jsf.api.components.JsfComponentIdentifier.FORM_RENDERER_TYPE;
import static de.cuioss.jsf.api.components.JsfComponentIdentifier.GROUP_RENDERER_TYPE;
import static de.cuioss.jsf.api.components.JsfComponentIdentifier.HIDDEN_RENDERER_TYPE;
import static de.cuioss.jsf.api.components.JsfComponentIdentifier.LINK_RENDERER_TYPE;
import static de.cuioss.jsf.api.components.JsfComponentIdentifier.TEXT_RENDERER_TYPE;
import static java.util.Objects.requireNonNull;

import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutcomeTargetButton;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.tools.collect.CollectionLiterals;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Repository for the standard JSF components / renderer
 *
 * @author Oliver Wolff
 * @param <T>
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JsfHtmlComponent<T extends UIComponent> {

    /** Component representation of h:button */
    public static final JsfHtmlComponent<HtmlOutcomeTargetButton> BUTTON =
        new JsfHtmlComponent<>(HtmlOutcomeTargetButton.COMPONENT_FAMILY, HtmlOutcomeTargetButton.COMPONENT_TYPE,
                BUTTON_RENDERER_TYPE, HtmlOutcomeTargetButton.class, Node.BUTTON);

    /** Component representation of h:commandButton */
    public static final JsfHtmlComponent<HtmlCommandButton> COMMAND_BUTTON =
        new JsfHtmlComponent<>(HtmlCommandButton.COMPONENT_FAMILY, HtmlCommandButton.COMPONENT_TYPE,
                BUTTON_RENDERER_TYPE,
                HtmlCommandButton.class, Node.BUTTON);

    /** Component representation of h:form */
    public static final JsfHtmlComponent<HtmlForm> FORM =
        new JsfHtmlComponent<>(HtmlForm.COMPONENT_FAMILY, HtmlForm.COMPONENT_TYPE, FORM_RENDERER_TYPE,
                HtmlForm.class, Node.FORM);

    /** Component representation of h:panelGroup */
    public static final JsfHtmlComponent<HtmlPanelGroup> PANEL_GROUP =
        new JsfHtmlComponent<>(HtmlPanelGroup.COMPONENT_FAMILY, HtmlPanelGroup.COMPONENT_TYPE,
                GROUP_RENDERER_TYPE,
                HtmlPanelGroup.class, Node.DIV);

    /** Component representation of h:outputText */
    public static final JsfHtmlComponent<HtmlOutputText> SPAN =
        new JsfHtmlComponent<>(HtmlOutputText.COMPONENT_FAMILY, HtmlOutputText.COMPONENT_TYPE, TEXT_RENDERER_TYPE,
                HtmlOutputText.class, Node.SPAN);

    /** Component representation for h:selectBooleanCheckbox */
    public static final JsfHtmlComponent<HtmlSelectBooleanCheckbox> CHECKBOX =
        new JsfHtmlComponent<>(HtmlSelectBooleanCheckbox.COMPONENT_FAMILY,
                HtmlSelectBooleanCheckbox.COMPONENT_TYPE,
                CHECKBOX_RENDERER_TYPE, HtmlSelectBooleanCheckbox.class, Node.INPUT);

    /** UIInput representation for h:inputText */
    public static final JsfHtmlComponent<UIInput> INPUT =
        new JsfHtmlComponent<>(UIInput.COMPONENT_FAMILY, UIInput.COMPONENT_TYPE, TEXT_RENDERER_TYPE,
                UIInput.class, Node.INPUT);

    /** Component representation for h:inputHidden */
    public static final JsfHtmlComponent<HtmlInputHidden> HIDDEN =
        new JsfHtmlComponent<>(HtmlInputHidden.COMPONENT_FAMILY, HtmlInputHidden.COMPONENT_TYPE,
                HIDDEN_RENDERER_TYPE,
                HtmlInputHidden.class, Node.INPUT);

    /** Component representation for h:inputText */
    public static final JsfHtmlComponent<HtmlInputText> HTMLINPUT =
        new JsfHtmlComponent<>(HtmlInputText.COMPONENT_FAMILY, HtmlInputText.COMPONENT_TYPE, TEXT_RENDERER_TYPE,
                HtmlInputText.class, Node.INPUT);

    /** Component representation for h:outputText */
    public static final JsfHtmlComponent<HtmlOutputText> HTML_OUTPUT_TEXT =
        new JsfHtmlComponent<>(HtmlOutputText.COMPONENT_FAMILY, HtmlOutputText.COMPONENT_TYPE,
                TEXT_RENDERER_TYPE,
                HtmlOutputText.class, Node.SPAN);

    /** Component representation for h:outputLink */
    public static final JsfHtmlComponent<HtmlOutputLink> HTML_OUTPUT_LINK =
        new JsfHtmlComponent<>(HtmlOutputLink.COMPONENT_FAMILY, HtmlOutputLink.COMPONENT_TYPE,
                LINK_RENDERER_TYPE,
                HtmlOutputLink.class, Node.A);

    /** Similar toEnum#values(): Provides all elements */
    @SuppressWarnings("squid:S2386") // owolff: false positive, list is immutable
    public static final Collection<JsfHtmlComponent<? extends UIComponentBase>> VALUES =
        CollectionLiterals.immutableList(BUTTON, COMMAND_BUTTON, FORM, PANEL_GROUP, SPAN, CHECKBOX, INPUT, HIDDEN,
                HTMLINPUT, HTML_OUTPUT_TEXT, HTML_OUTPUT_LINK);
    @Getter
    private final String family;
    @Getter
    private final String componentType;
    @Getter
    private final String rendererType;
    @Getter
    private final Class<T> componentClass;
    @Getter
    private final Node defaultHtmlElement;

    /**
     * Returns a newly created instance of that component
     *
     * @param context must not be null
     * @return The newly created {@link UIComponent}
     */
    public T component(final FacesContext context) {
        return ComponentUtility.createComponent(context, componentType);
    }

    /**
     * Returns a newly created instance of the renderer
     *
     * @param context must not be null
     * @return The newly created {@link Renderer}
     */
    public Renderer renderer(final FacesContext context) {
        return ComponentUtility.createRenderer(context, family, rendererType);
    }

    /**
     * Shortcut for creating and casting a component to a given type.
     *
     * @param context must not be null
     * @param component to be created, must not be null
     * @return the created component.
     */
    public static <T extends UIComponent> T createComponent(final FacesContext context,
            final JsfHtmlComponent<T> component) {
        requireNonNull(component);
        return component.component(context);
    }

    /**
     * Shortcut for creating and casting a renderer to a given type.
     *
     * @param context must not be null
     * @param component to be created, must not be null
     * @return the created component.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Renderer> T createRenderer(final FacesContext context,
            final JsfHtmlComponent<?> component) {
        requireNonNull(component);
        return (T) component.renderer(context);
    }

}
