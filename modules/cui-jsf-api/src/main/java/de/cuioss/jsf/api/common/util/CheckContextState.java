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
package de.cuioss.jsf.api.common.util;

import jakarta.faces.context.FacesContext;
import lombok.experimental.UtilityClass;

/**
 * Utility class providing methods for checking and handling {@link FacesContext} state.
 * <p>
 * This class allows components and backing beans to check the current state of the
 * JSF lifecycle and determine whether further processing should continue.
 * 
 * <p>
 * During the JSF lifecycle, certain conditions can cause processing to be short-circuited,
 * such as when {@code responseComplete()} or {@code renderResponse()} is called. This
 * utility provides convenient methods to check these conditions.
 * 
 * <p>
 * Usage example:
 * <pre>
 * if (CheckContextState.isResponseNotComplete(facesContext)) {
 *     // Proceed with further processing
 * } else {
 *     // Skip processing as response is already complete
 * }
 * </pre>
 * 
 * <p>
 * This class is designed as a stateless utility class (using Lombok's {@code @UtilityClass})
 * and cannot be instantiated.
 * 
 * @author Oliver Wolff
 * @since 1.0
 */
@UtilityClass
public class CheckContextState {

    /**
     * Determines whether JSF lifecycle processing should continue by checking the
     * current state of the FacesContext.
     * <p>
     * This method checks several conditions:
     * <ul>
     *   <li>Whether the FacesContext is null</li>
     *   <li>Whether the FacesContext has been released</li>
     *   <li>Whether response completion has been signaled</li>
     * </ul>
     * 
     * <p>
     * This method is particularly useful in phases like Invoke Application or
     * Update Model Values, where you might want to check if previous phases have
     * short-circuited the lifecycle.
     *
     * @param facesContext The current FacesContext instance to check, may be null
     * @return {@code true} if processing should continue (response is not complete),
     *         {@code false} otherwise (including when facesContext is null)
     * @see FacesContext#getResponseComplete()
     * @see FacesContext#isReleased()
     */
    public static boolean isResponseNotComplete(FacesContext facesContext) {
        return null != facesContext && !facesContext.isReleased() && !facesContext.getResponseComplete();
    }
}
