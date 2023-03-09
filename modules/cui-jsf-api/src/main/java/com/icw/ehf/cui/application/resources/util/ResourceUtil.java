package com.icw.ehf.cui.application.resources.util;

import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import lombok.experimental.UtilityClass;

/**
 * Utility class to provide access to resources at bean level.
 *
 * Notice: Current implementation is based on prefix mapping for resources.
 * Please add the following line to the web.xml:
 *
 * <p>
 * <code>
 * &lt;servlet-mapping&gt;<br>
 *       &lt;servlet-name&gt;Faces Servlet&lt;/servlet-name&gt;<br>
 *       &lt;url-pattern&gt;/javax.faces.resource/*&lt;/url-pattern&gt;<br>
 * &lt;/servlet-mapping&gt;<br>
 * </code>
 * </p>
 *
 * @author Matthias Walliczek
 */
@UtilityClass
public class ResourceUtil {

    /**
     * Calculate the request path to a given resource to be used a link target.
     *
     * @param resourceName
     * @param libraryName
     * @param context
     * @return the request path (relative, without host name)
     */
    public static String calculateRequestPath(String resourceName, String libraryName, FacesContext context) {
        String uri;

        uri = ResourceHandler.RESOURCE_IDENTIFIER + '/' + resourceName;
        if (null != libraryName) {
            uri += "?ln=" + libraryName;
        }
        uri = context.getApplication().getViewHandler().getResourceURL(context, uri);

        return uri;
    }
}
