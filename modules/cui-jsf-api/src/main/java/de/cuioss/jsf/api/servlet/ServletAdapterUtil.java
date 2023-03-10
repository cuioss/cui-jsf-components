package de.cuioss.jsf.api.servlet;

import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;
import static java.util.Objects.requireNonNull;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.experimental.UtilityClass;

/**
 * Utility class, introducing functionality not covered within {@link ExternalContext}
 * <p>
 * In essence these are mainly convenience methods
 * </p>
 *
 * @author Oliver Wolff
 * @author Nina Petersmann (i000656)
 */
@UtilityClass
public final class ServletAdapterUtil {

    /**
     * Reads an Parameter from the external Request.
     *
     * @param facesContext representing current state. Must not be null or empty
     * @param parameterName identifying the wanted parameter. Must not be null or empty
     * @return the loaded parameter if available
     */
    public static String getRequestParameter(FacesContext facesContext, String parameterName) {
        requireNotEmpty(parameterName, "parameterName");
        return getRequest(facesContext).getParameter(parameterName);
    }

    /**
     * Accesses the contained HttpServletResponse
     *
     * @param facesContext representing current state. Must not be null
     * @return corresponding HttpServletResponse
     */
    public static HttpServletResponse getResponse(FacesContext facesContext) {
        requireNonNull(facesContext);
        final var externalContext = facesContext.getExternalContext();
        return (HttpServletResponse) externalContext.getResponse();
    }

    /**
     * Accesses the contained RequestHeaderMap
     *
     * @param facesContext representing current state. Must not be null
     * @return corresponding RequestHeaderMap
     */
    public static Map<String, String> getRequestHeaderMap(FacesContext facesContext) {
        requireNonNull(facesContext);
        final var externalContext = facesContext.getExternalContext();
        return externalContext.getRequestHeaderMap();
    }

    /**
     * Accesses the contained HttpServletRequest
     *
     * @param facesContext representing current state. Must not be null
     * @return corresponding HttpServletRequest
     */
    public static HttpServletRequest getRequest(FacesContext facesContext) {
        requireNonNull(facesContext);
        final var externalContext = facesContext.getExternalContext();
        return (HttpServletRequest) externalContext.getRequest();
    }
}
