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
package de.cuioss.jsf.api.application.navigation;

import de.cuioss.tools.net.UrlParameter;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.FacesContext;

/**
 * Interface defining a simple redirection capability for JSF applications.
 * <p>
 * Implementors of this interface can perform redirects to specific locations,
 * optionally with URL parameters. This is typically implemented by objects that
 * represent navigation targets, such as enums or other constants that identify
 * pages or views within the application.
 * 
 * <p>
 * The primary use case for this interface is to encapsulate redirection logic
 * for complex navigation scenarios where the standard JSF navigation rules
 * are insufficient, such as:
 * <ul>
 *   <li>Navigating with complex parameter sets</li>
 *   <li>Dynamic navigation based on runtime conditions</li>
 *   <li>Navigation to external resources</li>
 * </ul>
 * 
 * <p>
 * <em>Important:</em> This interface is not intended as a replacement for the standard
 * {@link NavigationHandler} and should only be used for special cases where the
 * standard navigation mechanisms are insufficient. Frequent use of this interface
 * may indicate design or architectural issues in the application.
 * 
 * <p>
 * Implementations must be thread-safe if they are to be used from multiple threads.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see NavigationUtils
 * @see ViewIdentifier
 */
public interface Redirector {

    /**
     * Executes a redirect to the target represented by this object.
     * <p>
     * This method performs a redirection to a JSF view or external resource,
     * optionally with the provided URL parameters.
     * 
     * <p>
     * Implementation details, such as how the target URL is determined and
     * how parameters are handled, are left to the implementing class.
     *
     * @param facesContext The current FacesContext, must not be null
     * @param parameters   Optional URL parameters to include in the redirect
     * @throws NullPointerException if facesContext is null
     */
    void redirect(FacesContext facesContext, UrlParameter... parameters);
}
