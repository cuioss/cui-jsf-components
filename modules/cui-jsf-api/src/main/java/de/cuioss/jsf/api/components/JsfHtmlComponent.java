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
package de.cuioss.jsf.api.components;

import static de.cuioss.jsf.api.components.JsfComponentIdentifier.*;
import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.tools.collect.CollectionLiterals;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.component.UIInput;
import jakarta.faces.component.html.*;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.Renderer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

/**
 * Repository for the standard JSF HTML components and renderers.
 * <p>
 * This enum-like class provides access to commonly used JSF component types with their
 * associated component families, renderer types, and default HTML elements. It simplifies
 * the process of creating standard JSF components programmatically.
 * </p>
 * <p>
 * Each component is represented as a static final instance with convenience methods for 
 * component and renderer creation. The class allows you to create components without needing
 * to know the specific implementation details like component families and renderer types.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * FacesContext context = FacesContext.getCurrentInstance();
 * HtmlCommandButton button = JsfHtmlComponent.COMMAND_BUTTON.component(context);
 * button.setValue("Submit");
 * </pre>
 * <p>
 * This class is thread-safe as all instances are immutable.
 * </p>
 *
 * @author Oliver Wolff
 * @param <T> the specific type of UIComponent represented by this instance
 * @since 1.0
 * @see UIComponent
 * @see Renderer
 * @see ComponentUtility
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JsfHtmlComponent<T extends UIComponent> {

    /** 
     * Component representation of h:button.
     * <p>
     * Creates an HTML button that acts as a link with navigation outcomes.
     * </p>
     */
    public static final JsfHtmlComponent<HtmlOutcomeTargetButton> BUTTON = new JsfHtmlComponent<>(
            HtmlOutcomeTargetButton.COMPONENT_FAMILY, HtmlOutcomeTargetButton.COMPONENT_TYPE, BUTTON_RENDERER_TYPE,
            HtmlOutcomeTargetButton.class, Node.BUTTON);

    /** 
     * Component representation of h:commandButton.
     * <p>
     * Creates an HTML button that submits the form and invokes actions.
     * </p>
     */
    public static final JsfHtmlComponent<HtmlCommandButton> COMMAND_BUTTON = new JsfHtmlComponent<>(
            HtmlCommandButton.COMPONENT_FAMILY, HtmlCommandButton.COMPONENT_TYPE, BUTTON_RENDERER_TYPE,
            HtmlCommandButton.class, Node.BUTTON);

    /** 
     * Component representation of h:form.
     * <p>
     * Creates an HTML form element that encapsulates other components for submission.
     * </p>
     */
    public static final JsfHtmlComponent<HtmlForm> FORM = new JsfHtmlComponent<>(HtmlForm.COMPONENT_FAMILY,
            HtmlForm.COMPONENT_TYPE, FORM_RENDERER_TYPE, HtmlForm.class, Node.FORM);

    /** 
     * Component representation of h:panelGroup.
     * <p>
     * Creates a container component, typically rendered as a div element.
     * </p>
     */
    public static final JsfHtmlComponent<HtmlPanelGroup> PANEL_GROUP = new JsfHtmlComponent<>(
            HtmlPanelGroup.COMPONENT_FAMILY, HtmlPanelGroup.COMPONENT_TYPE, GROUP_RENDERER_TYPE, HtmlPanelGroup.class,
            Node.DIV);

    /** 
     * Component representation of h:outputText rendered as a span.
     * <p>
     * Creates a component for displaying text output, rendered as a span element.
     * </p>
     */
    public static final JsfHtmlComponent<HtmlOutputText> SPAN = new JsfHtmlComponent<>(HtmlOutputText.COMPONENT_FAMILY,
            HtmlOutputText.COMPONENT_TYPE, TEXT_RENDERER_TYPE, HtmlOutputText.class, Node.SPAN);

    /** 
     * Component representation for h:selectBooleanCheckbox.
     * <p>
     * Creates an HTML checkbox input element.
     * </p>
     */
    public static final JsfHtmlComponent<HtmlSelectBooleanCheckbox> CHECKBOX = new JsfHtmlComponent<>(
            HtmlSelectBooleanCheckbox.COMPONENT_FAMILY, HtmlSelectBooleanCheckbox.COMPONENT_TYPE,
            CHECKBOX_RENDERER_TYPE, HtmlSelectBooleanCheckbox.class, Node.INPUT);

    /** 
     * UIInput representation for h:inputText.
     * <p>
     * Creates a generic UIInput component used for text input.
     * </p>
     */
    public static final JsfHtmlComponent<UIInput> INPUT = new JsfHtmlComponent<>(UIInput.COMPONENT_FAMILY,
            UIInput.COMPONENT_TYPE, TEXT_RENDERER_TYPE, UIInput.class, Node.INPUT);

    /** 
     * Component representation for h:inputHidden.
     * <p>
     * Creates an HTML hidden input element.
     * </p>
     */
    public static final JsfHtmlComponent<HtmlInputHidden> HIDDEN = new JsfHtmlComponent<>(
            HtmlInputHidden.COMPONENT_FAMILY, HtmlInputHidden.COMPONENT_TYPE, HIDDEN_RENDERER_TYPE,
            HtmlInputHidden.class, Node.INPUT);

    /** 
     * Component representation for h:inputText.
     * <p>
     * Creates an HTML text input element.
     * </p>
     */
    public static final JsfHtmlComponent<HtmlInputText> HTML_INPUT = new JsfHtmlComponent<>(
            HtmlInputText.COMPONENT_FAMILY, HtmlInputText.COMPONENT_TYPE, TEXT_RENDERER_TYPE, HtmlInputText.class,
            Node.INPUT);

    /** 
     * Component representation for h:outputText.
     * <p>
     * Creates a component for displaying text output.
     * </p>
     */
    public static final JsfHtmlComponent<HtmlOutputText> HTML_OUTPUT_TEXT = new JsfHtmlComponent<>(
            HtmlOutputText.COMPONENT_FAMILY, HtmlOutputText.COMPONENT_TYPE, TEXT_RENDERER_TYPE, HtmlOutputText.class,
            Node.SPAN);

    /** 
     * Component representation for h:outputLink.
     * <p>
     * Creates an HTML anchor element for linking.
     * </p>
     */
    public static final JsfHtmlComponent<HtmlOutputLink> HTML_OUTPUT_LINK = new JsfHtmlComponent<>(
            HtmlOutputLink.COMPONENT_FAMILY, HtmlOutputLink.COMPONENT_TYPE, LINK_RENDERER_TYPE, HtmlOutputLink.class,
            Node.A);

    /** 
     * Similar to Enum#values(): Provides all component representations defined in this class.
     * <p>
     * This collection can be used to iterate over all available component representations.
     * The collection is immutable.
     * </p>
     */
    @SuppressWarnings("squid:S2386") // owolff: false positive, the list is immutable
    public static final Collection<JsfHtmlComponent<? extends UIComponentBase>> VALUES = CollectionLiterals
            .immutableList(BUTTON, COMMAND_BUTTON, FORM, PANEL_GROUP, SPAN, CHECKBOX, INPUT, HIDDEN, HTML_INPUT,
                    HTML_OUTPUT_TEXT, HTML_OUTPUT_LINK);

    /**
     * The component family used for component creation.
     */
    @Getter
    private final String family;

    /**
     * The component type identifier used for component creation.
     */
    @Getter
    private final String componentType;

    /**
     * The renderer type used for renderer creation.
     */
    @Getter
    private final String rendererType;

    /**
     * The class type of the component being represented.
     */
    @Getter
    private final Class<T> componentClass;

    /**
     * The default HTML element used for rendering this component.
     */
    @Getter
    private final Node defaultHtmlElement;

    /**
     * Returns a newly created instance of the component represented by this JsfHtmlComponent.
     * <p>
     * This method uses the FacesContext to create a component instance using the configured
     * componentType.
     * </p>
     *
     * @param context the FacesContext used for component creation, must not be null
     * @return a newly created instance of the UIComponent of type T
     * @throws NullPointerException if context is null
     * @see ComponentUtility#createComponent(FacesContext, String)
     */
    public T component(final FacesContext context) {
        return ComponentUtility.createComponent(context, componentType);
    }

    /**
     * Returns a newly created instance of the renderer for this component.
     * <p>
     * This method uses the FacesContext to create a renderer instance using the configured
     * family and rendererType.
     * </p>
     *
     * @param context the FacesContext used for renderer creation, must not be null
     * @return a newly created instance of the Renderer
     * @throws NullPointerException if context is null
     * @see ComponentUtility#createRenderer(FacesContext, String, String)
     */
    public Renderer renderer(final FacesContext context) {
        return ComponentUtility.createRenderer(context, family, rendererType);
    }

    /**
     * Static convenience method that creates and casts a component to a given type.
     * <p>
     * This is a shorthand for calling {@link #component(FacesContext)} on the provided component.
     * </p>
     *
     * @param <T> the specific type of UIComponent to create
     * @param context the FacesContext used for component creation, must not be null
     * @param component the JsfHtmlComponent configuration to use for creation, must not be null
     * @return the newly created UIComponent of type T
     * @throws NullPointerException if context or component is null
     */
    public static <T extends UIComponent> T createComponent(final FacesContext context,
            final JsfHtmlComponent<T> component) {
        requireNonNull(component);
        return component.component(context);
    }

    /**
     * Static convenience method that creates and casts a renderer to a given type.
     * <p>
     * This is a shorthand for calling {@link #renderer(FacesContext)} on the provided component
     * and casting to the expected renderer type.
     * </p>
     *
     * @param <T> the specific type of Renderer to create
     * @param context the FacesContext used for renderer creation, must not be null
     * @param component the JsfHtmlComponent configuration to use for renderer creation, must not be null
     * @return the newly created Renderer of type T
     * @throws NullPointerException if context or component is null
     */
    @SuppressWarnings("unchecked")
    public static <T extends Renderer> T createRenderer(final FacesContext context,
            final JsfHtmlComponent<?> component) {
        requireNonNull(component);
        return (T) component.renderer(context);
    }
}
