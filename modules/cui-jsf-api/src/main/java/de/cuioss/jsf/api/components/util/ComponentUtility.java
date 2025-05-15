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
package de.cuioss.jsf.api.components.util;

import static de.cuioss.tools.base.Preconditions.checkArgument;
import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static java.util.Objects.requireNonNull;

import jakarta.faces.component.*;
import jakarta.faces.component.visit.VisitContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.Renderer;

import java.util.List;
import java.util.Map;

/**
 * <p>Utility class providing helper methods for JSF component manipulation, navigation and state management.
 * This class offers functionality for:</p>
 * <ul>
 *   <li>Finding parent components in the component tree (forms, naming containers)</li>
 *   <li>Determining the source of requests and detecting AJAX requests</li>
 *   <li>Component state manipulation and validation</li>
 *   <li>Component tree traversal and manipulation</li>
 * </ul>
 * 
 * <p>This class follows the utility pattern and provides only static methods.
 * All methods are designed to be robust against common error conditions 
 * and provide clear exceptions with descriptive messages when invalid 
 * inputs are detected.</p>
 * 
 * <p>Usage examples:</p>
 * <pre>
 * // Find the enclosing form for a component
 * UIForm form = ComponentUtility.findCorrespondingForm(someComponent);
 * 
 * // Reset all input fields in a form
 * ComponentUtility.resetEditableValueHolder(form, facesContext);
 * 
 * // Check if a request is an AJAX request
 * boolean isAjax = ComponentUtility.isAjaxRequest(requestParameterMap);
 * 
 * // Create a new component instance
 * UIInput input = ComponentUtility.createComponent(facesContext, "jakarta.faces.Input");
 * </pre>
 *
 * @author Oliver Wolff
 */
public final class ComponentUtility {

    /**
     * <p>Request parameter name for the client ID of the component that triggered
     * the current request.</p>
     * 
     * <p>This parameter is set by the JSF runtime for all component-initiated requests,
     * including form submissions and AJAX requests.</p>
     */
    public static final String JAVAX_FACES_SOURCE = "jakarta.faces.source";

    /**
     * <p>Request parameter name indicating that the current request is an AJAX request.</p>
     * 
     * <p>When this parameter is present with a value of "true", it indicates
     * that the request was made using the JSF AJAX framework.</p>
     */
    public static final String JAVAX_FACES_PARTIAL_AJAX = "jakarta.faces.partial.ajax";

    /**
     * <p>Determines whether the request was triggered by the given component.</p>
     * 
     * <p>This method checks if the specified component is the source of the current request
     * by comparing its client ID with the value of the {@link #JAVAX_FACES_SOURCE} parameter.
     * This is useful for components that need to perform special processing only when they
     * are directly triggered.</p>
     *
     * @param map       The request parameter map from the external context,
     *                  typically obtained from {@code FacesContext.getExternalContext().getRequestParameterMap()}.
     *                  Must not be null.
     * @param component The component to check against. Must not be null.
     * 
     * @return {@code true} if the request was triggered by the specified component,
     *         {@code false} otherwise
     * 
     * @throws NullPointerException If either parameter is null
     */
    public static boolean isSelfRequest(final Map<String, String> map, final UIComponent component) {
        return map.containsKey(JAVAX_FACES_SOURCE) && component.getClientId().equals(map.get(JAVAX_FACES_SOURCE));
    }

    /**
     * <p>Determines whether the current request is an AJAX request.</p>
     * 
     * <p>This method checks for the presence of the {@link #JAVAX_FACES_PARTIAL_AJAX}
     * parameter with a value of "true" in the request parameter map.</p>
     *
     * @param map The request parameter map from the external context,
     *            typically obtained from {@code FacesContext.getExternalContext().getRequestParameterMap()}.
     *            Must not be null.
     * 
     * @return {@code true} if the current request is an AJAX request,
     *         {@code false} otherwise
     *         
     * @throws NullPointerException If the map is null
     */
    public static boolean isAjaxRequest(final Map<String, String> map) {
        return map.containsKey(JAVAX_FACES_PARTIAL_AJAX) && Boolean.parseBoolean(map.get(JAVAX_FACES_PARTIAL_AJAX));
    }

