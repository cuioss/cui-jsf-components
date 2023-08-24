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
package de.cuioss.jsf.api.application.resources.util;

import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import lombok.experimental.UtilityClass;

/**
 * Utility class to provide access to resources at bean level.
 * <p>
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
        return context.getApplication().getViewHandler().getResourceURL(context, uri);
    }
}
