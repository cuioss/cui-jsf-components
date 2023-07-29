package de.cuioss.jsf.api.application.navigation;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import de.cuioss.tools.net.UrlParameter;

/**
 * <p>
 * Defines a simply redirectMethod a given object. The Object is usually an enum
 * or similar representing a page or portal.
 * </p>
 * <em>Caution</em>: This Interface does not define a replacement for
 * {@link NavigationHandler} and should solely be used for special cases to
 * simplify do redirects with complex parameter set. If you find it often within
 * your code you have possible a design / architectural issue
 *
 * @author Oliver Wolff
 */
public interface Redirector {

    /**
     * Executes a redirect on a given object. The Object is usually an enum or
     * similar representing a page or portal.
     *
     * @param facesContext must not be null.
     * @param parameters   optional {@link UrlParameter}
     */
    void redirect(FacesContext facesContext, UrlParameter... parameters);
}
