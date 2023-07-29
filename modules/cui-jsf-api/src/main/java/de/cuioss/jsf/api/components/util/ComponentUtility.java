package de.cuioss.jsf.api.components.util;

import static de.cuioss.tools.base.Preconditions.checkArgument;
import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UINamingContainer;
import javax.faces.component.visit.VisitContext;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

/**
 * Some component / tree specific helper methods.
 *
 * @author Oliver Wolff
 */
public final class ComponentUtility {

    /**
     * Constant of javax.faces.source
     */
    public static final String JAVAX_FACES_SOURCE = "javax.faces.source";

    /**
     * Constant of javax.faces.partial.ajax
     */
    public static final String JAVAX_FACES_PARTIAL_AJAX = "javax.faces.partial.ajax";

    /**
     * Checks whether the component sent the request itself or the request was sent
     * by one of it ancestors.
     *
     * @param map       representing the external Context based request parameter ,
     *                  must nor be null
     * @param component the be checked against.
     * @return boolean indicating whether the request was triggered by the component
     *         itself
     */
    public static boolean isSelfRequest(final Map<String, String> map, final UIComponent component) {
        return map.containsKey(JAVAX_FACES_SOURCE) && component.getClientId().equals(map.get(JAVAX_FACES_SOURCE));
    }

    /**
     * Checks whether the current request is an ajax request
     *
     * @param map representing the external Context based request parameter, must
     *            nor be null
     * @return boolean indicating whether the request is am Ajax-Request
     */
    public static boolean isAjaxRequest(final Map<String, String> map) {
        return map.containsKey(JAVAX_FACES_PARTIAL_AJAX) && Boolean.parseBoolean(map.get(JAVAX_FACES_PARTIAL_AJAX));
    }

    /**
     * @param component to be started from
     * @return The form for the given component. If there is no form as parent for
     *         the given component it throws an {@link IllegalArgumentException}
     */
    public static UIForm findCorrespondingForm(final UIComponent component) {
        checkArgument(null != component, "Component is null, no valid form found");

        if (component instanceof UIForm) {
            return (UIForm) component;
        }

        return findCorrespondingForm(component.getParent());
    }

    /**
     * @param component to be started from
     * @return TRUE if the component is nested in a form, FALSE otherwise.
     */
    public static boolean isInForm(final UIComponent component) {
        return findCorrespondingFormOrNull(component) != null;
    }

    /**
     * Checks if the component is nested in a form. Otherwise throw an
     * {@link IllegalStateException}.
     *
     * @param component the component to check, must not be null.
     * @return the form is present.
     */
    public static UIComponent checkIsNestedInForm(final UIComponent component) {
        requireNonNull(component);
        final UIComponent form = ComponentUtility.findCorrespondingFormOrNull(component);
        if (form == null) {
            throw new IllegalStateException(String.format("Component %s with id [%s] should be placed inside a form",
                    component.getClass().getSimpleName(), component.getId()));
        }
        return form;
    }

    /**
     * @param component to be started from
     * @return The form for the given component. If there is no form as parent for
     *         the given component it returns null. This is the main difference to
     *         {@link #findCorrespondingForm(UIComponent)}
     */
    public static UIForm findCorrespondingFormOrNull(final UIComponent component) {

        if (null == component) {
            return null;
        }

        if (component instanceof UIForm) {
            return (UIForm) component;
        }

        return findCorrespondingFormOrNull(component.getParent());

    }

    /**
     * Finds the nearest {@link UINamingContainer} for the given component.
     *
     * @param component to be checked
     * @return The nearest found naming Container. If there is no
     *         {@link UINamingContainer} as parent for the given component it throws
     *         an {@link IllegalArgumentException}
     */
    public static NamingContainer findNearestNamingContainer(final UIComponent component) {
        checkArgument(null != component, "No parent naming container could be found");
        if (component instanceof NamingContainer) {
            return (NamingContainer) component;
        }
        return findNearestNamingContainer(component.getParent());
    }

    /**
     * Visits all {@link EditableValueHolder} within the given form that are
     * rendered and resets them.
     *
     * @param form         to start from, must not be null
     * @param facesContext must not be null
     */
    public static void resetEditableValueHolder(final UIForm form, final FacesContext facesContext) {

        requireNonNull(form);
        requireNonNull(facesContext);

        // iterate over found sub-components and reset their values
        final var editableValueHolders = getEditableValueHolders(form, facesContext);

        for (final EditableValueHolder editableValueHolder : editableValueHolders) {
            editableValueHolder.resetValue();
        }

    }

    /**
     * Visits all {@link EditableValueHolder} within the given form that are
     * rendered and set them as valid.
     *
     * @param form         to start from, must not be null
     * @param facesContext must not be null
     */
    public static void setEditableValueHoldersValid(final UIForm form, final FacesContext facesContext) {

        requireNonNull(form);
        requireNonNull(facesContext);

        // iterate over found sub-components and reset their values
        final var editableValueHolders = getEditableValueHolders(form, facesContext);

        for (final EditableValueHolder editableValueHolder : editableValueHolders) {
            editableValueHolder.setValid(true);
        }

    }

    /**
     * Shortcut for creating and casting a component to a given type.
     *
     * @param context       must not be null
     * @param componentType to be created, must not be null nor empty
     * @return the created component.
     */
    @SuppressWarnings("unchecked")
    public static <T extends UIComponent> T createComponent(final FacesContext context, final String componentType) {
        requireNonNull(context);
        requireNonNull(emptyToNull(componentType));
        return (T) context.getApplication().createComponent(componentType);
    }

    /**
     * Shortcut for creating and casting a renderer to a given type.
     *
     * @param context      must not be null
     * @param family       to be created, must not be null nor empty
     * @param rendererType to be created, must not be null nor empty
     * @return the created component.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Renderer> T createRenderer(final FacesContext context, final String family,
            final String rendererType) {
        requireNonNull(context);
        requireNonNull(emptyToNull(family));
        requireNonNull(emptyToNull(rendererType));
        return (T) context.getRenderKit().getRenderer(family, rendererType);
    }

    private static List<EditableValueHolder> getEditableValueHolders(final UIForm form,
            final FacesContext facesContext) {
        final var visitCallback = new EditableValueHoldersVisitCallback();

        final var visitContext = VisitContext.createVisitContext(facesContext);

        form.visitTree(visitContext, visitCallback);

        return visitCallback.getEditableValueHolders();
    }

    /**
     * Enforce utility class
     */
    private ComponentUtility() {

    }
}