    /**
     * <p>Finds the enclosing {@link UIForm} for the given component.</p>
     * 
     * <p>This method traverses up the component tree, starting from the specified component,
     * until it finds a {@link UIForm} component or reaches the root of the tree.</p>
     *
     * @param component The component to start the search from. May be null.
     * 
     * @return The enclosing {@link UIForm} for the given component
     * 
     * @throws IllegalArgumentException If no form is found in the component's ancestry
     *                                  or if the component parameter is null
     */
    public static UIForm findCorrespondingForm(final UIComponent component) {
        checkArgument(null != component, "Component is null, no valid form found");

        if (component instanceof UIForm form) {
            return form;
        }

        return findCorrespondingForm(component.getParent());
    }

    /**
     * <p>Determines whether the given component is nested within a {@link UIForm}.</p>
     * 
     * <p>This method uses {@link #findCorrespondingFormOrNull(UIComponent)} to check
     * if the component has a form in its ancestry.</p>
     *
     * @param component The component to check. May be null.
     * 
     * @return {@code true} if the component is nested within a form,
     *         {@code false} otherwise (including if the component is null)
     */
    public static boolean isInForm(final UIComponent component) {
        return findCorrespondingFormOrNull(component) != null;
    }

    /**
     * <p>Verifies that the given component is nested within a {@link UIForm} and returns the form.</p>
     * 
     * <p>This method checks if the component has a form in its ancestry and throws an
     * exception if none is found. This is useful for components that require being placed
     * inside a form to function correctly.</p>
     *
     * @param component The component to check. Must not be null.
     * 
     * @return The enclosing form if present
     * 
     * @throws NullPointerException If the component parameter is null
     * @throws IllegalStateException If the component is not nested within a form
     */
    public static UIComponent checkIsNestedInForm(final UIComponent component) {
        requireNonNull(component);
        final UIComponent form = ComponentUtility.findCorrespondingFormOrNull(component);
        if (form == null) {
            throw new IllegalStateException("Component %s with id [%s] should be placed inside a form"
                    .formatted(component.getClass().getSimpleName(), component.getId()));
        }
        return form;
    }

    /**
     * <p>Finds the enclosing {@link UIForm} for the given component, returning null if none is found.</p>
     * 
     * <p>This method is similar to {@link #findCorrespondingForm(UIComponent)}, but returns
     * null instead of throwing an exception if no form is found. This is useful when you want
     * to check for the presence of a form without requiring it.</p>
     *
     * @param component The component to start the search from. May be null.
     * 
     * @return The enclosing {@link UIForm} for the given component,
     *         or null if no form is found or if the component is null
     */
    public static UIForm findCorrespondingFormOrNull(final UIComponent component) {

        if (null == component) {
            return null;
        }

        if (component instanceof UIForm form) {
            return form;
        }

        return findCorrespondingFormOrNull(component.getParent());

    }

    /**
     * <p>Finds the nearest {@link UINamingContainer} in the component's ancestry.</p>
     * 
     * <p>This method traverses up the component tree, starting from the specified component,
     * until it finds a component that implements the {@link NamingContainer} interface.</p>
     * 
     * <p>Naming containers are important in JSF as they create a new namespace for component IDs,
     * affecting the client IDs generated for child components.</p>
     *
     * @param component The component to start the search from
     * 
     * @return The nearest naming container in the component's ancestry
     * 
     * @throws IllegalArgumentException If no naming container is found or if the component parameter is null
     */
    public static NamingContainer findNearestNamingContainer(final UIComponent component) {
        checkArgument(null != component, "No parent naming container could be found");
        if (component instanceof NamingContainer container) {
            return container;
        }
        return findNearestNamingContainer(component.getParent());
    }

    /**
     * <p>Resets all editable value holders within the given form.</p>
     * 
     * <p>This method finds all rendered components within the specified form that implement
     * {@link EditableValueHolder} and calls {@link EditableValueHolder#resetValue()} on each.
     * This effectively clears all input fields in the form.</p>
     * 
     * <p>This is useful for programmatically clearing a form after a successful submission
     * or when implementing a "Reset" button.</p>
     *
     * @param form         The form containing the editable value holders to reset. Must not be null.
     * @param facesContext The current faces context. Must not be null.
     * 
     * @throws NullPointerException If either parameter is null
     */
    public static void resetEditableValueHolder(final UIForm form, final FacesContext facesContext) {

        requireNonNull(form);
        requireNonNull(facesContext);

        // iterate over found subcomponents and reset their values
        final var editableValueHolders = getEditableValueHolders(form, facesContext);

        for (final EditableValueHolder editableValueHolder : editableValueHolders) {
            editableValueHolder.resetValue();
        }

    }

