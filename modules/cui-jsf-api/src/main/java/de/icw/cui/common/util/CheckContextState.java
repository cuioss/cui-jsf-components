package de.icw.cui.common.util;

import javax.faces.context.FacesContext;

import lombok.experimental.UtilityClass;

/**
 * Utilities provided for handling {@link FacesContext} related actions
 */
@UtilityClass
public class CheckContextState {

    /**
     * Helper method needed for indicating whether the Lifecycle processing should continue
     *
     * @param facesContext To be used for checking
     * @return boolean indicating whether {@link FacesContext#getRenderResponse()} or
     *         {@link FacesContext#getResponseComplete()} has been called. In case none of that
     *         happened it will return <code>true</code>
     */
    public static boolean isResponseNotComplete(FacesContext facesContext) {
        return null != facesContext && !facesContext.isReleased() && !facesContext.getResponseComplete();
    }

}
