package de.cuioss.jsf.api.components.partial;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * <p>
 * This Interface builds the bridge for the interaction between {@link UIComponent} and the partial
 * elements. In essence it exposes the {@link StateHelper}, {@link FacesContext} and
 * {@link UIComponent Facets} to the
 * corresponding composite element.
 * </p>
 * <p>
 * <em>Caution:</em> With this concept we introduce a cyclic dependency into the component classes.
 * Use it with care. The actual implementations, like {@link TitleProviderImpl} must <em>not</em>
 * contain any state within a field. Especially not {@link FacesContext} or {@link StateHelper}
 * </p>
 *
 * @author Oliver Wolff
 */
public interface ComponentBridge {

    /**
     * @return the {@link StateHelper} encapsulated within the specific component.
     */
    StateHelper stateHelper();

    /**
     * @return the {@link FacesContext} encapsulated within the specific component.
     */
    FacesContext facesContext();

    /**
     * @param facetName the facets name, e.g. 'header', 'footer'.
     * @return the facet or null.
     */
    UIComponent facet(String facetName);
}