    /**
     * <p>Sets all editable value holders within the given form to valid state.</p>
     * 
     * <p>This method finds all rendered components within the specified form that implement
     * {@link EditableValueHolder} and calls {@link EditableValueHolder#setValid(boolean)} with
     * {@code true} on each. This effectively clears all validation errors in the form.</p>
     * 
     * <p>This can be useful when implementing custom validation logic that needs to
     * temporarily suppress or override standard JSF validation errors.</p>
     *
     * @param form         The form containing the editable value holders to validate. Must not be null.
     * @param facesContext The current faces context. Must not be null.
     * 
     * @throws NullPointerException If either parameter is null
     */
    public static void setEditableValueHoldersValid(final UIForm form, final FacesContext facesContext) {

        requireNonNull(form);
        requireNonNull(facesContext);

        // iterate over found subcomponents and reset their values
        final var editableValueHolders = getEditableValueHolders(form, facesContext);

        for (final EditableValueHolder editableValueHolder : editableValueHolders) {
            editableValueHolder.setValid(true);
        }

    }

    /**
     * <p>Creates a component of the specified type and casts it to the expected class.</p>
     * 
     * <p>This is a convenience method that combines calling {@link jakarta.faces.application.Application#createComponent(String)}
     * with casting the result to the expected type. It's particularly useful when creating
     * components programmatically in a type-safe manner.</p>
     *
     * @param <T>          The expected component type
     * @param context      The faces context. Must not be null.
     * @param componentType The component type identifier. Must not be null or empty.
     * 
     * @return The newly created component, cast to the expected type
     * 
     * @throws NullPointerException If either parameter is null
     * @throws IllegalArgumentException If componentType is empty
     * @throws ClassCastException If the created component cannot be cast to the expected type
     */
    @SuppressWarnings("unchecked")
    public static <T extends UIComponent> T createComponent(final FacesContext context, final String componentType) {
        requireNonNull(context);
        requireNonNull(emptyToNull(componentType));
        return (T) context.getApplication().createComponent(componentType);
    }

    /**
     * <p>Creates a renderer of the specified type and casts it to the expected class.</p>
     * 
     * <p>This is a convenience method that combines calling {@link jakarta.faces.render.RenderKit#getRenderer(String, String)}
     * with casting the result to the expected type. It's particularly useful when obtaining
     * renderers programmatically in a type-safe manner.</p>
     *
     * @param <T>          The expected renderer type
     * @param context      The faces context. Must not be null.
     * @param family       The component family. Must not be null or empty.
     * @param rendererType The renderer type. Must not be null or empty.
     * 
     * @return The renderer, cast to the expected type
     * 
     * @throws NullPointerException If any parameter is null
     * @throws IllegalArgumentException If family or rendererType is empty
     * @throws ClassCastException If the renderer cannot be cast to the expected type
     */
    @SuppressWarnings("unchecked")
    public static <T extends Renderer> T createRenderer(final FacesContext context, final String family,
            final String rendererType) {
        requireNonNull(context);
        requireNonNull(emptyToNull(family));
        requireNonNull(emptyToNull(rendererType));
        return (T) context.getRenderKit().getRenderer(family, rendererType);
    }

    /**
     * <p>Helper method to retrieve all editable value holders within a form.</p>
     * 
     * <p>This method uses the {@link EditableValueHoldersVisitCallback} to traverse
     * the component tree and collect all components that implement {@link EditableValueHolder}.</p>
     *
     * @param form         The form to search within. Must not be null.
     * @param facesContext The current faces context. Must not be null.
     * 
     * @return A list of all editable value holders found within the form
     */
    private static List<EditableValueHolder> getEditableValueHolders(final UIForm form,
            final FacesContext facesContext) {
        final var visitCallback = new EditableValueHoldersVisitCallback();

        final var visitContext = VisitContext.createVisitContext(facesContext);

        form.visitTree(visitContext, visitCallback);

        return visitCallback.getEditableValueHolders();
    }

    /**
     * <p>Private constructor to prevent instantiation of this utility class.</p>
     * 
     * <p>This class contains only static methods and should not be instantiated.</p>
     */
    private ComponentUtility() {
        // Enforce utility class pattern
    }
}
