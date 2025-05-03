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
