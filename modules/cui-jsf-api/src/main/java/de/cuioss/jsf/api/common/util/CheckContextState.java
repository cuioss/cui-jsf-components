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
 * Utilities provided for handling {@link FacesContext} related actions
 */
@UtilityClass
public class CheckContextState {

    /**
     * Helper method needed for indicating whether the Lifecycle processing should
     * continue
     *
     * @param facesContext To be used for checking
     * @return boolean indicating whether {@link FacesContext#getRenderResponse()}
     *         or {@link FacesContext#getResponseComplete()} has been called. In
     *         case none of that happened it will return <code>true</code>
     */
    public static boolean isResponseNotComplete(FacesContext facesContext) {
        return null != facesContext && !facesContext.isReleased() && !facesContext.getResponseComplete();
    }

}
